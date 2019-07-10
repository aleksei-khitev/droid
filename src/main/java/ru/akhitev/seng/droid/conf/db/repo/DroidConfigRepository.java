package ru.akhitev.seng.droid.conf.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.akhitev.seng.droid.conf.db.entity.DroidConfig;

public interface DroidConfigRepository extends JpaRepository<DroidConfig, Integer> {
    DroidConfig findByProperty(String property);
}
