package se.matzlarsson.skuff.database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import android.annotation.SuppressLint;
import android.util.Log;

public class DateUtil {

	public static String dateToString(Date date){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.GERMANY).format(date);
	}
	
	public static String dateToUIString(Date date){
		String[] months = {"Jan", "Feb", "Mar", "Apr", "Maj", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"};
		String dateString = new SimpleDateFormat("d", Locale.GERMANY).format(date);
		dateString += " "+months[date.getMonth()]+" ";
		dateString += new SimpleDateFormat("HH:mm", Locale.GERMANY).format(date);
		return dateString;
	}
	
	public static Date stringToDate(String s){
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.GERMANY).parse(s);
		} catch (ParseException pe1) {
			try {
				return new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY).parse(s);
			}catch(ParseException pe2){
				Log.w("SKUFF", "Invalid date ("+s+")");
			}
		}
		
		return null;
	}

	@SuppressLint("SimpleDateFormat")
	public static String getNow(){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance(Locale.GERMANY).getTime());
	}
}
