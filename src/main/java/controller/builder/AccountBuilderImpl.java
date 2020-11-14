package controller.builder;

import model.AccountStatus;

public class AccountBuilderImpl extends AccountBuilder{
    @Override
    public void setName(String name) {
        getAccount().setName(name);
    }

    @Override
    public void setStatus(AccountStatus status) {
        getAccount().setStatus(status);
    }
}
