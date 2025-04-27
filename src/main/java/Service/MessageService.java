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

    public Message patchMessage(String msg_text, String msg_id_raw) {
        Integer msg_id = Conversions.stringToInteger(msg_id_raw);
        if (msg_id == null || msg_id == 0) return null;
        if (msg_text == null || msg_text.length() < 1 || msg_text.length() > 254) return null;
        Message currentMsg = messageDAO.getMessageById(msg_id);
        if (currentMsg == null) return null;
        if (messageDAO.updateMessageById(msg_text, msg_id)) {
            currentMsg.setMessage_id(msg_id);
            currentMsg.setMessage_text(msg_text);
            return currentMsg;
        } else {
            return null;
        }
    }

    public List<Message> getAllMessagesFromAccount(String posted_by_raw) {
        Integer id = Conversions.stringToInteger(posted_by_raw);
        if (id == null) return null;
        if (!(accountDAO.getAccountById(id.intValue()) instanceof Account)) return null;
        else return messageDAO.getAllMessagesFromAccount(id.intValue());
    }
}