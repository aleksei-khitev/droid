package ru.akhitev.seng.droid.employee.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.akhitev.seng.droid.employee.db.entity.Employee;
import ru.akhitev.seng.droid.employee.db.repo.EmployeeRepository;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @RequestMapping(value = "/list")
    public String list(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        return "employee/list";
    }

    @RequestMapping(value = "/add")
    public String add(Model model) {
        return "employee/add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@RequestParam("name") String name, Model model) {
        Employee employee = new Employee();
        employee.setName(name);
        employeeRepository.save(employee);
        model.addAttribute("employees", employeeRepository.findAll());
        return "employee/list";
    }
}
