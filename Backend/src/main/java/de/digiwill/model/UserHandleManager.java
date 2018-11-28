package de.digiwill.model;

import de.digiwill.model.UserHandle;
import de.digiwill.repository.UserHandleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;


@Component
public class UserHandleManager implements UserDetailsManager {

    @Autowired UserHandleRepository userHandleRepository;

    public UserHandleManager() {

    }

    @Override
    public void createUser(UserDetails user) {
        UserHandle userHandle = (UserHandle) user;
        userHandleRepository.insert(userHandle);
    }

    @Override
    public void updateUser(UserDetails user) {
        UserHandle userHandle = (UserHandle) user;
        userHandleRepository.save(userHandle);
    }

    @Override
    public void deleteUser(String username) {
        userHandleRepository.deleteByUsername(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {

        try {
            UserDetails user = loadUserByUsername(username);
            return true;
        }catch(UsernameNotFoundException e){
            return false;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserHandle user = userHandleRepository.findUserHandleByUsername(username);
        if(user == null){
            user = userHandleRepository.findUserHandleByUsername(username);
        }
        if(user == null){
            throw new UsernameNotFoundException(username);
        }
        return user;
    }
}
