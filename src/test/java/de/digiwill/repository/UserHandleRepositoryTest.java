package de.digiwill.repository;

import de.digiwill.model.*;
import de.digiwill.service.UserHandleManager;
import de.digiwill.util.SecurityHelper;
import de.digiwill.util.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UserHandleRepositoryTest {

    @Autowired
    private UserHandleRepository repository;
    private UserHandleManager userHandleManager;

    @Before
    public void setUp() {
        userHandleManager = new UserHandleManager(repository);
        userHandleManager.createUsers(TestUtils.createUserHandles(5, Arrays.asList(
                new EmailAction(Arrays.asList("nobodyT@digiwill.de"), "Hey there!", false, "blalbalbla")
        )));
    }

    @Test
    public void findUserHandleByEmailAddressTest() {
        Assert.assertEquals("nobody1@digiwill.de", userHandleManager.loadUserByEmailAddress("nobody1@digiwill.de").getEmailAddress());
    }



}
