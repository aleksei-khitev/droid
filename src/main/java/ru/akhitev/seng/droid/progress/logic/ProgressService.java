package ru.akhitev.seng.droid.progress.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.akhitev.seng.droid.employee.db.entity.Employee;
import ru.akhitev.seng.droid.employee.db.repo.EmployeeRepository;
import ru.akhitev.seng.droid.google.service.GoogleSheetsService;
import ru.akhitev.seng.droid.progress.db.entity.Progress;
import ru.akhitev.seng.droid.progress.db.repo.ProgressRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class ProgressService {
    private GoogleSheetsService sheetsService;
    private EmployeeRepository employeeRepository;
    private ProgressRepository progressRepository;
    private List<Progress> progresses;
    private LocalDate selectedDate;

    @Autowired
    public ProgressService(GoogleSheetsService sheetsService, EmployeeRepository employeeRepository, ProgressRepository progressRepository) {
        this.sheetsService = sheetsService;
        this.employeeRepository = employeeRepository;
        this.progressRepository = progressRepository;
    }

    public void loadProgresses(LocalDate selectedDate) throws IOException {
        this.selectedDate = selectedDate;
        List<List<Object>> progressesForLoad = sheetsService.readTable();
        List<String> header = parseHeader(progressesForLoad);
        progresses = parseProgresses(progressesForLoad, header);
    }

    public List<Progress> getProgresses() {
        return progresses;
    }

    public void saveProgresses() {
        getProgresses().forEach(progress -> progressRepository.saveAndFlush(progress));
    }

    private List<String> parseHeader(List<List<Object>> progressesForLoad) {
        if (progressesForLoad == null
                || progressesForLoad.size() < 1
                || progressesForLoad.get(0) == null
                || progressesForLoad.get(0).size() < 5) {
            throw new ProgressException("Ошибка обработки заголовка (пустые данные из google-таблицы или неправильное количество колонок)");
        }
        List<String> header = new ArrayList<>();
        progressesForLoad.get(0).forEach(o -> header.add((String) o));
        if (header.size() < 5) {
            throw new ProgressException("Ошибка обработки заголовка (пустой заголовок или неправильное количество колонок)");
        }
        return header;
    }
    
    private List<Progress>  parseProgresses(List<List<Object>> progressesForLoad, List<String> header) {
        List<Progress> progresses = new ArrayList<>();
        IntStream.range(1, progressesForLoad.size()).forEach(i -> progresses.add(parseProgress(progressesForLoad.get(i), header)));
        if (progresses.size() < 1) {
            throw new ProgressException("Ошибка обработки прогресса (нет записей прогресса)");
        }
        return progresses;
    }
    
    private Progress parseProgress(List<Object> progressForLoad, List<String> header) {
        if (progressForLoad == null || progressForLoad.size() < 5) {
            throw new ProgressException("Ошибка обработки прогресса (пустая запись прогресса или неправильное количество колонок)");
        }
        Progress progress = new Progress();
        Employee employee = employeeRepository.findByName((String) progressForLoad.get(0)).get(0);
        progress.setEmployee(employee);
        progress.setProgressDate(selectedDate);
        IntStream.range(1, header.size()).forEach( index -> {
            if ("Взято в работу".equals(header.get(index).trim())) {
                progress.setTookInWork(Integer.parseInt((String) progressForLoad.get(index)));
            }
            if ("Отказ наш".equals(header.get(index).trim())) {
                progress.setOurRefuges(Integer.parseInt((String) progressForLoad.get(index)));
            }
            if ("Резерв, все причины".equals(header.get(index).trim())) {
                progress.setAllCausesReserve(Integer.parseInt((String) progressForLoad.get(index)));
            }
            if ("Выведено в работу".equals(header.get(index).trim())) {
                progress.setLaunchedInWork(Integer.parseInt((String) progressForLoad.get(index)));
            }
            if ("Отказ кандидата".equals(header.get(index).trim())) {
                progress.setCandidateRefuges(Integer.parseInt((String) progressForLoad.get(index)));
            }
            if ("Ср срок вывода, дни".equals(header.get(index).trim())) {
                progress.setLaunchesAverageTerm(Double.parseDouble(((String) progressForLoad.get(index)).replace(",", ".")));
            }    
        });
        return progress;
    }

    public LocalDate getSelectedDate() {
        return selectedDate;
    }
}
