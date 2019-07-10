package ru.akhitev.seng.droid.average.term.logic.service;

import org.springframework.stereotype.Service;
import ru.akhitev.seng.droid.average.term.logic.calculator.ReportCalculator;
import ru.akhitev.seng.droid.average.term.logic.pasrer.MegaPlanReportPasrer;
import ru.akhitev.seng.droid.average.term.logic.vo.CandidateValue;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class AverageTermService {
    private LocalDate bottomBorder;
    private Map<String, Double> averageCandidates;

    public void processFile(File file) {
        averageCandidates = getAveragedCandidates(getCandidatesFromFile(Paths.get(file.toURI())));
    }

    private Map<String, Double> getAveragedCandidates(List<CandidateValue> candidates) {
        ReportCalculator calculator = new ReportCalculator(candidates, bottomBorder);
        return calculator.calculate();
    }

    private List<CandidateValue> getCandidatesFromFile(Path filePath) {
        MegaPlanReportPasrer parser = new MegaPlanReportPasrer(filePath);
        return parser.parse();
    }

    public void setBottomBorder(LocalDate bottomBorder) {
        this.bottomBorder = bottomBorder;
    }

    public Map<String, Double> getAverageCandidates() {
        return averageCandidates;
    }
}
