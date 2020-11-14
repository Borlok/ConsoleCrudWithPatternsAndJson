package repository.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Account;
import model.AccountStatus;
import model.Customer;
import repository.CustomerRepository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CustomerRepositoryImpl implements CustomerRepository {
    private final String FILE_PATH = "./src/main/resources/files/customer.json";
    private final Path PATH = Paths.get(FILE_PATH);
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public Customer create(Customer customer) {
        List<Customer> customers = getAll();
        customers.add(customer);
        saveCustomerCollectionToFile(customers);
        return customer;
    }

    private void saveCustomerCollectionToFile(List<Customer> customers) {
        try (FileWriter writer = new FileWriter(PATH.toFile())) {
            gson.toJson(customers, writer);
        } catch (IOException e) {
            System.err.println("Ошибка при записи покупателя в файл");
        }
    }

    @Override
    public Customer getById(Integer id) {
        return getAll()
                .stream()
                .filter(x -> x.getAccount().getId() == id)
                .findFirst()
                .orElse(isNotInRepository());
    }

    @Override
    public Customer update(Customer customer, Integer id) {
        List<Customer> customers = getAll();
        customers.set(
                customers.indexOf(customers.stream()
                        .filter(x -> x.getAccount().getId() == id)
                        .findFirst()
                        .orElse(isNotInRepository())), customer);
        saveCustomerCollectionToFile(customers);
        return customer;
    }

    private Customer isNotInRepository() {
        return new Customer(new HashSet<>(), new Account("DELETED", AccountStatus.DELETED));
    }

    @Override
    public List<Customer> getAll() {
        List<Customer> customers = new ArrayList<>();
        try (FileReader reader = new FileReader(PATH.toFile())) {
            customers = gson.fromJson(reader, new TypeToken<List<Customer>>() {
            }.getType());
        } catch (IOException | NullPointerException e) {
            System.err.println("Ошибка чтения файла покупателя: " + e);
        }
        return customers;
    }

    @Override
    public void delete(Integer id) {
        List<Customer> customers = getAll();
        Customer customer = isNotInRepository();
        customer.getAccount().setId(id);
        customers.set(customers.indexOf(customers.stream()
                .filter(x -> x.getAccount().getId() == id)
                .findFirst()
                .orElse(isNotInRepository())), customer);
        saveCustomerCollectionToFile(customers);
    }

    @Override
    public String toString() {
        return "CustomerRepository";
    }
}
