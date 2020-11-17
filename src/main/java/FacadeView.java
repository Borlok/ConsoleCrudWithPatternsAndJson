import controller.AccountController;
import controller.CompositeController;
import controller.CustomerController;
import controller.SpecialtyController;
import repository.CompositeRepository;
import repository.json.JsonAccountRepositoryImpl;
import repository.json.JsonCustomerRepositoryImpl;
import repository.json.JsonSpecialRepositoryImpl;
import view.*;

public class FacadeView {
    private final CompositeRepository compositeRepository = new CompositeRepository();
    private final CompositeController compositeController = new CompositeController();
    private final CompositeView views = new CompositeView();
    private final MainView view = new MainView();

    public void startTheWork() {
        compositeRepository
                .addRepository(new JsonCustomerRepositoryImpl())
                .addRepository(new JsonAccountRepositoryImpl())
                .addRepository(new JsonSpecialRepositoryImpl());

        compositeController
                .addController(new CustomerController(compositeRepository))
                .addController(new AccountController(compositeRepository))
                .addController(new SpecialtyController(compositeRepository));

        views
                .addView(new CustomerView(compositeController))
                .addView(new AccountView(compositeController))
                .addView(new SpecialtyView(compositeController));

        view.addView(views);
        view.main();
    }
}
