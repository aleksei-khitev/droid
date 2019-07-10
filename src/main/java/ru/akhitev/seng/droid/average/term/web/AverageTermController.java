package ru.akhitev.seng.droid.average.term.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.akhitev.seng.droid.average.term.logic.service.AverageTermService;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/average")
public class AverageTermController {
    private AverageTermService averageTermService;

    @Autowired
    public AverageTermController(AverageTermService averageTermService) {
        this.averageTermService = averageTermService;
    }

    @RequestMapping(value = "/select_date", method = RequestMethod.GET)
    public String selectDate(Model model) throws IOException {
        return "average/date";
    }

    @RequestMapping(value = "/select_file", method = RequestMethod.POST)
    public String selectFile(@RequestParam("date") String date, Model model) throws IOException {
        LocalDate selectedDate = LocalDate.parse(date);
        averageTermService.setBottomBorder(selectedDate);
        return "average/select_file";
    }

    @RequestMapping(value = "/process", method = RequestMethod.POST)
    public String load(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        File destination = new File("/var/tmp", UUID.randomUUID().toString() + "-" + file.getOriginalFilename());
        file.transferTo(destination);
        averageTermService.processFile(destination);
        Map<String, Double> averageCandidates = averageTermService.getAverageCandidates();
        System.out.println(averageCandidates);
        model.addAttribute("averageCandidates", averageCandidates);
        return "average/process";
    }
}
