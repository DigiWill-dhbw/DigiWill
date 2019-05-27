package de.digiwill.service;

import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;

@Service
public class WebhookService {

    public WebhookService() {
    }

    public void sendGet(String url) throws Exception {
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");
        con.getInputStream();
    }
}
