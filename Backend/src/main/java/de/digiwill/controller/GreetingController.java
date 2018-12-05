package de.digiwill.controller;

import de.digiwill.service.SignOfLifeDaemon;
import de.digiwill.util.EmailDispatcher;
import org.apache.catalina.core.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {
    @Autowired
    private EmailDispatcher emailDispatcher;

    @Autowired
    SignOfLifeDaemon signOfLifeDaemon;

    private static ApplicationContext app;
    Logger logger = LoggerFactory.getLogger(GreetingController.class);
    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);

        //Examples for logging
        logger.trace("A TRACE Message");
        logger.debug("A DEBUG Message");
        logger.info("An INFO Message");
        logger.warn("A WARN Message");
        logger.error("An ERROR Message");

        signOfLifeDaemon.check();
        return "greeting";

    }

}
