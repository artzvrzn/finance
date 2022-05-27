package com.artzvrzn.view.handler;

import com.artzvrzn.exception.ValidationException;
import com.artzvrzn.model.Account;
import com.artzvrzn.model.Operation;
import com.artzvrzn.view.handler.api.IReportHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

//@Component
//@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ByDateReportHandler implements IReportHandler {

    private final Map<UUID, String> readCurrencies = new HashMap<>();
    private final Map<UUID, String> readCategories = new HashMap<>();
//    @Autowired
    private final Communicator communicator;
//    @Autowired
    private final ObjectMapper mapper;

    public ByDateReportHandler(Communicator communicator, ObjectMapper mapper) {
        this.mapper = mapper;
        this.communicator = communicator;
    }

    @Override
    public byte[] generate(Map<String, Object> params) {
        validateParams(params);
        List<Account> accounts = communicator.getAccounts(getAccountIds(params));
        try (Workbook workbook = getWorkbook(accounts, getFrom(params), getTo(params), getCategoryIds(params));
             ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            workbook.write(os);
            return os.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to create report", e);
        }
    }

    @Override
    public void validate(Map<String, Object> params) {

    }

    private List<UUID> getIds(Map<String, Object> params, String key) {
        return mapper.convertValue(
                params.get(key), mapper.getTypeFactory().constructCollectionType(List.class, UUID.class));
    }

    private long getLong(Map<String, Object> params, String key) {
        return mapper.convertValue(params.get(key), Long.class);
    }

    private long getFrom(Map<String, Object> params) {
        return getLong(params, "from");
    }

    private List<UUID> getAccountIds(Map<String, Object> params) {
        return getIds(params, "accounts");
    }

    private List<UUID> getCategoryIds(Map<String, Object> params) {
        return getIds(params, "categories");
    }

    private long getTo(Map<String, Object> params) {
        return getLong(params, "to");
    }

    private void validateParams(Map<String, Object> params) {
        if (params == null || params.isEmpty()) {
            throw new ValidationException("Empty params");
        }
        if (params.size() <= 1) {
            throw new ValidationException("Wrong parameters for that type of report");
        }
    }

    private Workbook getWorkbook(List<Account> accounts, long from, long to, Collection<UUID> categories) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("By date");
        sheet.setColumnWidth(0, 4000);
        sheet.setColumnWidth(1, 10000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 4000);
        sheet.setColumnWidth(4, 4000);
        CellStyle headerStyle = headerStyle(workbook);
        createHeader(sheet, headerStyle);
        CellStyle contentStyle = contentStyle(workbook);
        int rowIndex = 1;
        for (Account account: accounts) {
            List<Operation> operations = communicator.getOperations(account.getId(), from, to, categories)
                    .stream()
                    .sorted(Comparator.comparing(Operation::getDate))
                    .collect(Collectors.toList());
            for (Operation operation: operations) {
                createRow(account, operation, rowIndex++, sheet, contentStyle);
            }
        }
        return workbook;
    }

    private CellStyle headerStyle(Workbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);
        return headerStyle;
    }

    private CellStyle contentStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        return style;
    }
    private void createHeader(Sheet sheet, CellStyle headerStyle) {
        Row header = sheet.createRow(0);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Дата");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Аккаунт");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Сумма");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("Валюта");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("Категория");
        headerCell.setCellStyle(headerStyle);
    }

    private void createRow(Account account, Operation operation, int index, Sheet sheet, CellStyle contentStyle) {
        Row row = sheet.createRow(index);
        Cell cell = row.createCell(0);
        cell.setCellValue(operation.getDate().toLocalDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        cell.setCellStyle(contentStyle);

        cell = row.createCell(1);
        cell.setCellValue(account.getId().toString());
        cell.setCellStyle(contentStyle);

        cell = row.createCell(2);
        cell.setCellValue(operation.getValue());
        cell.setCellStyle(contentStyle);

        cell = row.createCell(3);
        String currency = readCurrencies.get(operation.getCurrency());
        if (currency == null) {
            currency = communicator.readCurrency(operation.getCurrency()).getTitle();
            readCurrencies.put(operation.getCurrency(), currency);
        }
        cell.setCellValue(currency);
        cell.setCellStyle(contentStyle);

        cell = row.createCell(4);
        String category = readCategories.get(operation.getCategory());
        if (category == null) {
            category = communicator.readCategory(operation.getCategory()).getTitle();
            readCategories.put(operation.getCategory(), category);
        }
        cell.setCellValue(category);
        cell.setCellStyle(contentStyle);
    }
}
