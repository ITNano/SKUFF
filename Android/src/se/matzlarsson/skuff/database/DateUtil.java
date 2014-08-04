package se.matzlarsson.skuff.database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.util.Log;

public class DateUtil {

	public static String dateToString(Date date){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.GERMANY).format(date);
	}
	
	public static Date stringToDate(String s){
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.GERMANY).parse(s);
		} catch (ParseException e) {
			Log.w("SKUFF", "Invalid date");
		}
		
		return null;
	}

}
