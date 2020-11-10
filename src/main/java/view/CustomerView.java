package view;

import controller.ControllerStrategy;

public class CustomerView implements View {
    ControllerStrategy meto;

    public CustomerView() {

    }

    public CustomerView(ControllerStrategy meto) {
        this.meto = meto;
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
        return "Покупатели";
    }
}
