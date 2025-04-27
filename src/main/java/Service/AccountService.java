package Service;

import DAO.AccountDAO;

import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    public Integer registerAccount(Account ac) {
        if (ac.username.length() < 1) return null;
        if (ac.password.length() < 5) return null;
        if (accountDAO.getAccountByUsername(ac.username) instanceof Account) {
            return null;
        }
        return accountDAO.insertAccount(ac);
    }

    public Integer verifyLoginAccount(Account ac) {
        return accountDAO.verifyLoginAccount(ac);
    }
}