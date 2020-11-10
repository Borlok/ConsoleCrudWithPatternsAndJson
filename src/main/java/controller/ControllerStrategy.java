package controller;

import java.util.ArrayList;
import java.util.List;

public class ControllerStrategy {
    List<Controller<?>> controllers = new ArrayList<>();

    public void addController(Controller<?> controller) {
        controllers.add(controller);
    }

    public Controller<?> getController(Controller<?> controller) {
        return controllers.stream().filter(x -> x.toString().equals(controller.toString())).findFirst().orElse(null);
    }

}
