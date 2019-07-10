package ru.akhitev.seng.droid.report.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.akhitev.seng.droid.employee.db.entity.Employee;
import ru.akhitev.seng.droid.employee.db.repo.EmployeeRepository;
import ru.akhitev.seng.droid.progress.db.entity.Progress;
import ru.akhitev.seng.droid.progress.db.repo.ProgressRepository;
import ru.akhitev.seng.droid.progress.logic.ProgressService;
import ru.akhitev.seng.droid.report.logic.xlsx.XlsWorkReportCreator;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private EmployeeRepository employeeRepository;
    private ProgressRepository progressRepository;
    private ProgressService progressService;

    @Autowired
    public ReportService(EmployeeRepository employeeRepository, ProgressRepository progressRepository, ProgressService progressService) {
        this.employeeRepository = employeeRepository;
        this.progressRepository = progressRepository;
        this.progressService = progressService;
    }

    public ByteArrayInputStream makeReportForCurrentPeriod() throws IOException {
        return prepareReport(prepareData(currentPeriodFunction));
    }

    public ByteArrayInputStream makeReportForCurrentPeriodWithStep7() throws IOException {
        return prepareReport(prepareData(e -> currentPeriodFunction.apply(e)
                .stream().filter( p -> p.getProgressDate().getDayOfWeek().equals(DayOfWeek.FRIDAY)).collect(Collectors.toList())));
    }

    public ByteArrayInputStream makeReportForWholePeriod() throws IOException {
        return prepareReport(prepareData(e -> progressRepository.findByEmployee(e)));
    }

    private ByteArrayInputStream prepareReport(Map<String, List<Progress>> data) throws IOException {
        XlsWorkReportCreator reportCreator = new XlsWorkReportCreator();
        return reportCreator.makeReport(data);
    }

    private Map<String, List<Progress>> prepareData(Function<Employee, List<Progress>> function) {
        Map<String, List<Progress>> allProgress = new HashMap<>();
        List<Employee> employees = employeeRepository.findAll();
        employees.forEach( e -> {
            List<Progress> progresses = function.apply(e);
            if (!progresses.isEmpty() && progresses.size() > 1) {
                allProgress.put(e.getName(), progresses);
            }
        });
        return allProgress;
    }

    private Function<Employee, List<Progress>> currentPeriodFunction = e -> {
        final int periodBorder = 1;
        final LocalDate startOfPeriod;
        final LocalDate endOfPeriod;
        final LocalDate currentDate = LocalDate.now();
        if (currentDate.getDayOfMonth() < periodBorder) {
            startOfPeriod = LocalDate.of(currentDate.getYear(), currentDate.getMonth().minus(1), periodBorder);
            endOfPeriod = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), periodBorder);
        } else {
            startOfPeriod = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), periodBorder);
            endOfPeriod = LocalDate.of(currentDate.getYear(), currentDate.getMonth().plus(1), periodBorder);
        }
        return progressRepository.findByEmployeeCurrentPeriod(e, startOfPeriod, endOfPeriod);
    };

    public String prepareReportName() {
        String fileExtension = ".xlsx";
        LocalDate date;
        if (progressService.getSelectedDate() != null) {
            date = progressService.getSelectedDate();
        } else {
            date = LocalDate.now();
        }
        return date.toString() + fileExtension;
    }
}
