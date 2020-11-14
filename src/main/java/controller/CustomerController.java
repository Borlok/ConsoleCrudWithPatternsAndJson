package controller;

import model.Customer;
import repository.CompositeRepository;
import repository.CustomerRepository;
import repository.io.CustomerRepositoryImpl;

import java.util.List;

public class CustomerController implements Controller<Customer> {
    private CompositeRepository compositeRepository;

    public CustomerController() {
    }

    public CustomerController(CompositeRepository compositeRepository) {
        this.compositeRepository = compositeRepository;
    }

    @Override
    public Customer create(Customer customer) {
        List<Customer> customers = getAll();
        if (customers.isEmpty())
            customer.getAccount().setId(1);
        else if (customer.getAccount().getId() == 0)
            customer.getAccount().setId(customers.get(customers.size() - 1).getAccount().getId() + 1);
        return save(customer);
    }

    private Customer save(Customer customer) {
        return ((CustomerRepository) compositeRepository.getRepository(new CustomerRepositoryImpl()))
                .create(customer);
    }

    @Override
    public List<Customer> getAll() {
        return ((CustomerRepository) compositeRepository.getRepository(new CustomerRepositoryImpl()))
                .getAll();
    }

    @Override
    public Customer update(Customer customer, Integer id) {
        return ((CustomerRepository) compositeRepository.getRepository(new CustomerRepositoryImpl()))
                .update(customer, id);
    }

    @Override
    public void delete(Integer id) {
        compositeRepository.getRepository(new CustomerRepositoryImpl()).delete(id);
    }

    @Override
    public String toString() {
        return "Customer Controller";
    }
}
