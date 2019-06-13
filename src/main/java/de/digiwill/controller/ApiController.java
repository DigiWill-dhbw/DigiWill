package de.digiwill.controller;

import de.digiwill.configuration.SpringRequestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class ApiController {

    @Autowired
    private SpringRequestHelper requestHelper;

    @RequestMapping(value = "/api/csrf", method = RequestMethod.GET)
    public Map<String, String> getLoginApiJson(HttpServletRequest request) {
        return requestHelper.getTokenInfo(request);
    }
}
