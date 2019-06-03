package de.digiwill.service;

import de.digiwill.exception.EmailException;
import de.digiwill.model.UserHandle;
import de.digiwill.repository.UserHandleRepository;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class SignOfLifeDaemon {

    /**
     * Check interval in minutes
     */
    private static final int CHECK_INTERVAL = 10;

    @Autowired
    private UserHandleRepository userHandleRepository;

    @Autowired
    private UserHandleManager userHandleManager;

    @Autowired
    private EmailDispatcher emailDispatcher;

    private boolean running = false;
    /**
     * Progress in percent
     */
    private float progress;

    private Logger logger = LoggerFactory.getLogger(SignOfLifeDaemon.class);

    @Async
    @Scheduled(fixedRate = CHECK_INTERVAL * 60000)
    public void check() {
        running = true;
        long currentTime = Instant.now().getEpochSecond();
        List<UserHandle> userHandles = userHandleRepository.findAll();
        float amountOfUsersPerc = (float) userHandles.size() / 100;
        int processedUsers = 0;
        for (UserHandle user : userHandles) {
            if(processUser(currentTime, user)) {
                userHandleManager.updateUser(user);
            }
            processedUsers++;
            progress = processedUsers / amountOfUsersPerc;
            logger.trace("Progress: {}", progress);
        }
        long executionDuration = ((Instant.now().getEpochSecond()) - currentTime);
        logger.info("Check finished in: {} seconds", executionDuration);
        if(executionDuration > CHECK_INTERVAL * 60000){
            logger.error("SignOfLifeDaemon EXECUTION TOOK TO LONG: {} minutes", (executionDuration / 60000));
        }
        running = false;
    }

    private boolean processUser(long currentTime, @NotNull UserHandle user) {
        boolean isUser = user.getAuthorityByRoleName("ROLE_USER") != null && user.getAuthorityByRoleName("ROLE_ADMIN") == null;
        boolean userPresumedDead = currentTime >= user.getLastSignOfLife() + user.getDeltaDeathTime();
        boolean returnValue = false;
        if (isUser) {
            if (user.getLastSignOfLife() != -1 && !user.areAllActionsCompleted() && userPresumedDead) {
                user.setDead();
                user.executeActions(emailDispatcher);
                returnValue = true;
            } else if (!user.isDead() && user.getLastInteractionWithUser() + user.getDeltaReminder() > currentTime) {
                try {
                    emailDispatcher.sendReminderEmail(user);
                    user.setLastReminder(currentTime);
                    returnValue = true;
                } catch (EmailException e) {
                    logger.error("Couldn't send reminder to user ", e);
                }
            }
        }
        return returnValue;
    }


    public boolean isRunning() {
        return running;
    }

    public float getProgress() {
        return progress;
    }
}