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

    //CallbackService(){
    //
    //}

    public CallbackResponse getCallbackResponse(String id, String token){
            //TODO check for timeout
            EmailResponseHandle emailResponseHandle;
            try {
                emailResponseHandle = emailResponseHandleManager.findEmailResponseHandleBy(new ObjectId(id));
            }catch(IllegalArgumentException ex){
                return CallbackResponse.CALLBACK_ERROR;
            }

            if (emailResponseHandle != null && emailResponseHandle.verifyToken(token)) {
                return emailResponseHandle.executeCallback(userHandleManager, emailResponseHandleManager);
            } else {
                return CallbackResponse.CALLBACK_ERROR;
            }
    }

}
