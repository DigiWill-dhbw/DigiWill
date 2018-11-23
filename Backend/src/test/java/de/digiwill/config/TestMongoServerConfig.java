package de.digiwill.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@ComponentScan(basePackages = "de.digiwill")
@EnableMongoRepositories(basePackages = "de.digiwill.repository")
@Configuration
public class TestMongoServerConfig extends AbstractFongoConfiguration {

}


