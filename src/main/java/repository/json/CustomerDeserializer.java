package repository.json;

import com.google.gson.*;
import model.Customer;
import model.Specialty;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

public class CustomerDeserializer implements JsonDeserializer<Customer> {

    @Override
    public Customer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject cus = json.getAsJsonObject();
        Customer customer = new Customer();

        customer.setId(cus.get("Id").getAsInt());

        JsonArray specialties = cus.getAsJsonArray("Specialties");
        Set<Specialty> specialtySet = new HashSet<>();
        for (JsonElement x : specialties)
            specialtySet.add(new JsonSpecialRepositoryImpl().getById(x.getAsInt()));
        customer.setSpecialties(specialtySet);

        customer.setAccount(new JsonAccountRepositoryImpl().getById(cus.get("AccountId").getAsInt()));
        return customer;
    }
}
