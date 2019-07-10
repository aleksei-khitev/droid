package ru.akhitev.seng.droid.report.web;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.akhitev.seng.droid.report.logic.ReportService;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Controller
@RequestMapping("/report")
public class ReportController {
    private ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String selectReport() {
        return "report/select_report";
    }

    @RequestMapping(value = "/make_report_for_current_period", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> makeReportForCurrentPeriod(Model model) throws IOException {
        ByteArrayInputStream in = reportService.makeReportForCurrentPeriod();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + reportService.prepareReportName());
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
    }

    @RequestMapping(value = "/make_report_for_current_period_with_step7", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> makeReportForCurrentPeriodWithStep7(Model model) throws IOException {
        ByteArrayInputStream in = reportService.makeReportForCurrentPeriodWithStep7();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + reportService.prepareReportName());
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
    }

    @RequestMapping(value = "/make_report_for_whole_period", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> makeReportForWholePeriod(Model model) throws IOException {
        ByteArrayInputStream in = reportService.makeReportForWholePeriod();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + reportService.prepareReportName());
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
    }
}
