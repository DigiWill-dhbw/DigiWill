package digiwill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        FileInputStream propFile = null;
        try {
            propFile = new FileInputStream( "digiwill.properties");
            Properties p = new Properties(System.getProperties());
            p.load(propFile);
            // set the system properties
            System.setProperties(p);
            // display new properties
            System.getProperties().list(System.out);
        } catch (IOException e) {
            System.err.println("No properties found.");
            return;
        }

        SpringApplication.run(Application.class, args);
    }
}