package de.digiwill.service;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;

public class WebhookService {

    public WebhookService() {
    }

    public static void sendGet(String url) throws Exception {
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");
        con.getInputStream();
    }
}
