package de.digiwill.service;

import de.digiwill.model.UserHandle;
import de.digiwill.repository.UserHandleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserHandleManager implements UserDetailsManager {

    @Autowired
    private UserHandleRepository userHandleRepository;

    private Logger logger = LoggerFactory.getLogger(UserHandleManager.class);

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
        long usersDeleted = userHandleRepository.deleteUserHandleByEmailAddress(emailAddress);
        logger.debug("Deleted "+ usersDeleted + " user(s) with emailAdress: "+emailAddress);
        if(usersDeleted > 2){
            logger.error(usersDeleted + " users deleted. Should have been only one.");
        }
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        //TODO implement
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
