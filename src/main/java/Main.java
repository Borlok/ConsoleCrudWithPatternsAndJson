import controller.AccountController;
import controller.ControllerStrategy;
import view.*;

public class Main {
    public static void main(String[] args) {

        ControllerStrategy controllerStrategy = new ControllerStrategy();
        controllerStrategy.addController(new AccountController());

        CompositeView views = new CompositeView();

        views.addView(
                new CustomerView(
                        controllerStrategy));
        views.addView(
                new AccountView(
                        controllerStrategy));
        views.addView(
                new SpecialtyView(
                        controllerStrategy));

        MainView view = new MainView();
        view.addView(views);
        view.main();

        /**
         * Композит в View добавлен для возможности расширения функционала
         * Для Контроллеров - стратегия
         *
         */
    }
}
