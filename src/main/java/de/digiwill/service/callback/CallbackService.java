package de.digiwill.service.callback;

import de.digiwill.model.EmailResponseHandle;
import de.digiwill.model.EmailVerificationHandle;
import de.digiwill.model.UserHandleManager;
import de.digiwill.repository.EmailResponseHandleRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CallbackService {
    @Autowired
    private UserHandleManager userHandleManager;
    @Autowired
    private EmailResponseHandleRepository emailResponseHandleRepository;

    //CallbackService(){
    //
    //}

    public CallbackResponse getCallbackResponse(String id, String token){
            EmailResponseHandle emailResponseHandle;
            try {
                emailResponseHandle = emailResponseHandleRepository.findEmailResponseHandleBy(new ObjectId(id));
            }catch(IllegalArgumentException ex){
                return CallbackResponse.CALLBACK_ERROR;
            }

            if (emailResponseHandle != null && emailResponseHandle.verifyToken(token)) {
                ((EmailVerificationHandle) emailResponseHandle).executeCallback(userHandleManager, emailResponseHandleRepository);
                return CallbackResponse.CALLBACK_SUCCESS;
            } else {
                return CallbackResponse.CALLBACK_ERROR;
            }
    }

}
