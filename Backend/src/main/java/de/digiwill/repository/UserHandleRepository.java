package de.digiwill.repository;

import de.digiwill.model.UserHandle;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Only CRUD operations for UserHandle in this interface
 */
@Repository
public interface UserHandleRepository extends MongoRepository<UserHandle, ObjectId>, UserHandleRepositoryCustom {

    UserHandle findUserHandleByUID(ObjectId UID);

    UserHandle findUserHandleByEmailAddress(String emailAddress);

    UserHandle findAllBy();

}
