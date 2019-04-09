package de.digiwill.repository;

import de.digiwill.model.EmailResponseHandle;
import de.digiwill.model.UserHandle;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailResponseHandleRepository extends MongoRepository<EmailResponseHandle, ObjectId>{

        EmailResponseHandle findEmailResponseHandleByUID(ObjectId UID);

        EmailResponseHandle deleteEmailResponseHandleByUID(ObjectId UID);

    }
