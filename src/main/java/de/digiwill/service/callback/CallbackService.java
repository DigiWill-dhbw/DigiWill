package de.digiwill.service.callback;

import de.digiwill.model.EmailResponseHandle;
import de.digiwill.service.EmailResponseHandleManager;
import de.digiwill.service.UserHandleManager;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CallbackService {
    @Autowired
    private UserHandleManager userHandleManager;
    @Autowired
    private EmailResponseHandleManager emailResponseHandleManager;


    public CallbackResponse getCallbackResponse(String id, String token) {
        EmailResponseHandle emailResponseHandle = getEmailResponseHandle(id, token);

        if (emailResponseHandle != null) {
            return emailResponseHandle.executeCallback(userHandleManager, emailResponseHandleManager);
        } else {
            return CallbackResponse.CALLBACK_ERROR;
        }
    }

    public EmailResponseHandle getEmailResponseHandle(String id, String token) throws IllegalArgumentException {
        EmailResponseHandle emailResponseHandle = emailResponseHandleManager.findEmailResponseHandleBy(new ObjectId(id));

        if (emailResponseHandle.verifyToken(token)) {
            return emailResponseHandle;
        }
        return null;

    }

}
