package Service;

import Model.Message;
import DAO.MessageDAO;
import java.util.List;

public class MessageService {
    MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message addMessage(Message message){
        return messageDAO.insertMessage(message);
    }

    public List<Message> getAllMessages(){
        return messageDAO.selectAllMessage();
    }

    public Message getMessageById(int message_id) {
        return messageDAO.selectMessageById(message_id);
    }

    public Message deleteMessageById(int message_id) {
        return messageDAO.deleteMsgById(message_id);
    }

    public Message updateMessageById(int message_id, String message_text) {
        return messageDAO.updateMsgById(message_id, message_text);
    }
    
}