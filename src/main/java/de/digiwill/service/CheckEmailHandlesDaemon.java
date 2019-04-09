package de.digiwill.service;

import de.digiwill.model.EmailResponseHandle;
import de.digiwill.repository.EmailResponseHandleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckEmailHandlesDaemon {

    /**
     * Check interval in minutes
     */
    private static final int checkInterval = 5;

    @Autowired
    private EmailResponseHandleRepository emailResponseHandleRepository;

    private boolean running = false;
    /**
     * Progress in percent
     */
    private float progress;

    private Logger logger = LoggerFactory.getLogger(CheckEmailHandlesDaemon.class);
//TODO make daemons generic?
    @Async
    @Scheduled(fixedRate = checkInterval * 60000)
    public void check() {
        running = true;
        //TODO maybe update time
        long currentTime = System.currentTimeMillis() / 1000L;
        List<EmailResponseHandle> emailResponseHandles = emailResponseHandleRepository.findAll();
        float amountOfUsersPerc = (float) emailResponseHandles.size() / 100;
        int processedData = 0;
        for (EmailResponseHandle emailResponseHandle : emailResponseHandles) {
            process(emailResponseHandle, currentTime);
            processedData++;
            progress = processedData / amountOfUsersPerc;
            logger.trace("Progress: " + progress);
        }
        logger.info("Check finished in: " + ((System.currentTimeMillis() / 1000L) - currentTime));
        running = false;
    }

    public void process(EmailResponseHandle emailResponseHandle, long currentTime){
        if(emailResponseHandle.getTimeout() < currentTime ){
            //TODO special operations for different response handles
            //for example delete account if registrarion handle gets deleted
            emailResponseHandleRepository.delete(emailResponseHandle);
        }
    }

    public boolean isRunning() {
        return running;
    }

    public float getProgress() {
        return progress;
    }
}
