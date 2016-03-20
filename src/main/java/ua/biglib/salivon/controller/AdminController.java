package ua.biglib.salivon.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private static Logger log = LogManager.getLogger(AdminController.class);

    @RequestMapping
    public String loadAdminPage() {
        return "admin";
    }
}
