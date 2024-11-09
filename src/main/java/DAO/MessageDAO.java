package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    public boolean checkExistOfUser(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT 1 FROM account WHERE account_id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message.getPosted_by());
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return true;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    public Message insertMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            if(message.getMessage_text()!=null && message.getMessage_text()!="" && message.getMessage_text().length()<256 && checkExistOfUser(message)){
                String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                
                preparedStatement.setInt(1, message.getPosted_by());
                preparedStatement.setString(2, message.getMessage_text());
                preparedStatement.setLong(3, message.getTime_posted_epoch());
                preparedStatement.executeUpdate();

                ResultSet rs = preparedStatement.getGeneratedKeys();
                if(rs.next()){
                    message.setMessage_id(rs.getInt(1)); 
                    return message;
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> selectAllMessage(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), 
                                                rs.getInt("posted_by"),
                                                rs.getString("message_text"),
                                                rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }
    
    public Message selectMessageById(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE message_id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                Message message = new Message(rs.getInt("message_id"), 
                                            rs.getInt("posted_by"),
                                            rs.getString("message_text"),
                                            rs.getLong("time_posted_epoch"));
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMsgById(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        Message message = selectMessageById(message_id);
        try {
            if(message!=null){
                String sql = "DELETE FROM message WHERE message_id=?;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, message_id);
                preparedStatement.executeUpdate();
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return message;
    }

    public Message updateMsgById(int message_id, String message_text){
        Connection connection = ConnectionUtil.getConnection();
        try {
            Message message = selectMessageById(message_id);
            if(message!=null && message_text!=null && message_text!="" && message_text.length()<256){
                String sql = "UPDATE message SET message_text=? WHERE message_id=?;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, message_text);
                preparedStatement.setInt(2, message_id);
                preparedStatement.executeUpdate();
                message.setMessage_text(message_text);
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

}
