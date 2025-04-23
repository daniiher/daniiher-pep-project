package Controller;

import java.util.List;

import io.javalin.Javalin;
import io.javalin.http.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;
    private static ObjectMapper om = new ObjectMapper();

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerAccountHandler);
        app.post("/login", this::loginAccountHandler);

        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::patchMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesFromAccountHandler);
        return app;
    }

    private void registerAccountHandler(Context ctx) throws JsonProcessingException {
        Account ac = om.readValue(ctx.body(), Account.class);
        System.out.print("\n\nregisterAccountHandler::ac = \n" + ac.toString() + "\n\n");
        Integer account_id = accountService.registerAccount(ac);
        if (account_id != null) {
            ac.setAccount_id(account_id);
            ctx.json(ac);
        }
        else ctx.status(400);
    }
    private void loginAccountHandler(Context ctx) throws JsonProcessingException {
        Account ac = om.readValue(ctx.body(), Account.class);
        Integer account_id = accountService.verifyLoginAccount(ac);
        if (account_id != null) {
            ac.setAccount_id(account_id);
            ctx.json(ac);
        }
        else ctx.status(401);
    }


    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        Message msg = om.readValue(ctx.body(), Message.class);
        Integer message_id = messageService.postMessage(msg);
        if (message_id != null) {
            msg.setMessage_id(message_id);
            ctx.json(msg);
        }
        else ctx.status(400);
    }
    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }
    private void getMessageByIdHandler(Context ctx) throws JsonProcessingException {
        String id_raw = ctx.pathParam("message_id");
        Message found = messageService.getMessageById(id_raw);
        if (found != null) ctx.json(found);
    }
    private void deleteMessageByIdHandler(Context ctx) throws JsonProcessingException {
        String id_raw = ctx.pathParam("message_id");
        Message deleted = messageService.deleteMessageById(id_raw);
        if (deleted != null) ctx.json(deleted);
    }
    private void patchMessageHandler(Context ctx) throws JsonProcessingException {
        Message msg = om.readValue(ctx.body(), Message.class);
        String id_raw = ctx.pathParam("message_id");
        System.out.print("\n\npatchMessageHandler: msg: " + msg.toString() + "\n");
        Boolean patched = messageService.patchMessage(msg, id_raw);
        System.out.print("\n\npatchMessageHandler: patched: " + patched + "\n");
        if (patched == true) ctx.json(msg);
        else ctx.status(400);
    }
    private void getAllMessagesFromAccountHandler(Context ctx) throws JsonProcessingException {
        String posted_by_raw = ctx.pathParam("account_id");
        List<Message> accMessages = messageService.getAllMessagesFromAccount(posted_by_raw);
        if (accMessages != null) ctx.json(accMessages);
    }


}