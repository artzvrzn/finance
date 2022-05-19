package com.artzvrzn.view;

import com.artzvrzn.view.api.ExcelReportProducer;
import com.artzvrzn.model.rest.Account;
import com.artzvrzn.model.report.Report;
import com.artzvrzn.model.report.ReportType;
import com.artzvrzn.view.api.IRestService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class BalanceReportProducer implements ExcelReportProducer {

    private final IRestService restService;

    public BalanceReportProducer(IRestService restService) {
        this.restService = restService;
    }

    @Override
    public byte[] produce(Report report) {
        if (!report.getType().equals(ReportType.BALANCE)) {
            throw new IllegalStateException("Wrong params type");
        }
        List<Account> accounts = restService.getAccounts(report.getParams());
        try (Workbook workbook = getWorkbook(accounts);
             ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            workbook.write(os);
            return os.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to convert report to bytes", e);
        }
    }

    private Workbook getWorkbook(List<Account> accounts) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Persons");
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
        cell.setCellValue(account.getCurrency().toString());
        cell.setCellStyle(contentStyle);

        cell = row.createCell(2);
        cell.setCellValue(account.getBalance());
        cell.setCellStyle(contentStyle);
    }
}
