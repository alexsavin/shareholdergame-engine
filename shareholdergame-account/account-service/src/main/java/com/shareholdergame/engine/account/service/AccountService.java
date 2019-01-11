package com.shareholdergame.engine.account.service;

import java.time.LocalDate;
import javax.inject.Inject;

import com.shareholdergame.engine.account.api.AccountOperations;
import com.shareholdergame.engine.account.dao.AccountDao;
import com.shareholdergame.engine.account.model.Account;
import com.shareholdergame.engine.account.model.AccountStatus;
import com.shareholdergame.engine.account.model.AccountWithPassword;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.exceptions.HttpStatusException;

@Controller("/account")
public class AccountService implements AccountOperations {

    @Inject
    private AccountDao accountDao;

    @Override
    public boolean checkUserExistence(String userNameOrEmail) {
        return null != accountDao.checkUserExistence(userNameOrEmail);
    }

    @Override
    public AccountWithPassword findUserByNameOrEmail(String userNameOrEmail) {
        AccountWithPassword userAccount = accountDao.findByUserNameOrEmail(userNameOrEmail);
        if (userAccount == null) {
            throw new HttpStatusException(HttpStatus.NOT_FOUND, userNameOrEmail);
        }
        return userAccount;
    }

    @Override
    public void createAccount(String userName, String email, String password) {
        accountDao.insertAccount(AccountWithPassword.builder()
            .withAccount(Account.builder()
                .withUserName(userName)
                .withEmail(email)
                .withStatus(AccountStatus.NEW)
                .withCreationDate(LocalDate.now())
                .build())
            .withPassword(password).build());
    }
}
