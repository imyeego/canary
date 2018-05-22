package com.example.administrator.accountbook;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/12/20 0020.
 */

public class TimeTool {

    public static String currentTime(String format){
        String time = "";
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        time = dateFormat.format(date);
        return time;
    }
}
