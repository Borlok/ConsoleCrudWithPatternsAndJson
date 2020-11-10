package view;

import controller.ControllerStrategy;

public class SpecialtyView implements View {
    ControllerStrategy controllerStrategy;

    public SpecialtyView(ControllerStrategy controllerStrategy) {
        this.controllerStrategy = controllerStrategy;
    }

    @Override
    public void main() {

    }

    @Override
    public void create() {

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
        return "Специальности";
    }
}
