package ru.akhitev.seng.droid.progress.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.akhitev.seng.droid.employee.db.entity.Employee;
import ru.akhitev.seng.droid.progress.db.entity.Progress;

import java.time.LocalDate;
import java.util.List;

public interface ProgressRepository extends JpaRepository<Progress, Integer> {
    List<Progress> findByEmployee(Employee employee);

    List<Progress> findByEmployeeAndProgressDate(@Param("employee") Employee employee, @Param("startOfPeriod") LocalDate progressDate);

    @Query("select p from Progress p where p.progressDate >= :startOfPeriod and p.employee = :employee")
    List<Progress> findByEmployeeCurrentPeriod(@Param("employee") Employee employee, @Param("startOfPeriod") LocalDate startOfPeriod);

    @Query("select p from Progress p where p.progressDate >= :startOfPeriod and p.progressDate < :endOfPeriod and p.employee = :employee")
    List<Progress> findByEmployeeCurrentPeriod(@Param("employee") Employee employee, @Param("startOfPeriod") LocalDate startOfPeriod, @Param("endOfPeriod") LocalDate endOfPeriod);
}
