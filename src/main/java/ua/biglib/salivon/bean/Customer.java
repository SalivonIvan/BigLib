package ua.biglib.salivon.bean;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Customer {

    @Size(min = 3, max = 50,
            message = "Your full name must be between 3 and 50 characters long.")
    private String fullName;
    @Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}",
            message = "Invalid email address.")
    private String email;
    @Size(min = 6, max = 20,
            message = "The password must be at least 6 characters long.")
    private String password;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
