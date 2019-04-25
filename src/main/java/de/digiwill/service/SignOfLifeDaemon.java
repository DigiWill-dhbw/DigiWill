package de.digiwill.service;

import de.digiwill.exception.EmailException;
import de.digiwill.model.BaseAction;
import de.digiwill.model.UserHandle;
import de.digiwill.repository.UserHandleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SignOfLifeDaemon {

    /**
     * Check interval in minutes
     */
    private static final int checkInterval = 10;

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
    @Scheduled(fixedRate = checkInterval * 60000)
    public void check() {
        running = true;
        long currentTime = System.currentTimeMillis() / 1000L;
        List<UserHandle> userHandles = userHandleRepository.findAll();
        float amountOfUsersPerc = (float) userHandles.size() / 100;
        int processedUsers = 0;
        for (UserHandle user : userHandles) {
            processUser(currentTime, user);
            processedUsers++;
            progress = processedUsers / amountOfUsersPerc;
            logger.trace("Progress: " + progress);
        }
        long executionDuration = ((System.currentTimeMillis() / 1000L) - currentTime);
        logger.info("Check finished in: " + executionDuration + " seconds");
        if(executionDuration > checkInterval * 60000){
            logger.error("SignOfLifeDaemon EXECUTION TOOK TO LONG: " + (executionDuration / 60000) + " minutes");
        }
        running = false;
    }

    private void processUser(long currentTime, UserHandle user) {
        boolean isUser = user.getAuthorityByRoleName("ROLE_USER") != null;
        boolean shouldTriggerActions = user.getLastSignOfLife() + user.getDeltaDeathTime() >= currentTime;
        if(isUser) {
            if (user.getLastSignOfLife() != -1 && !user.areAllActionsCompleted() && shouldTriggerActions) {
                user.setDead();
                executeActions(user);
            }else if(!user.isDead()){
                long lastInteractionWithUser = Math.max(user.getLastSignOfLife(), user.getLastReminder());
                if(lastInteractionWithUser + user.getDeltaReminder() > currentTime){
                    try {
                        emailDispatcher.sendReminderEmail(user);
                        user.setLastReminder(currentTime);
                    } catch (EmailException e) {
                        logger.error("Couldn't send reminder to user ", e);
                    }
                }
            }
        }
    }

    private void executeActions(UserHandle user) {
        List<BaseAction> actions = user.getActions();
        boolean allCompleted = true;
        for (BaseAction action : actions) {
            if (!action.wasCompleted()) {
                allCompleted = allCompleted && action.execute().wasSuccessful();
            }
        }
        if(allCompleted) {
            user.setAllActionsCompleted();
        }
        userHandleManager.updateUser(user);
    }


    public boolean isRunning() {
        return running;
    }

    public float getProgress() {
        return progress;
    }
}