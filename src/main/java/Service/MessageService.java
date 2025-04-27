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
    
    public Message getMessageById(String msg_id_raw) {
        Integer msg_id = Conversions.stringToInteger(msg_id_raw);
        if (msg_id == null || msg_id == 0) return null;
        return messageDAO.getMessageById(msg_id);
    }

    public Message deleteMessageById(String msg_id_raw) {
        Message currentMsg = getMessageById(msg_id_raw);
        if (currentMsg == null) return null;
        if (messageDAO.deleteMessageById(currentMsg.getMessage_id())) return currentMsg;
        return null;
    }

    public Message patchMessage(String msg_text, String msg_id_raw) {
        if (msg_text == null || msg_text.length() < 1 || msg_text.length() > 254) return null;

        Message currentMsg = getMessageById(msg_id_raw);
        if (currentMsg == null) return null;
        int msg_id = currentMsg.getMessage_id();

        if (messageDAO.updateMessageById(msg_text, msg_id)) {
            currentMsg.setMessage_id(msg_id);
            currentMsg.setMessage_text(msg_text);
            return currentMsg;
        }
        return null;
    }

    public List<Message> getAllMessagesFromAccount(String posted_by_raw) {
        Integer posted_by_id = Conversions.stringToInteger(posted_by_raw);
        if (posted_by_id == null || posted_by_id == 0) return null;
        return messageDAO.getAllMessagesFromAccount(posted_by_id);
    }
}