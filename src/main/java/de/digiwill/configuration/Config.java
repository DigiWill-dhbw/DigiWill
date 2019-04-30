package de.digiwill.configuration;


import com.mongodb.MongoClient;
import de.digiwill.service.EmailDispatcher;
import de.digiwill.util.EmailTransportWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Configuration
@PropertySource("classpath:secrets-${envTarget}.properties")
public class Config {
    @Autowired
    private Environment env;

    private Logger logger = LoggerFactory.getLogger(Config.class);

    @Bean("emailDispatcher")
    public EmailDispatcher getEmailDispatcher() {
        EmailConfig emailconfig = new EmailConfig(env.getProperty("mail.host"),
                env.getProperty("mail.port"),
                env.getProperty("mail.user"),
                env.getProperty("mail.password"));

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", emailconfig.getHost());
        props.put("mail.smtp.port", emailconfig.getPort());

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailconfig.getUser(), emailconfig.getPassword());
            }
        });
        logger.info("emailDispatcher Bean created");
        return new EmailDispatcher(session, new EmailTransportWrapper());
    }

    @Bean("mongodbfactory")
    public MongoDbFactory mongoDbFactory() {
        return new SimpleMongoDbFactory(new MongoClient(env.getProperty("database.host"), Integer.parseInt(env.getProperty("database.port"))),
                env.getProperty("database.name"));
    }

}
