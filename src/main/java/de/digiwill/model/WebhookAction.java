package de.digiwill.model;


import de.digiwill.service.EmailDispatcher;
import de.digiwill.service.WebhookService;

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
        this.type = ActionType.WEBHOOK;
    }

    public ActionSuccess executeAction(EmailDispatcher emailDispatcher) {
        boolean fail = false;
        for (int i = 0; i < this.getEventNames().size(); i++) {
            String event = this.getEventNames().get(i);
            String url = "https://maker.ifttt.com/trigger/eventName/with/key/apiKey";
            url = url.replaceAll("eventName", event);
            url = url.replaceAll("apiKey", this.getApiKey());
            try {
                WebhookService.sendGet(url);
            } catch(Exception e) {
                fail = true;
            }
        }
        if (fail) {
            return ActionSuccess.FAILURE;
        } else {
            return ActionSuccess.SUCCESS;
        }
    }
}
