package de.digiwill.configuration;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Configuration
public class MongoConfig {

    public @Bean
    MongoDbFactory mongoDbFactory() {
        //TODO move constants to property files
        return new SimpleMongoDbFactory(new MongoClient("localhost", 27017), "digiwill");
    }

}
