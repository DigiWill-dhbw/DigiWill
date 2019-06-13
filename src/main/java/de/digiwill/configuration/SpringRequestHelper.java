package de.digiwill.configuration;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Component
public class SpringRequestHelper {

    public Map<String, String> getTokenInfo(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class
                .getName());
        map.put("token", csrf.getToken());
        map.put("parameterName", csrf.getParameterName());
        return map;
    }
}

