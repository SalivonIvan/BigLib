package ua.biglib.salivon.controller;

import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.biglib.salivon.entity.Customer;
import ua.biglib.salivon.repositoriy.CustomerJpaController;
import ua.biglib.salivon.service.RePasswordService;

@Controller
public class IndexController {

    private static Logger log = LogManager.getLogger(IndexController.class);
    @Autowired
    private CustomerJpaController customerJpaController;

    public CustomerJpaController getCustomerJpaController() {
        return customerJpaController;
    }

    public void setCustomerJpaController(CustomerJpaController customerJpaController) {
        this.customerJpaController = customerJpaController;
    }

    @RequestMapping(value = "/index")
    public String loadIndex() {
        return "index";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String loadFormSignup(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("visibility", "hidden");
        log.info("PreCreate new Customer");
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView performSignup(@Valid Customer customer,
            BindingResult bindingResult, @RequestParam String rePassword) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("customer", customer);
        log.info(bindingResult);
        log.info("RePassword - " + rePassword);
        log.info(rePassword.getClass());
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("visibility", "hidden");
            modelAndView.setViewName("signup");
            return modelAndView;
        }
        if (!new RePasswordService().compare(customer, rePassword)) {
            modelAndView.addObject("visibility", "show");
            modelAndView.setViewName("signup");
            return modelAndView;
        }
        customer.setEnabled(1);
        customerJpaController.create(customer);
        log.info("Full Name - " + customer.getFullName());
        log.info("save new Customer");
        modelAndView.setViewName("redirect:/index");
        return modelAndView;
    }

    @RequestMapping(value = "/login")
    public String loadFormLogin() {
        return "login";
    }
}
