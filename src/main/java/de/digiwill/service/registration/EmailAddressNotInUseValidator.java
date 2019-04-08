package de.digiwill.service.registration;

import de.digiwill.model.UserHandle;
import de.digiwill.model.UserHandleManager;
import org.springframework.util.MultiValueMap;

public class EmailAddressNotInUseValidator extends RegistrationValidator {

    private UserHandleManager userHandleManager;

    public EmailAddressNotInUseValidator(UserHandleManager userHandleManager) {
        super(RegistrationResponse.EMAIL_ALREADY_IN_USE);
        this.userHandleManager = userHandleManager;
    }

    @Override
    public boolean validate(final MultiValueMap<String, String> formData) {
        UserHandle userHandle = userHandleManager.loadUserByEmailAddress(formData.getFirst("email"));
        return userHandle == null;
    }
}
