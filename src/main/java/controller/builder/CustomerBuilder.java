package controller.builder;

import model.Account;
import model.Customer;
import model.Specialty;

import java.util.Set;

public abstract class CustomerBuilder {
    private Customer customer;

    public void createCustomer() {
        customer = new Customer();
    }

    public abstract void setAccount(Account account);
    public abstract void setSpecialties(Set<Specialty> specialties);

    public Customer getCustomer() {
        return customer;
    }
}
