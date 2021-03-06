package model;

import java.util.Set;

public class Customer {
    private int id;
    private Set<Specialty> specialties;
    private Account account;

    public Customer() {
    }

    public Customer(Set<Specialty> specialties, Account account) {
        this.specialties = specialties;
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Specialty> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(Set<Specialty> specialties) {
        this.specialties = specialties;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return account.getName() + " [" + specialties + "]";
    }
}
