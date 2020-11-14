package repository.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Account;
import model.AccountStatus;
import repository.AccountRepository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryImpl implements AccountRepository {
    private final String FILE_PATH = "./src/main/resources/files/accounts.json";
    private final Path PATH = Paths.get(FILE_PATH);
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public Account create(Account account) {
        List<Account> accounts = getAll();
        accounts.add(account);
        saveAccountsCollectionToFile(accounts);
        return account;
    }

    private void saveAccountsCollectionToFile(List<Account> accounts) {
        try (FileWriter writer = new FileWriter(PATH.toFile())) {
            gson.toJson(accounts, writer);
        } catch (IOException e) {
            System.err.println("Ошибка при записи аккаунта в файл");
        }
    }

    @Override
    public Account getById(Integer id) {
        return getAll()
                .stream()
                .filter(x -> x.getId() == id)
                .findFirst()
                .orElse(isNotInRepository());
    }

    @Override
    public Account update(Account account, Integer id) {
        List<Account> accounts = getAll();
        accounts.set(
                accounts.indexOf(accounts.stream()
                        .filter(x -> x.getId() == id)
                        .findFirst()
                        .orElse(isNotInRepository())), account);
        saveAccountsCollectionToFile(accounts);
        return account;
    }

    private Account isNotInRepository() {
        return new Account("DELETED", AccountStatus.DELETED);
    }

    @Override
    public List<Account> getAll() {
        List<Account> accounts = new ArrayList<>();
        try (FileReader reader = new FileReader(PATH.toFile())) {
            accounts = gson.fromJson(reader, new TypeToken<List<Account>>() {
            }.getType());
        } catch (IOException | NullPointerException e) {
            System.err.println("Ошибка чтения файла аккаунта: " + e);
        }
        return accounts;
    }


    @Override
    public void delete(Integer id) {
        List<Account> accounts = getAll();
        Account account = isNotInRepository();
        account.setId(id);
        accounts.set(accounts.indexOf(accounts.stream()
                .filter(x -> x.getId() == id)
                .findFirst()
                .orElse(isNotInRepository())), account);
        saveAccountsCollectionToFile(accounts);
    }

    @Override
    public String toString() {
        return "AccountRepository";
    }
}
