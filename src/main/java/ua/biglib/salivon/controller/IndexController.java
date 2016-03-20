package ua.biglib.salivon.controller;

import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.biglib.salivon.bean.Customer;

@Controller
public class IndexController {

    private static Logger log = LogManager.getLogger(IndexController.class);

    @RequestMapping(value = "/index")
    public String loadIndex() {
        return "index";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String loadFormSignup(Model model) {
        model.addAttribute(new Customer());
        log.info("create new Customer");
        return "signup";
    }
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String performSignup(@Valid Customer customer,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        log.info("save new Customer");
        return "redirect:/index";
    }

    @RequestMapping(value = "/login")
    public String loadFormLogin() {
        return "login";
    }
}
