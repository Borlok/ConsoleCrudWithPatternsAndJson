package controller.builder;

import model.Specialty;

public abstract class SpecialtyBuilder {
    private Specialty specialty;

    public void createSpecialty() {
        specialty = new Specialty();
    }

    public abstract void setName(String name);


    public Specialty getSpecialty() {
        return specialty;
    }
}
