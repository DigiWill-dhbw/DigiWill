package digiwill;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserHandleRepositoryImpl implements UserHandleRepositoryCustom {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<BaseAction> findBaseActionsBy(ObjectId userHandleUID) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(userHandleUID));
        return mongoTemplate.findAll(BaseAction.class, "actions");
    }

    @Override
    public Iterable<BaseAction> findBaseActionsBy(String emailAddress) {
        Query query = new Query();
        query.addCriteria(Criteria.where("emailAddress").is(emailAddress));
        return mongoTemplate.findOne(query, UserHandle.class).getActions();
    }
}
