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
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
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
        app.post("/messages", this::postMessages);
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

    private void postMessages(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedmessage = messageService.addMessage(message);
        if(addedmessage==null){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(addedmessage));
        }
    }

}