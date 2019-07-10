package ru.akhitev.seng.droid.employee.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.akhitev.seng.droid.employee.db.entity.Employee;

import java.util.List;

//https://www.baeldung.com/spring-data-jpa-modifying-annotation
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findByName(String name);
}
