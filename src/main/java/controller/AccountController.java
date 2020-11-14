package controller;

import model.Account;
import repository.AccountRepository;
import repository.CompositeRepository;
import repository.io.AccountRepositoryImpl;

import java.util.List;

public class AccountController implements Controller<Account> {
    private CompositeRepository compositeRepository;

    public AccountController() {
    }

    public AccountController(CompositeRepository compositeRepository) {
        this.compositeRepository = compositeRepository;
    }

    @Override
    public Account create(Account account) {
        List<Account> accounts = getAll();
        if (accounts.isEmpty())
            account.setId(1);
        else if (account.getId() == 0)
            account.setId(accounts.get(accounts.size() - 1).getId() + 1);
        return save(account);
    }

    private Account save(Account account) {
        return ((AccountRepository) compositeRepository.getRepository(new AccountRepositoryImpl()))
                .create(account);
    }

    @Override
    public List<Account> getAll() {
        return ((AccountRepository) compositeRepository
                .getRepository(new AccountRepositoryImpl())).getAll();
    }

    @Override
    public Account update(Account account, Integer id) {
        return ((AccountRepository) compositeRepository.getRepository(new AccountRepositoryImpl()))
                .update(account, id);
    }

    @Override
    public void delete(Integer id) {
        compositeRepository.getRepository(new AccountRepositoryImpl()).delete(id);
    }

    @Override
    public String toString() {
        return "Account Controller";
    }
}
