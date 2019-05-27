package de.digiwill.model;


import de.digiwill.service.WebhookService;
import org.springframework.beans.factory.annotation.Autowired;

public class WebhookAction extends BaseAction {

    @Autowired
    WebhookService webhookService;

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public WebhookAction(String url) {
        this.url = url;
    }

    public ActionSuccess executeAction() {
        try {
            webhookService.sendGet(this.url);
            return ActionSuccess.SUCCESS;
        } catch (Exception e) {
            return ActionSuccess.FAILURE;
        }
    }
}
