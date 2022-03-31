package com.dala;

import com.dala.data.DataManager;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class AppStartupRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        DataManager dataManager = DataManager.getInstance();
        dataManager.generateRandomPersons(10);

        log.info("Default data generated");
    }
}
