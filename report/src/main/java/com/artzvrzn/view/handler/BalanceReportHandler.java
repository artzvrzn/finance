package com.artzvrzn.view.handler;

import com.artzvrzn.model.Account;
import com.artzvrzn.view.handler.api.IReportHandler;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

//@Component
//@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BalanceReportHandler implements IReportHandler {

    private static final String ACCOUNT_KEY = "accounts";
    private final Communicator communicator;
    private final ParamsParser paramsParser;

    public BalanceReportHandler(Communicator communicator, ParamsParser paramsParser) {
        this.communicator = communicator;
        this.paramsParser = paramsParser;
    }

    @Override
    public ByteArrayOutputStream generate(Map<String, Object> params) {
        List<Account> accounts = communicator.getAccounts(paramsParser.getAccountIds(params));
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            Workbook workbook = getWorkbook(accounts);
            workbook.write(os);
            workbook.close();
            return os;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to create report", e);
        } finally {
        }
    }

    @Override
    public String getFileFormat() {
        return ".xlsx";
    }

    private Workbook getWorkbook(List<Account> accounts) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Баланс");
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
        headerCell.setCellValue("Аккаунт");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Валюта");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Сумма");
        headerCell.setCellStyle(headerStyle);
    }

    private void createRow(Account account, int index, Sheet sheet, CellStyle contentStyle) {
        Row row = sheet.createRow(index);
        Cell cell = row.createCell(0);
        cell.setCellValue(account.getId().toString());
        cell.setCellStyle(contentStyle);

        cell = row.createCell(1);
        cell.setCellValue(communicator.getCurrency(account.getCurrency()).getTitle());
        cell.setCellStyle(contentStyle);

        cell = row.createCell(2);
        cell.setCellValue(account.getBalance());
        cell.setCellStyle(contentStyle);
    }
}
