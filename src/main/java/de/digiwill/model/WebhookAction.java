package de.digiwill.model;


import de.digiwill.service.WebhookService;

public class WebhookAction extends BaseAction {

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
            WebhookService.sendGet(this.url);
            return ActionSuccess.SUCCESS;
        } catch (Exception e) {
            return ActionSuccess.FAILURE;
        }
    }
}
