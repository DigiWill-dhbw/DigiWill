package de.digiwill.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class UserHandleRepositoryImpl implements UserHandleRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public boolean checkLogin() {
        //TODO implement
        return false;
    }
}
