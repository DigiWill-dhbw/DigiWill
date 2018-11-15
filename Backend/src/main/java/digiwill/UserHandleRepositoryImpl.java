package digiwill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class UserHandleRepositoryImpl implements UserHandleRepositoryCustom {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public boolean checkLogin() {
        //TODO implement
        return false;
    }
}
