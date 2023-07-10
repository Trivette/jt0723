package com.cardinal.toolrental;

import com.cardinal.toolrental.entities.Tool;
import com.cardinal.toolrental.repositories.ToolRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(ToolRepository toolRepository) {

        return args -> {
            log.info("Preloading " + toolRepository.save(new Tool("CHNS", Tool.ToolType.Chainsaw, Tool.ToolBrand.Stihl)));
            log.info("Preloading " + toolRepository.save(new Tool("LADW", Tool.ToolType.Ladder, Tool.ToolBrand.Werner)));
            log.info("Preloading " + toolRepository.save(new Tool("JAKD", Tool.ToolType.Jackhammer, Tool.ToolBrand.DeWalt)));
            log.info("Preloading " + toolRepository.save(new Tool("JAKR", Tool.ToolType.Jackhammer, Tool.ToolBrand.Ridgid)));
        };
    }
}