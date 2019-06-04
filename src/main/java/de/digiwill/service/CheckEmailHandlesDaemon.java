package de.digiwill.service;

import de.digiwill.model.EmailResponseHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class CheckEmailHandlesDaemon {

    /**
     * Check interval in minutes
     */
    private static final int CHECK_INTERVAL = 5;

    @Autowired
    private EmailResponseHandleManager emailResponseHandleManager;
    @Autowired
    private UserHandleManager userHandleManager;

    private boolean running = false;
    /**
     * Progress in percent
     */
    private float progress;

    private Logger logger = LoggerFactory.getLogger(CheckEmailHandlesDaemon.class);

    @Async
    @Scheduled(fixedRate = CHECK_INTERVAL * 60000)
    public void check() {
        running = true;
        //TODO maybe update time
        long currentTime = Instant.now().getEpochSecond();
        List<EmailResponseHandle> emailResponseHandles = emailResponseHandleManager.findAll();
        float amountOfUsersPerc = (float) emailResponseHandles.size() / 100;
        int processedData = 0;
        for (EmailResponseHandle emailResponseHandle : emailResponseHandles) {
            process(emailResponseHandle, currentTime);
            processedData++;
            progress = processedData / amountOfUsersPerc;
            logger.trace("Progress: {}", progress);
        }
        long executionDuration = ((Instant.now().getEpochSecond()) - currentTime);
        logger.info("Check finished in: {} seconds", executionDuration);
        running = false;
    }

    public void process(EmailResponseHandle emailResponseHandle, long currentTime){
        if(emailResponseHandle.getTimeout() < currentTime ){
            emailResponseHandle.executeTimeout(userHandleManager, emailResponseHandleManager);
        }
    }

    public boolean isRunning() {
        return running;
    }

    public float getProgress() {
        return progress;
    }
}
