package controller;

import model.Customer;
import repository.CompositeRepository;
import repository.CustomerRepository;
import repository.json.JsonCustomerRepositoryImpl;

import java.util.List;

public class CustomerController implements Controller<Customer> {
    private CompositeRepository compositeRepository;

    public CustomerController() {}

    public CustomerController(CompositeRepository compositeRepository) {
        this.compositeRepository = compositeRepository;
    }

    @Override
    public Customer create(Customer customer) {
        List<Customer> customers = getAll();
        if (customers.isEmpty())
            customer.setId(1);
        else if (customer.getId() == 0)
            customer.setId(customers.get(customers.size() - 1).getId() + 1);
        return save(customer);
    }

    private Customer save(Customer customer) {
        return ((CustomerRepository) compositeRepository.getRepository(new JsonCustomerRepositoryImpl()))
                .create(customer);
    }

    @Override
    public List<Customer> getAll() {
        return ((CustomerRepository) compositeRepository.getRepository(new JsonCustomerRepositoryImpl()))
                .getAll();
    }

    @Override
    public Customer update(Customer customer, Integer id) {
        return ((CustomerRepository) compositeRepository.getRepository(new JsonCustomerRepositoryImpl()))
                .update(customer, id);
    }

    @Override
    public void delete(Integer id) {
        compositeRepository.getRepository(new JsonCustomerRepositoryImpl()).delete(id);
    }

    @Override
    public String toString() {
        return "Customer Controller";
    }
}
