package Service;

import Model.Account;
import Model.Message;
import DAO.AccountDAO;
import java.util.List;

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

    public List<Message> getAllMessagesByUser(int account_id){
        return accountDAO.selectAllMessageByUser(account_id);
    }
}