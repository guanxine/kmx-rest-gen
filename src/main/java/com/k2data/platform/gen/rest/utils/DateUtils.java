package com.k2data.platform.gen.rest.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Created by guanxine on 18-4-4.
 */
public class DateUtils {
	public final static String DATE_PATTERN = "yy-MM-dd";
	
	public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }
}
