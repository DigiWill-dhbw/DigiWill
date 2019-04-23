package de.digiwill.repository;

import de.digiwill.model.EmailResponseHandle;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailResponseHandleRepository extends MongoRepository<EmailResponseHandle, ObjectId>{

        EmailResponseHandle findEmailResponseHandleBy(ObjectId UID);

        EmailResponseHandle deleteEmailResponseHandleBy(ObjectId UID);

    }
