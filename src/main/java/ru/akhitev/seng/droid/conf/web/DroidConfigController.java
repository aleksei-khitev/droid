package ru.akhitev.seng.droid.conf.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.akhitev.seng.droid.conf.db.entity.DroidConfig;
import ru.akhitev.seng.droid.conf.logic.DroidConfigService;
import java.util.List;

@Controller
@RequestMapping("/config")
public class DroidConfigController {
    private DroidConfigService service;

    @Autowired
    public DroidConfigController(DroidConfigService service) {
        this.service = service;
    }

    @RequestMapping(value = "/list")
    public String load(Model model) {
        List<DroidConfig> configs = service.findAll();
        model.addAttribute("configs", configs);
        return "config/list";
    }

    @RequestMapping(value = "/add")
    public String add(Model model) {
        return "config/add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@RequestParam("property") String property, @RequestParam("value") String value, Model model) {
        service.saveNewProperty(property, value);
        model.addAttribute("configs", service.findAll());
        return "config/list";
    }
}
