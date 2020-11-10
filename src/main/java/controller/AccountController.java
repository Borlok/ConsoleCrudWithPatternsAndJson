package controller;

import model.Account;

public class AccountController implements Controller<Account>{

    @Override
    public Account create() {
        System.out.println("Hell");
        return null;
    }

    public Account read() {
        return null;
    }

    public Account update() {
        return null;
    }

    public void delete() {

    }

    @Override
    public String toString() {
        return "AccountController";
    }
}
