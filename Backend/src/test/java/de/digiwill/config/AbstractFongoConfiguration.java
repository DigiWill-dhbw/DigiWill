package de.digiwill.config;

import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

public class AbstractFongoConfiguration extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        //TODO replace constant with property
        return "digiwill_test";
    }

    @Bean
    @Override
    public MongoClient mongoClient() {
        return new Fongo("mongo-test").getMongo();
    }

}
