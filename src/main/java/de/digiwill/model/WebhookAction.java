package de.digiwill.model;


import de.digiwill.service.EmailDispatcher;

import java.util.ArrayList;
import java.util.List;

public class WebhookAction extends BaseAction {

    private List<String> eventNames = new ArrayList<>();
    private String apiKey;

    public List<String> getEventNames() {
        return eventNames;
    }

    public void setEventNames(List<String> eventNames) {
        this.eventNames = eventNames;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void addEvent(String eventName) {
        eventNames.add(eventName);
    }

    public void setEvents(List<String> eventNames) {
        this.eventNames = eventNames;
    }

    public void removeEvent(int idx) {
        eventNames.remove(idx);
    }

    public String getEvent(int idx) {
        return eventNames.get(idx);
    }

    public WebhookAction(String apiKey) {
        this.apiKey = apiKey;
    }

    public WebhookAction(String apiKey, List<String> eventNames) {
        this.apiKey = apiKey;
        this.eventNames = eventNames;
    }

    public ActionSuccess executeAction(EmailDispatcher emailDispatcher) {
        return ActionSuccess.SUCCESS;
    }
}
