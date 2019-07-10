package ru.akhitev.seng.droid.google.service;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.akhitev.seng.droid.conf.db.repo.DroidConfigRepository;
import ru.akhitev.seng.droid.conf.logic.DroidConfigService;

import java.io.IOException;
import java.util.List;

@Service
public class GoogleSheetsService {
    private GoogleConnectionService connectionService;
    private String appName;
    private String spreadsheetId;
    private String sheetRange;

    private Sheets sheetsService = null;

    @Autowired
    public GoogleSheetsService(GoogleConnectionService connectionService, DroidConfigService configService) {
        this.connectionService = connectionService;
        appName = "SEng Droid";
        spreadsheetId = "1Y91MZ-SzZ3xOVBHvIZBwSE2eBPiL_nPd9qc7wDvfGM8";
        sheetRange = configService.valueOfProperty("sheet_range");
    }

    public List<List<Object>> readTable()  throws IOException {
        Sheets service = getSheetsService();
        return readTable(service, spreadsheetId, sheetRange);
    }

    private Sheets getSheetsService() {
        if (this.sheetsService == null) {
            return new Sheets.Builder(this.connectionService.getHttpTransport(), this.connectionService.getJsonFactory(), this.connectionService.getCredential())
                    .setApplicationName(appName).build();
        } else {
            return this.sheetsService;
        }
    }

    private List<List<Object>> readTable(Sheets service, String spreadsheetId, String sheetName)  throws IOException {
        ValueRange table = service.spreadsheets().values().get(spreadsheetId, sheetName)
                .execute();
        List<List<Object>> values = table.getValues();
        printTable(values);
        return values;
    }

    private void printTable(List<List<Object>> values) {
        if (values == null || values.size() == 0) {
            System.out.println("No data found.");
        }

        else {
            System.out.println("read data");
            for (List<Object> row : values) {
                for (int c = 0; c < row.size(); c++) {
                    System.out.printf("%s ", row.get(c));
                }
                System.out.println();
            }
        }
    }
}
