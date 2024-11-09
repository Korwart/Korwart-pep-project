package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

public class AccountDAO {

    public boolean checkExistOfAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            if(account.getUsername()!=null && account.getUsername()!=""){
                String sql = "SELECT 1 FROM account WHERE username=?;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, account.getUsername());
                ResultSet rs = preparedStatement.executeQuery();
                if(rs.next()){
                    return true;
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            if(account.getUsername()!=null && account.getUsername()!="" && account.getPassword().length()>3 && !checkExistOfAccount(account)){
                String sql = "INSERT INTO account (username, password) VALUES (?, ?);";
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                
                preparedStatement.setString(1, account.getUsername());
                preparedStatement.setString(2, account.getPassword());

                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if(rs.next()){
                    account.setAccount_id(rs.getInt(1)); 
                    return account;
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account chLogin(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            if(account.getUsername()!=null && account.getUsername()!="" && account.getPassword()!=null && account.getPassword()!=""){
                String sql = "SELECT account_id FROM account WHERE username=? AND password=?;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, account.getUsername());
                preparedStatement.setString(2, account.getPassword());
                ResultSet rs = preparedStatement.executeQuery();
                if(rs.next()){
                    account.setAccount_id(rs.getInt(1)); 
                    return account;
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

}
