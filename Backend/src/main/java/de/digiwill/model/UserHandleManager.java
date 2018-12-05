package de.digiwill.model;

import de.digiwill.repository.UserHandleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserHandleManager implements UserDetailsManager {

    @Autowired
    UserHandleRepository userHandleRepository;

    public UserHandleManager() {

    }

    /**
     * constructor for tests
     * @param userHandleRepository
     */
    public UserHandleManager(UserHandleRepository userHandleRepository) {
        if (this.userHandleRepository == null) {
            this.userHandleRepository = userHandleRepository;
        }
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
        userHandleRepository.deleteUserHandleBy(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {

        try {
            UserDetails user = loadUserByUsername(username);
            return true;
        } catch (UsernameNotFoundException e) {
            return false;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserHandle user = userHandleRepository.findUserHandleByUsername(username);
        if (user == null) {
            user = userHandleRepository.findUserHandleByUsername(username);
        }
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

    public void createUsers(Iterable<UserHandle> users) {
        userHandleRepository.insert(users);
    }

    public UserHandle loadUserByUserName(String username) {
        return (UserHandle) loadUserByUsername(username);
    }

    public List<UserHandle> findAll(){
        return userHandleRepository.findAll();
    }
}
