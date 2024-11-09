package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public Account addAccount(Account account){
        return accountDAO.insertAccount(account);
    }

    public Account checkLogin(Account account){
        return accountDAO.chLogin(account);
    }
}