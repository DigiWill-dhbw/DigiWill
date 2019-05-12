package de.digiwill.model;


public class WebhookAction extends BaseAction {

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    private String domain;

    public WebhookAction(String domain) {
        this.domain = domain;
    }

    public ActionSuccess executeAction() {
        return ActionSuccess.SUCCESS;
    }
}
