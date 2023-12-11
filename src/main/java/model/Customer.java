package model;

import java.util.regex.Pattern;

public class Customer {
    private final String firstName;
    private final String lastName;
    private final String email;

    public Customer(String firstName, String lastName, String email) {
        if (validateEmail(email)) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
        } else {
            throw new IllegalArgumentException("invalid email address");
        }
    }

    public final String getFirstName() {
        return firstName;
    }

    public final String getLastName() {
        return lastName;
    }

    public final String getEmail() {
        return email;
    }

    @Override
    public final String toString() {
        return "Name: " + firstName + " " + lastName + " Email: " + email;
    }

    private boolean validateEmail(String email) {
        final Pattern pattern = Pattern.compile("^(.+)@[a-z]+.com$");
        return pattern.matcher(email).matches();
    }
}

class CustomerTester {
    public static void main(String[] args) {
        Customer customer = new Customer("first", "second", "jack@example.com");

        System.out.println(customer);
    }
}