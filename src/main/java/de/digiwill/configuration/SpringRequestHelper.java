package de.digiwill.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Component
public class SpringRequestHelper {

    private static final Logger logger = LoggerFactory.getLogger(SpringRequestHelper.class);

    public Map<String, String> getTokenInfo(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class
                .getName());
        map.put("token", csrf.getToken());
        map.put("parameterName", csrf.getParameterName());
        return map;
    }
}

