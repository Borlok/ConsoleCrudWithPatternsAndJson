package repository.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Customer;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class JsonUtils {
    private static final Gson saverLoader = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Customer.class, new CustomerSerializer())
            .create();

    public static <T> void saveCollectionToJsonFile(List<T> collection, Path path) {
        try (FileWriter writer = new FileWriter(path.toFile())) {
            saverLoader.toJson(collection, writer);
        } catch (IOException | NullPointerException e) {
            System.err.println("Ошибка при записи в файл");
        }
    }
}
