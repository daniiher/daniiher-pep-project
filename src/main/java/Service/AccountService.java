package Service;

import DAO.AccountDAO;

import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    public Account registerAccount(Account ac) {
        System.out.print("\n\nregisterAccount(ac):\nac.username: " + ac.getUsername() + " \nas.password: " + ac.getPassword() + "\n\n");
        if (ac.username.length() < 1) return null;
        if (ac.password.length() < 5) return null;
        if (accountDAO.getAccountByUsername(ac.username) instanceof Account) {
            System.out.print("\n\nservice.registerAccount::\n(accountDAO.getAccountByUsername() instance of Account) == true\nduplicate username -> return null;\n\n");
            return null;
        }
        return accountDAO.insertAccount(ac);
    }

    public Account verifyLoginAccount(Account ac) {
        return accountDAO.verifyLoginAccount(ac);
    }
}