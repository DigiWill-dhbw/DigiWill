package digiwill;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Only CRUD operations for UserHandle in this interface
 */
public interface UserHandleRepository extends MongoRepository<UserHandle, ObjectId>, UserHandleRepositoryCustom {

    UserHandle findUserHandleByUID(ObjectId UID);

    UserHandle findUserHandleByEmailAddress(String emailAddress);

    UserHandle findAllBy();

}
