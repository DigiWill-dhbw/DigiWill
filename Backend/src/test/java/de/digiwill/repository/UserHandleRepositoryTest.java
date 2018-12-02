package de.digiwill.repository;

import de.digiwill.model.BaseAction;
import de.digiwill.model.EmailAction;
import de.digiwill.model.PersonalData;
import de.digiwill.model.UserHandle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UserHandleRepositoryTest {

    @Autowired
    protected MongoTemplate mongoTemplate;

    @Autowired
    private UserHandleRepository repository;

    @Before
    public void setup() {
        repository.insert(createUserHandle(5, Arrays.asList(
                new EmailAction(Arrays.asList("nobodyT@digiwill.de"), "Hey there!", false, "blalbalbla")
        )));
    }

    @Test
    public void findUserHandleByEmailAddressTest() {
        Assert.assertEquals("nobody1", repository.findUserHandleByUsername("nobody1@digiwill.de").getAlias());
    }

    private Iterable<UserHandle> createUserHandle(int amount, List<BaseAction> actions) {
        List<UserHandle> users = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            UserHandle userHandle = new UserHandle("nobody" + i + "@digiwill.de", "nobody" + i + "@digiwill.de", null);
            //UserHandle userHandle = new UserHandle("nobody" + i + "@digiwill.de", new PersonalData("No", "Body" + i),
            //        "nobody" + i, 0, 0, 0, 0, false, true, actions);
            users.add(userHandle);
        }
        return users;
    }

}
