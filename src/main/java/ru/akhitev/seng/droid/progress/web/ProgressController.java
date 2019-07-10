package ru.akhitev.seng.droid.progress.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.akhitev.seng.droid.progress.db.entity.Progress;
import ru.akhitev.seng.droid.progress.logic.ProgressService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/progress")
public class ProgressController {
    private ProgressService progressService;

    @Autowired
    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @RequestMapping(value = "/load", method = RequestMethod.POST)
    public String load(@RequestParam("date") String date, Model model) throws IOException {
        LocalDate selectedDate = LocalDate.parse(date);
        progressService.loadProgresses(selectedDate);
        List<Progress> progressForLoad = progressService.getProgresses();
        model.addAttribute("progressForLoad", progressForLoad);
        return "progress/load";
    }

    @RequestMapping(value = "/select_date", method = RequestMethod.GET)
    public String select_date(Model model) throws IOException {
        return "progress/date";
    }

    @RequestMapping(value = "/load_result", method = RequestMethod.GET)
    public String load_result(Model model) throws IOException {
        progressService.saveProgresses();
        return "progress/load_result";
    }
}
