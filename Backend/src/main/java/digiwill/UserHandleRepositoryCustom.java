package digiwill;

import org.bson.types.ObjectId;

/**
 * Custom db operations
 */
public interface UserHandleRepositoryCustom {

    Iterable<BaseAction> findBaseActionsBy(ObjectId UserHandleUID);

    Iterable<BaseAction> findBaseActionsBy(String emailAddress);

}
