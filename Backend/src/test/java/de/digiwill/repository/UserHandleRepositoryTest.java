package de.digiwill.repository;

import de.digiwill.model.*;
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

    private UserHandleManager userHandleManager;

    @Before
    public void setup() {
        userHandleManager = new UserHandleManager(repository);
        userHandleManager.createUsers(createUserHandle(5, Arrays.asList(
                new EmailAction(Arrays.asList("nobodyT@digiwill.de"), "Hey there!", false, "blalbalbla")
        )));
    }

    @Test
    public void findUserHandleByEmailAddressTest() {
        Assert.assertEquals("nobody1@digiwill.de", userHandleManager.findUserHandleByUsername("nobody1@digiwill.de").getUsername());
    }

    private Iterable<UserHandle> createUserHandle(int amount, List<BaseAction> actions) {
        List<UserHandle> users = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            UserHandle userHandle = new UserHandle("nobody" + i + "@digiwill.de", "nobody" + i + "@digiwill.de", null);
            users.add(userHandle);
        }
        return users;
    }

}
