package repository.json;

import com.google.gson.*;
import model.Customer;
import model.Specialty;

import java.lang.reflect.Type;

public class CustomerSerializer implements JsonSerializer<Customer> {
    @Override
    public JsonElement serialize(Customer src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject customer = new JsonObject();

        customer.addProperty("Id",src.getId());
        JsonArray specialties = new JsonArray();
        for (Specialty s : src.getSpecialties()) {
            specialties.add(s.getId());
        }
        customer.add("Specialties",specialties);
        JsonPrimitive account = new JsonPrimitive(src.getAccount().getId());
        customer.add("AccountId", account);

        return customer;
    }
}
