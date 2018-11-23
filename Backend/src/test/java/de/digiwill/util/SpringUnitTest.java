package de.digiwill.util;

import org.apache.commons.io.FileUtils;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;

@ActiveProfiles({ "fongo", "test", "unit" })
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = { UnitTestApplicationConfig.class })
public class SpringUnitTest {

    protected void importJSON(String collection, String file) {
        try {
            for (Object line : FileUtils.readLines(new File(file), "utf8")) {
                //mongoTemplate.save(line, collection);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not import file: " + file, e);
        }

    }
}
