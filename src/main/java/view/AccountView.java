package view;

import controller.*;
import controller.builder.AccountBuilderImpl;
import model.Account;
import model.AccountStatus;
import model.Customer;
import model.Specialty;
import view.utils.CustomerAndAccountCommitter;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class AccountView implements View {
    private final CompositeController compositeController;
    private final AccountBuilderImpl accountBuilder = new AccountBuilderImpl();
    private Scanner sc;

    public AccountView(CompositeController compositeController) {
        this.compositeController = compositeController;
    }

    @Override
    public void main() {
        try {
            sc = new Scanner(System.in);
            System.out.println("\n--Аккаунт--\n" +
                    "Выберите действие:\n" +
                    "1: Добавить аккаунт\n" +
                    "2: Посмотреть аккаунты\n" +
                    "3: Редактировать аккаунт\n" +
                    "4: Удалить аккаунт\n" +
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
            System.out.println("Введены неверные символы " + e);
            main();
        }
    }

    @Override
    public void create() {
        createAllAndSave();
        main();
    }

    private void createAllAndSave() {
        Customer customer = new Customer();
        Account account = createAccount();
        customer.setSpecialties(getSpecialties());
        customer.setAccount(account);
        CustomerAndAccountCommitter.setCompositeController(compositeController);
        CustomerAndAccountCommitter.commitCustomerAndAccount(customer, account, "create");
    }

    private Account createAccount() {
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
        viewAllAccounts();
        System.out.println("Введите любой символ для продолжения...");
        sc.next();
        main();
    }

    private void viewAllAccounts() {
        getAllAccountsAsList().forEach(x ->
                System.out.println(x.getId() + ": | " + x.getName() + " | " + x.getStatus() + "|"));
    }

    private List<Account> getAllAccountsAsList() {
        return ((AccountController) compositeController.getController(new AccountController()))
                .getAll();
    }

    @Override
    public void update() {
        System.out.println("Выберите аккаунт для замены");
        viewAllAccounts();
        int id = sc.nextInt();
        Customer customer = getCustomerByAccountId(id);
        Account account = createAccount();
        account.setId(id);
        customer.setSpecialties(getSpecialties());
        customer.setAccount(account);
        CustomerAndAccountCommitter.setCompositeController(compositeController);
        CustomerAndAccountCommitter.commitCustomerAndAccount(customer, account, "update");
        main();
    }

    private Customer getCustomerByAccountId(int id) {
        Customer customer = new Customer(new HashSet<>(), new Account());
        customer.setId(
                ((CustomerController) compositeController.getController(new CustomerController()))
                        .getAll()
                        .stream()
                        .filter(x -> x.getAccount().getId() == id)
                        .findFirst().orElse(
                        new Customer(new HashSet<>(),new Account("DELETED",AccountStatus.DELETED)))
                        .getId()
        );
        return customer;
    }

    @Override
    public void delete() {
        System.out.println("Выберите аккаунт для удаления: ");
        viewAllAccounts();
        int id = sc.nextInt();
        Customer customer = getCustomerByAccountId(id);
        customer.getAccount().setId(id);
        CustomerAndAccountCommitter.setCompositeController(compositeController);
        CustomerAndAccountCommitter.commitCustomerAndAccount(customer, customer.getAccount(), "delete");
        main();
    }

    @Override
    public String toString() {
        return "Аккаунт";
    }
}
