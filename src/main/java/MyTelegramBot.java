import org.checkerframework.checker.index.qual.PolyUpperBound;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.ws.rs.PUT;
import java.io.IOException;

public class MyTelegramBot extends TelegramLongPollingBot {

    public static final String BOT_TOKEN = "5614021549:AAFJBEHTml4IUVDUPoK-vIKK6wkC8i4n4d8";
    public static final String BOT_USERNAME = "double_bebra_bot";
    public static final String URI = "https://api.nasa.gov/planetary/apod?api_key=ZTHaBiPNse2w1kbW0qJs30MJMARjbVkPiKqpVnLa";
    public static long chat_id;

    public MyTelegramBot() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }
    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()){
            chat_id = update.getMessage().getChatId();
        }

        switch (update.getMessage().getText()){
            case "/help":
                sendMessage("Привет, я двойная бебра бот. Отправлю тебе картинку NASA!! Каждый день новая картинка!!!");
                break;
            case "/give":
                try{
                    sendMessage(Utils.getUrl(URI));
                }catch (IOException e){
                    throw new RuntimeException(e);
                }
                break;
            default:
                sendMessage("Я в душе не ебу че ты высрал");
                break;
        }
    }

    private void sendMessage(String messageText){
        SendMessage message = new SendMessage();
        message.setChatId(chat_id);
        message.setText(messageText);
        try {
            execute(message);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

}
