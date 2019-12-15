package com.PairBot;

public class Messages {
    enum LANGG{
        RU,EN
    }

    public void Messages(){

    }

    public void changeLang(LANGG lang){
        NOWLANG = lang;
    }

    public String[] getKmessages(){
        return KMESSEGESS[NOWLANG.ordinal()];
    }
    private final String[][] KMESSEGESS =  new String[][]{
            new String[]{
                    "Море волнуется раз \n",
                    "Море волнуется два \n",
                    "Море волнуется три \n",
                    "Сегодня судьа свела: \n",
            }, new String[]{
            "One \n",
            "Море волнуется два \n",
            "Море волнуется три \n",
            "Сегодня судьа свела: \n",
    }
    };
    private final String[] Mas = new String[]{};
    private LANGG NOWLANG = LANGG.RU;

}

