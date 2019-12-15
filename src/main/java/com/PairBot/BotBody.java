package com.PairBot;

import org.apache.commons.codec.language.bm.Lang;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;


import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.*;

public class BotBody extends TelegramLongPollingBot {
    public static void main(String[] args){
        ApiContextInitializer.init();
        TelegramBotsApi bot = new TelegramBotsApi();
        try {
            bot.registerBot(new BotBody());
        } catch(TelegramApiException e){
            e.printStackTrace();
        }
    }

    String HelpMsg = "Telegram Бот способный проводить розыгрыши среди зарегестрированных пользователей, собирать статистику и выводить ей в ввиде списка";

    private void sendMsg(Long id, String text,String[] typeOfKeyBoard){
        List<List<InlineKeyboardButton>> keyboardButtons = new ArrayList<>();
        for (int i = 0;i<typeOfKeyBoard.length;i++) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(typeOfKeyBoard[i]);
            inlineKeyboardButton.setCallbackData(typeOfKeyBoard[i]);
            keyboardButtons.add(new ArrayList());
            keyboardButtons.get(i).add(inlineKeyboardButton);
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(id);
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(new InlineKeyboardMarkup().setKeyboard(keyboardButtons));
        try{
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void sendMsg(Long id, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(id);
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()){
            Message message = update.getMessage();
            Long chatId = update.getMessage().getChatId();
            if (message != null && message.hasText()) {
                switch (message.getText()) {
                    case "/shipper@PairBot":
                        makeShipper(chatId);
                        break;
                    case "/help@PairBot":
                        System.out.println(1);
                        sendMsg(chatId, HelpMsg, COMMAND);
                        break;
                    case "/stat@PairBot":
                        makeStat(chatId);
                        break;
                    case "/reg@PairBot":
                        regUser(chatId, update.getMessage().getFrom());
                        break;
                    case "/setlang@PairBot":
                        sendMsg(chatId, "Chose lang", new String[]{"setRU \uD83C\uDDF7\uD83C\uDDFA", "setEN \uD83C\uDDFA\uD83C\uDDF8"});
                        break;
                    default:
                        break;
                }
            }


        }
        else if(update.hasCallbackQuery()){
            CallbackQuery data = update.getCallbackQuery();
            Long chatId = data.getMessage().getChatId();
            String message = data.getData();
            switch (message){
                case "setRU \uD83C\uDDF7\uD83C\uDDFA":
                    mes.changeLang(Messages.LANGG.RU);
                    break;
                case "setEN \uD83C\uDDFA\uD83C\uDDF8":
                    mes.changeLang(Messages.LANGG.EN);
                    break;
                default:
                    break;
            }
        }
    }

    public void makeShipper(Long id) {
        SqlBase database = new SqlBase("all_chat");
        List<BaseUser> players = database.getUsers(id);
        if (players.size() < 2) {
            sendMsg(id,"Недостаточно игроков(минимум 2)");
            return;
        }
        if (database.allreadyRunning(id, getToday())) {
            sendMsg(id,"Пара была уже определена сегодня");
            return;
        }
        int[] winners = getWinners(players.size());
        BaseUser winner1 = players.get(Math.min(winners[0], winners[1]));
        BaseUser winner2 = players.get(Math.max(winners[0], winners[1]));
        database.setPair(id,winner1,winner2,getToday());
        Thread myThready = new Thread(new Runnable() {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                try {
                    sendWin(id,winner1,winner2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        myThready.start();    //Запуск потока
        sendMsg(id, "@"+winner1.USERNAME + " + " + "@" + winner2.USERNAME);
        database.close();
    }
    public void sendWin(Long id, BaseUser winner1,BaseUser winner2) throws InterruptedException {
        for (int i=0; i<mes.getKmessages().length;i++) {
            sendMsg(id, mes.getKmessages()[i]);
            Thread.sleep(1500);
        }
        sendMsg(id, "@"+winner1.USERNAME + " + " + "@" + winner2.USERNAME);
    }
    public int[] getWinners(int size){
        int i1 = new Random().nextInt(size);
        int i2 = new Random().nextInt(size);
        while(i1 == i2)
        {
            i2 = new Random().nextInt(size);
        }
        return new int[]{i1,i2};
    }

    public void makeStat(Long id)
    {
        SqlBase database = new SqlBase("all_chat");
        HashMap<String,Integer> dict = database.getTopPair(id);
        String stat = "Топ 10 пар: \n";
        if (dict.size()!=0) {
            int i = 1;
            for (String p : dict.keySet()) {
                stat += i + ")" + p + " : " + dict.get(p) + "раз(а) \n";
                i++;
            }
        }
        else stat += "Нет пар";
        sendMsg(id,stat);
    }

    public void regUser(Long id, User user){
        SqlBase database = new SqlBase("all_chat");
        if (!database.registration(id, user)) {
            sendMsg(id,"Пользователь уже зарегестрирован");
        }
        else{
            sendMsg(id,"Теперь вы зарегестрированны");
        }
        database.close();
    }

    public String getToday()
    {
        Date date = new Date();
        SimpleDateFormat today = new SimpleDateFormat("dd.MM.yyyy");
        return today.format(date);
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
         return TOKEN;
    }

    private final String BOT_USERNAME = "PairBot";
    private final String TOKEN = "811617518:AAFRsmc6wupDPUxt63AJPmeBEBUaEsd_hnM";
    private Messages mes = new Messages();
    private final String[] COMMAND = new String[] {"/shipper@PairBot","/reg@PairBot", "/stat@PairBot"};
}
