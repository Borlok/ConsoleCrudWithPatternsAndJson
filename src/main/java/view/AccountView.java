package view;

import controller.AccountController;
import controller.ControllerStrategy;

import java.util.Scanner;

public class AccountView implements View {
    ControllerStrategy controllerStrategy;
    private final Scanner sc = new Scanner(System.in);

    public AccountView(ControllerStrategy controllerStrategy) {
        this.controllerStrategy = controllerStrategy;
    }

    @Override
    public void main() {
        try {
            int choice;
            System.out.println("\n--Аккаунт--\n" +
                    "Выберите действие:\n" +
                    "1: Добавить аккаунт\n" +
                    "2: Посмотреть аккаунты\n" +
                    "3: Редактировать аккаунт\n" +
                    "4: Удалить аккаунт\n" +
                    "5: Назад");
            choice = sc.nextInt();
            while (choice != 5)
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
            System.out.println("Введены неверные символы");
            main();
        } finally {
            sc.close();
        }
    }

    @Override
    public void create() {
        controllerStrategy.getController(new AccountController()).create();
    }

    @Override
    public void read() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }

    @Override
    public String toString() {
        return "Аккаунт";
    }
}
