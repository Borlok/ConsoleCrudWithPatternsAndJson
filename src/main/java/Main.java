import controller.AccountController;
import controller.CompositeController;
import controller.CustomerController;
import controller.SpecialtyController;
import repository.CompositeRepository;
import repository.io.AccountRepositoryImpl;
import repository.io.CustomerRepositoryImpl;
import repository.io.SpecialRepositoryImpl;
import view.*;

/**
 * Для возможности расширения функционала применены паттерны:
 * для view - композит
 * для Контроллеров - композит
 * для репозиториев - композит
 * создание объектов происходит билдером
 */

public class Main {


    public static void main(String[] args) {
        CompositeRepository compositeRepository = new CompositeRepository();
        compositeRepository
                .addRepository(new CustomerRepositoryImpl())
                .addRepository(new AccountRepositoryImpl())
                .addRepository(new SpecialRepositoryImpl());

        CompositeController compositeController = new CompositeController();
        compositeController
                .addController(new CustomerController(compositeRepository))
                .addController(new AccountController(compositeRepository))
                .addController(new SpecialtyController(compositeRepository));

        CompositeView views = new CompositeView();
        views
                .addView(new CustomerView(compositeController))
                .addView(new AccountView(compositeController))
                .addView(new SpecialtyView(compositeController));

        MainView view = new MainView();
        view.addView(views);
        view.main();
    }
}
