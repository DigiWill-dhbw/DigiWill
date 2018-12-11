package de.digiwill.configuration;

import de.digiwill.SpringBootBaseIntegrationTest;
import de.digiwill.model.BaseAction;
import de.digiwill.model.PersonalData;
import de.digiwill.model.Security.SecurityHelper;
import de.digiwill.model.UserHandle;
import de.digiwill.model.UserHandleManager;
import de.digiwill.repository.UserHandleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;


public class CucumberConfig{

    Properties props;

    @Autowired
    private UserHandleRepository repository;
    private UserHandleManager userHandleManager;

    CucumberConfig(){
        this.props =  new Properties();
        try(FileInputStream fi = new FileInputStream("test/resources/secrets-"+System.getProperty("envTarget")+".properties")){
            props.load(fi);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setUpUserHandle(int amount, List<BaseAction> actions) {
        if (userHandleManager == null) {
            userHandleManager = new UserHandleManager(repository);
        }
        List<UserHandle> users = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            PersonalData personalData = new PersonalData("no", "body" + i, new Date(2018, 1, 1));
            UserHandle userHandle = new UserHandle("nobody" + i + "@digiwill.de", SecurityHelper.encodePassword("nobody" + i + "@digiwill.de"), AuthorityUtils.createAuthorityList("ROLE_USER"),
                    true, true, true,
                    true , -1, -1, -1, -1, false,
                    personalData,   actions, false);
            users.add(userHandle);
        }
        userHandleManager.createUsers(users);
    }

    public void dropUsers() {
        if (userHandleManager != null) {
            userHandleManager.deleteAllUsers();
        }
    }

    public int getPort(){
        return Integer.parseInt(props.getProperty("port"));
    }

    public UserHandleRepository getRepository() {
        return repository;
    }

    public UserHandleManager getUserHandleManager() {
        return userHandleManager;
    }
}
