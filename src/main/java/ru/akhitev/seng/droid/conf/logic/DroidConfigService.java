package ru.akhitev.seng.droid.conf.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.akhitev.seng.droid.conf.db.entity.DroidConfig;
import ru.akhitev.seng.droid.conf.db.repo.DroidConfigRepository;

import java.util.List;

@Service
public class DroidConfigService {
    private DroidConfigRepository droidConfigRepository;

    @Autowired
    public DroidConfigService(DroidConfigRepository droidConfigRepository) {
        this.droidConfigRepository = droidConfigRepository;
    }

    public List<DroidConfig> findAll() {
        return droidConfigRepository.findAll();
    }

    public String valueOfProperty(String property) {
        return droidConfigRepository.findByProperty(property).getValue();
    }

    public void saveNewProperty(String property, String value) {
        DroidConfig droidConfig = new DroidConfig(property, value);
        droidConfigRepository.save(droidConfig);
    }

    public void changeValueForConfig(DroidConfig droidConfig, String newValue) {
        droidConfig.setValue(newValue);
        droidConfigRepository.save(droidConfig);
    }
}
