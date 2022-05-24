package com.artzvrzn.view;

import com.artzvrzn.exception.ValidationException;
import com.artzvrzn.model.Account;
import com.artzvrzn.view.api.IReportHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class BalanceReportHandler implements IReportHandler {

    private static final String ACCOUNT_KEY = "accounts";
    @Autowired
    private Communicator communicator;
    @Autowired
    private ObjectMapper mapper;

    @Override
    public byte[] handle(Map<String, Object> params) {
        List<Account> accounts = communicator.getAccounts(getIds(params));
        try (Workbook workbook = getWorkbook(accounts);
             ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            workbook.write(os);
            return os.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to create report", e);
        }
    }

    private List<UUID> getIds(Map<String, Object> params) {
        validateParams(params);
        return mapper.convertValue(
                params.get(ACCOUNT_KEY), mapper.getTypeFactory().constructCollectionType(List.class, UUID.class));
    }

    private void validateParams(Map<String, Object> params) {
        if (params == null || params.isEmpty()) {
            throw new ValidationException("Empty params");
        }
        if (params.containsKey(ACCOUNT_KEY)) {
            if (params.size() > 1) {
                throw new ValidationException("Wrong parameters for that type of report");
            }
        }
    }

    private Workbook getWorkbook(List<Account> accounts) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("BALANCE");
        sheet.setColumnWidth(0, 10000);
        sheet.setColumnWidth(1, 10000);
        sheet.setColumnWidth(2, 4000);
        CellStyle headerStyle = headerStyle(workbook);
        createHeader(sheet, headerStyle);
        CellStyle contentStyle = contentStyle(workbook);
        int rowIndex = 1;
        for (Account account: accounts) {
            createRow(account, rowIndex++, sheet, contentStyle);
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
        headerCell.setCellValue("Account Id");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Currency");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Balance");
        headerCell.setCellStyle(headerStyle);
    }

    private void createRow(Account account, int index, Sheet sheet, CellStyle contentStyle) {
        Row row = sheet.createRow(index);
        Cell cell = row.createCell(0);
        cell.setCellValue(account.getId().toString());
        cell.setCellStyle(contentStyle);

        cell = row.createCell(1);
        cell.setCellValue(communicator.readCurrency(account.getCurrency()).getTitle());
        cell.setCellStyle(contentStyle);

        cell = row.createCell(2);
        cell.setCellValue(account.getBalance());
        cell.setCellStyle(contentStyle);
    }
}
