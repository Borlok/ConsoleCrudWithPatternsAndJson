package repository.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Specialty;
import repository.SpecialtyRepository;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JsonSpecialRepositoryImpl implements SpecialtyRepository {
    private final String FILE_PATH = "./src/main/resources/files/specialty.json";
    private final Path PATH = Paths.get(FILE_PATH);
    private final Gson saverLoader = new GsonBuilder().create();

    @Override
    public Specialty create(Specialty specialty) {
        List<Specialty> specialties = getAll();
        if (isContains(specialty))
            return null;
        specialties.add(specialty);
        saveSpecialtyCollectionToFile(specialties);
        return specialty;
    }

    private void saveSpecialtyCollectionToFile(List<Specialty> specialties) {
        JsonUtils.saveCollectionToJsonFile(specialties,PATH);
    }

    private boolean isContains(Specialty specialty) {
        return getAll().stream()
                .filter(x -> x.getName().equalsIgnoreCase(specialty.getName()))
                .findFirst()
                .orElse(null) != null;
    }

    @Override
    public Specialty getById(Integer id) {
        return getAll().stream().filter(x -> x.getId() == id).findFirst().orElse(isNotInRepository());
    }

    private Specialty isNotInRepository() {
        return new Specialty("DELETED");
    }

    @Override
    public Specialty update(Specialty specialty, Integer id) {
        List<Specialty> specialties = getAll();
        specialties.set(
                specialties.indexOf(specialties.stream()
                        .filter(x -> x.getId() == id)
                        .findFirst()
                        .orElse(isNotInRepository())), specialty);
        saveSpecialtyCollectionToFile(specialties);
        return specialty;
    }

    @Override
    public List<Specialty> getAll() {
        List<Specialty> collection = new ArrayList<>();
        try (FileReader reader = new FileReader(PATH.toFile())) {
            collection = saverLoader.fromJson(reader, new TypeToken<List<Specialty>>() {
            }.getType());
        } catch (IOException | NullPointerException e) {
            System.err.println("Ошибка чтения файла: " + e);
        }
        return collection;
    }

    @Override
    public void delete(Integer id) {
        saveSpecialtyCollectionToFile(
                getAll().stream()
                        .filter(x -> x.getId() != id)
                        .collect(Collectors.toList()));
    }

    @Override
    public String toString() {
        return "JsonSpecialRepositoryImpl";
    }
}
