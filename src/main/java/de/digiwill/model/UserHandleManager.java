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
    private UserHandleRepository userHandleRepository;

    public UserHandleManager() {

    }

    /**
     * constructor for tests
     *
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
    public void deleteUser(String emailAddress) {
        userHandleRepository.deleteUserHandleBy(emailAddress);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String emailAddress) {

        try {
           loadUserByEmailAddress(emailAddress);
            return true;
        } catch (UsernameNotFoundException e) {
            return false;
        }
    }

    public UserHandle loadUserByEmailAddress(String emailAddress) throws UsernameNotFoundException {
        UserHandle user = userHandleRepository.findUserHandleByEmailAddress(emailAddress);
        if (user == null) {
            user = userHandleRepository.findUserHandleByEmailAddress(emailAddress);
        }
        if (user == null) {
            throw new UsernameNotFoundException(emailAddress);
        }
        return user;
    }

    public void createUsers(Iterable<UserHandle> users) {
        userHandleRepository.insert(users);
    }

    public List<UserHandle> findAll(){
        return userHandleRepository.findAll();
      }
    public void deleteAllUsers() {
        userHandleRepository.deleteAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUserByEmailAddress(username);
    }
}
