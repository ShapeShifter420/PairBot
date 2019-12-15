package com.PairBot;

public class Messages {
    enum LANGG{
        RU,EN
    }


    public static final String[][] KMESSEGESS =  new String[][]{
            new String[]{
                    "Море волнуется раз \n",
                    "Море волнуется два \n",
                    "Море волнуется три \n",
                    "Сегодня судьа свела: \n",
            }, new String[]{
            "One \n",
            "Two \n",
            "Three \n",
            "EOE: \n",
    }
    };
    public static final String[] AllreadyInGame = new String[]{"Пользователь уже в игре","Player already in the game"};
    public static final String[] InGame = new String[]{"Теперь вы зарегестрированны","Now you in the game"};
    public static final String[] Top10 = new String[]{"Топ 10  \n","Top 10  \n"};
    public static final String[] times = new String[]{"раз(а) \n","times  \n"};
    public static final String[] NoPlay = new String[]{"Недостаточно игроков(минимум 2)","No players(min 2)"};
    public static final String[] AllreadyWasGame = new String[]{"Пара была уже определена сегодня","Pair already choise"};
    public static final String[] NoPair = new String[]{"Нет Пар","No Pair"};
    public static final String[] HelpMsg = new String[]{"Telegram Бот способный проводить розыгрыши среди зарегестрированных пользователей, собирать статистику и выводить ей в ввиде списка",
            "Telegram bot can make shipper and get statistic"};


}

