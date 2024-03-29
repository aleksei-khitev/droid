package ru.akhitev.seng.droid.report.logic.xlsx;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.akhitev.seng.droid.progress.db.entity.Progress;
import ru.akhitev.seng.droid.report.logic.xlsx.charter.DataChartersCreatorAbstract;
import ru.akhitev.seng.droid.report.logic.xlsx.charter.DynamicChartersCreatorAbstract;
import ru.akhitev.seng.droid.report.logic.xlsx.table.DataTableCreator;
import ru.akhitev.seng.droid.report.logic.xlsx.table.DynamicTableCreator;

import java.util.List;

class XlsSheetCreator {
    private static final int DATA_CHARTER_TOP_ROW = 26;
    private DataTableCreator dataTableCreator;
    private DynamicTableCreator dynamicTableCreator;
    private DataChartersCreatorAbstract dataChartersCreator;
    private DynamicChartersCreatorAbstract dynamicChartersCreator;

    XlsSheetCreator(List<Progress> progresses, XSSFWorkbook workbook, String employeeName) {
        progresses.sort((o1, o2) -> {
            if (o1.getProgressDate().isAfter(o2.getProgressDate())) {
                return 1;
            } else if (o2.getProgressDate().isAfter(o1.getProgressDate())) {
                return -1;
            } else {
                return 0;
            }
        });
        XSSFSheet sheet = workbook.createSheet(employeeName);
        dataTableCreator = new DataTableCreator(employeeName, progresses, workbook, sheet);
        dynamicTableCreator = new DynamicTableCreator(employeeName, progresses, workbook, sheet);
        dataChartersCreator = new DataChartersCreatorAbstract(sheet, DATA_CHARTER_TOP_ROW, progresses.size());
        dynamicChartersCreator = new DynamicChartersCreatorAbstract(sheet, DATA_CHARTER_TOP_ROW, progresses.size() - 1);
    }

    void prepareSheet() {
        dataTableCreator.create();
        dynamicTableCreator.create();
        dataChartersCreator.create();
        dynamicChartersCreator.create();
    }
}
