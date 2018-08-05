package com.wegrzyn.marcin.fuelbills;

import java.util.Locale;

/**
 * Created by Marcin Węgrzyn on 30.07.2018.
 * wireamg@gmail.com
 */
class Utils {
    static String checkNum(String s){
        if(s==null||s.length()==0) return "0";
        else return s;
    }
    static String numberFormat(float number){
        return String.format(Locale.US,"%.2f",number);
    }
}
