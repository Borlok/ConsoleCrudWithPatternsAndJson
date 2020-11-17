package view.utils;

import controller.AccountController;
import controller.CompositeController;
import controller.Controller;
import controller.CustomerController;
import model.Account;
import model.Customer;

public class CustomerAndAccountCommitter {
    private static CompositeController compositeController;

    public static void setCompositeController(CompositeController compositeController) {
        CustomerAndAccountCommitter.compositeController = compositeController;
    }

    public static void commitCustomerAndAccount(Customer customer, Account account, String action) {
        Controller<Account> accountController =
                ((AccountController) compositeController.getController(new AccountController()));
        Controller<Customer> customerController =
                ((CustomerController) compositeController.getController(new CustomerController()));

        if (action.equalsIgnoreCase("create")) {
            accountController.create(account);
            customerController.create(customer);

        } else if (action.equalsIgnoreCase("update")) {
            accountController.update(account, account.getId());
            customerController.update(customer, customer.getId());

        } else if (action.equalsIgnoreCase("delete")) {
            accountController.delete(account.getId());
            customerController.delete(customer.getId());
        }
    }

}
