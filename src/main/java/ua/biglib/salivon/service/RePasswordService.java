package ua.biglib.salivon.service;

import ua.biglib.salivon.entity.Customer;

public class RePasswordService {

    public boolean compare(Customer customer, String rePassword) {
        String password = customer.getPassword();
        if (password.equalsIgnoreCase(rePassword)) {
            return true;
        }
        return false;
    }
}
