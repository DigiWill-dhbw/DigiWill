package de.digiwill.repository;

import com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import de.digiwill.model.BaseAction;
import de.digiwill.model.EmailAction;
import de.digiwill.model.PersonalData;
import de.digiwill.model.UserHandle;
import de.digiwill.util.SpringUnitTest;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb.InMemoryMongoRuleBuilder.newInMemoryMongoDbRule;
import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserHandleRepositoryTest extends SpringUnitTest {

    @ClassRule
    public static InMemoryMongoDb inMemoryMongoDb = newInMemoryMongoDbRule().build();

    @Rule
    public MongoDbRule mongoDbRule = newMongoDbRule().defaultSpringMongoDb("digiwill_test");

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    UserHandleRepository repository;

    @Before
    public void setup() {
        setupTestUsers();
    }

    @Test
    public void findUserHandleByEmailAddressTest() {
        Assert.assertEquals("nobody", repository.findUserHandleByEmailAddress("nobody@digiwill.de").getAlias());
    }

    protected void setupTestUsers() {
        List<BaseAction> list = new ArrayList<>();
        list.add(new EmailAction("Hey there!", false, "blalbalbla"));
        UserHandle userHandle1 = new UserHandle("nobody@digiwill.de", new PersonalData("No", "Body"),
                "nobody", 0, 0, 0, 0, false, true, list);
        repository.insert(userHandle1);
    }
}
