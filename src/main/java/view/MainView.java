package view;

import java.util.List;
import java.util.Scanner;

public class MainView {
    private CompositeView views;

    public void addView(CompositeView views) {
        this.views = views;
    }

    public void main() {
        try (Scanner sc = new Scanner(System.in)) {
            List<View> allView = views.getListOfView();

            System.out.println("\n--Меню--\n"
                    + "Добро пожаловать, выберите вариант");

            while (true) {
                views.getAllView();
                System.out.println((allView.size() + 1) + ": Выход");
                int choice = sc.nextInt();
                if (choice == (allView.size() + 1))
                    System.exit(0);
                allView.get(choice - 1).main();
            }
        } catch (RuntimeException e) {
            System.err.println("Для начала нужно добавить объект CompositeView"
                    + " в MainViews методом (addViews())");
        }
    }


}
