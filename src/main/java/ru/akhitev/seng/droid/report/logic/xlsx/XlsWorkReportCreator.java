package ru.akhitev.seng.droid.report.logic.xlsx;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.akhitev.seng.droid.progress.db.entity.Progress;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class XlsWorkReportCreator {
    private XSSFWorkbook workbook;

    public ByteArrayInputStream makeReport(Map<String, List<Progress>> allProgresses) throws IOException {
        workbook = new XSSFWorkbook();
        allProgresses.forEach((key, value) -> {
            XlsSheetCreator sheetCreator = new XlsSheetCreator(value, workbook, key);
            sheetCreator.prepareSheet();
        });
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        writeAndCloseBook(out);
        return new ByteArrayInputStream(out.toByteArray());
    }

    private void writeAndCloseBook(ByteArrayOutputStream out) throws IOException {
        workbook.write(out);
        workbook.close();
    }
}
