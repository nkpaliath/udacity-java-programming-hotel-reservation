package model;

import java.util.regex.Pattern;

public class Customer {
    private String firstName;
    private String lastName;
    private String email;

    Customer(String firstName, String lastName, String email) {
        if (validateEmail(email)) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
        } else {
            throw new IllegalArgumentException("invalid email address");
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    private boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile("^(.+)@[a-z]+.[a-z]{2,3}$");
        return pattern.matcher(email).matches();
    }
}

class CustomerTester {
    public static void main(String[] args) {
        Customer customer = new Customer("first", "second", "jack@example.com");

        System.out.println(customer);
    }
}