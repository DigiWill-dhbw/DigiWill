package de.digiwill.configuration;

public class EmailConfig {
    private String host,
            from,
            port,
            user,
            password;

    public EmailConfig(String host, String from, String port, String user, String password) {
        this.host = host;
        this.from = from;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public String getFrom() {
        return from;
    }

    public String getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
