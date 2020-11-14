package controller;

import model.Specialty;
import repository.CompositeRepository;
import repository.SpecialtyRepository;
import repository.io.SpecialRepositoryImpl;

import java.util.List;

public class SpecialtyController implements Controller<Specialty> {
    private CompositeRepository compositeRepository;

    public SpecialtyController() {
    }

    public SpecialtyController(CompositeRepository compositeRepository) {
        this.compositeRepository = compositeRepository;
    }

    @Override
    public Specialty create(Specialty specialty) {
        List<Specialty> specialties = getAll();
        if (specialties.isEmpty())
            specialty.setId(1);
        else if (specialty.getId() == 0)
            specialty.setId(specialties.get(specialties.size() - 1).getId() + 1);
        return save(specialty);
    }

    private Specialty save(Specialty specialty) {
        return ((SpecialtyRepository) compositeRepository
                .getRepository(new SpecialRepositoryImpl())).create(specialty);
    }

    @Override
    public List<Specialty> getAll() {
        return ((SpecialtyRepository) compositeRepository
                .getRepository(new SpecialRepositoryImpl())).getAll();
    }

    @Override
    public Specialty update(Specialty specialty, Integer id) {
        specialty.setId(id);
        return ((SpecialtyRepository) compositeRepository
                .getRepository(new SpecialRepositoryImpl())).update(specialty, id);
    }

    @Override
    public void delete(Integer id) {
        compositeRepository.getRepository(new SpecialRepositoryImpl()).delete(id);
    }

    @Override
    public String toString() {
        return "Specialty Controller";
    }
}
