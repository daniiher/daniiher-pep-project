package Service;

import java.util.List;

import DAO.MessageDAO;
import DAO.AccountDAO;

import Model.Message;
import Util.Conversions;
import Model.Account;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService() {
        this.messageDAO = new MessageDAO();
        this.accountDAO = new AccountDAO();
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Integer postMessage(Message msg) {
        if (msg.message_text.length() < 1) return null;
        if (msg.message_text.length() > 254) return null;
        if (!(accountDAO.getAccountById(msg.getPosted_by()) instanceof Account)) return null;
        return messageDAO.insertMessage(msg);
    }
    
    public Message getMessageById(String message_id_raw) {
        Integer id = Conversions.stringToInteger(message_id_raw);
        if (id == null) return null;
        return messageDAO.getMessageById(id.intValue());
    }

    public Message deleteMessageById(String message_id_raw) {
        Integer id = Conversions.stringToInteger(message_id_raw);
        if (id == null) return null;
        return messageDAO.deleteMessageById(id.intValue());
    }

    public Boolean patchMessage(Message msg, String message_id_raw) {
        System.out.print("\n\npatchMessage: msg: " + msg.toString() + "\n\n");
        Integer id = Conversions.stringToInteger(message_id_raw);
        if (id == null) return false;
        if (msg.message_text.length() < 1 | msg.message_text.length() > 254) return false;
        if ( (messageDAO.getMessageById(msg.getMessage_id())).getMessage_id() == 0 ) return false;
        System.out.print("\n\npatchMessage: goto messageDAO\n");
        msg.setMessage_id(id);
        return messageDAO.updateMessageById(msg);
    }

    public List<Message> getAllMessagesFromAccount(String posted_by_raw) {
        Integer id = Conversions.stringToInteger(posted_by_raw);
        if (id == null) return null;
        if (!(accountDAO.getAccountById(id.intValue()) instanceof Account)) return null;
        else return messageDAO.getAllMessagesFromAccount(id.intValue());
    }
}