package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * found in readme.md as well as the test cases
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        accountService = new AccountService();
        messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postRegistrationHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessagesHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByUserHandler);
        return app;
    }

    private void postRegistrationHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedaccount = accountService.addAccount(account);
        if(addedaccount==null){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(addedaccount));
        }
    }

    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loggaccount = accountService.checkLogin(account);
        if(loggaccount==null){
            ctx.status(401);
        }else{
            ctx.json(mapper.writeValueAsString(loggaccount));
        }
    }

    private void postMessagesHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedmessage = messageService.addMessage(message);
        if(addedmessage==null){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(addedmessage));
        }
    }

    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException {
        ctx.json(messageService.getAllMessages());
    }

    private void getMessageByIdHandler(Context ctx) throws JsonProcessingException {
        Message message = messageService.getMessageById(Integer.parseInt(ctx.pathParam("message_id")));
        if(message==null){
            ctx.status(200);
        }
        else{
            ctx.json(message);
        }
    }

    private void deleteMessageByIdHandler(Context ctx) throws JsonProcessingException {
        Message message = messageService.deleteMessageById(Integer.parseInt(ctx.pathParam("message_id")));
        if(message==null){
            ctx.status(200);
        }
        else{
            ctx.json(message);
        }
    }

    private void updateMessageByIdHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message ms = mapper.readValue(ctx.body(), Message.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        String message_text = ms.getMessage_text(); 

        Message message = messageService.updateMessageById(message_id, message_text);
        if(message==null){
            ctx.status(400);
        }
        else{
            ctx.json(message);
        }
    }

    private void getAllMessagesByUserHandler(Context ctx) throws JsonProcessingException {
        ctx.json(accountService.getAllMessagesByUser(Integer.parseInt(ctx.pathParam("account_id"))));
    }
    
}