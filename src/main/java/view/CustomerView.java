package view;

import controller.*;
import controller.builder.AccountBuilder;
import controller.builder.AccountBuilderImpl;
import controller.builder.CustomerBuilderImpl;
import model.Account;
import model.AccountStatus;
import model.Customer;
import model.Specialty;
import view.utils.CustomerAndAccountCommitter;

import java.util.*;

public class CustomerView implements View {
    private final CompositeController compositeController;
    private final CustomerBuilderImpl customerBuilder = new CustomerBuilderImpl();
    private Scanner sc;

    public CustomerView(CompositeController compositeController) {
        this.compositeController = compositeController;
    }

    @Override
    public void main() {
        try {
            sc = new Scanner(System.in);
            System.out.println(
                    "--Покупатели--\n" +
                            "Выберите действие:\n" +
                            "1: Создать покупателя\n" +
                            "2: Посмотреть покупателей\n" +
                            "3: Редактировать покупателя\n" +
                            "4: Удалить покупателя\n" +
                            "5: Назад");
            int choice = sc.nextInt();
            if (choice != 5)
                switch (choice) {
                    case 1 -> create();
                    case 2 -> read();
                    case 3 -> update();
                    case 4 -> delete();
                    default -> {
                        System.out.print("\nТакого действия нет");
                        main();
                    }
                }
        } catch (Exception e) {
            System.out.println("Введены неверные символы: " + e);
            main();
        }
    }

    @Override
    public void create() {
        Customer customer = createCustomer();
        CustomerAndAccountCommitter.setCompositeController(compositeController);
        CustomerAndAccountCommitter.commitCustomerAndAccount(customer, customer.getAccount(), "create");
        main();
    }

    private Customer createCustomer() {
        sc = new Scanner(System.in);
        Account account = createAccount();
        customerBuilder.createCustomer();
        customerBuilder.getCustomer().setAccount(account);
        customerBuilder.setSpecialties(getSpecialties());

        return customerBuilder.getCustomer();
    }

    private Account createAccount() {
        AccountBuilder accountBuilder = new AccountBuilderImpl();
        System.out.println("Введите имя");
        accountBuilder.createAccount();
        accountBuilder.setName(sc.next());

        System.out.println("Выберите статус");
        viewAccountStatus();
        accountBuilder.setStatus(AccountStatus.values()[sc.nextInt() - 1]);

        return accountBuilder.getAccount();
    }

    private void viewAccountStatus() {
        for (int i = 0; i < AccountStatus.values().length; i++)
            System.out.println((i + 1) + ": " + AccountStatus.values()[i]);
    }

    private Set<Specialty> getSpecialties() {
        Set<Specialty> specialties = new HashSet<>();
        List<Specialty> specialtyList = ((SpecialtyController) compositeController
                .getController(new SpecialtyController()))
                .getAll();

        specialtyList.forEach(x -> System.out.println(x.getId() + ": " + x.getName()));

        System.out.println("Добавьте специальности\n" + "для выхода нажмите '0'");
        int order = sc.nextInt();
        while (order != 0) {
            specialties.add(specialtyList.get(order - 1));
            System.out.println("добавьте еще или нажмите '0' для продолжения");
            order = sc.nextInt();
        }
        return specialties;
    }

    @Override
    public void read() {
        viewAllCustomers();
        System.out.println("Введите любой символ для продолжения...");
        sc.next();
        main();
    }

    private List<Customer> getAllCustomersAsList() {
        return ((CustomerController) compositeController.getController(new CustomerController()))
                .getAll();
    }

    private void viewAllCustomers() {
        getAllCustomersAsList().forEach(x ->
                System.out.println(x.getId()
                        + ": | " + x.getAccount().getName()
                        + " | " + x.getAccount().getStatus()
                        + " | " + Arrays.toString(x.getSpecialties().toArray()) + "|"));
    }

    @Override
    public void update() {
        System.out.println("Выберите покупателя для замены");
        viewAllCustomers();
        int id = sc.nextInt();

        Customer customer = createCustomer();
        customer.setId(id);
        updateAccountIdByCustomerId(customer);
        CustomerAndAccountCommitter.setCompositeController(compositeController);
        CustomerAndAccountCommitter.commitCustomerAndAccount(customer, customer.getAccount(), "update");
        main();
    }

    @Override
    public void delete() {
        System.out.println("Выберите покупателя для удаления: ");
        viewAllCustomers();
        Customer customer = new Customer(new HashSet<>(),new Account());
        int id = sc.nextInt();
        customer.setId(id);
        updateAccountIdByCustomerId(customer);
        CustomerAndAccountCommitter.setCompositeController(compositeController);
        CustomerAndAccountCommitter.commitCustomerAndAccount(customer, customer.getAccount(), "delete");
        main();
    }

    private void updateAccountIdByCustomerId(Customer customer) {
        customer.getAccount().setId(
                ((CustomerController) compositeController.getController(new CustomerController()))
                        .getAll().stream()
                        .filter(x -> x.getId() == customer.getId())
                        .findFirst().orElse(
                        new Customer(new HashSet<>(),new Account("DELETED",AccountStatus.DELETED)))
                        .getAccount().getId()
        );
    }

    @Override
    public String toString() {
        return "Покупатели";
    }
}
