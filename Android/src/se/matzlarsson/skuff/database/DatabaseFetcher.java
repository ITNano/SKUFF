package se.matzlarsson.skuff.database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import se.matzlarsson.skuff.database.data.Event;
import se.matzlarsson.skuff.database.data.EventInfo;
import se.matzlarsson.skuff.database.data.EventValue;
import se.matzlarsson.skuff.database.data.News;
import android.database.Cursor;

public class DatabaseFetcher {

	public static List<News> getAllNews(){
		DatabaseHelper db = DatabaseHelper.getInstance();
		String query = "SELECT N._id AS _id, N.uid AS uid, N.header AS header, N.content AS content, N.time AS time, U.name AS username FROM "+DatabaseFactory.TABLE_NEWS+" N LEFT JOIN "+DatabaseFactory.TABLE_USERS+" U ON N.uid = U._id ORDER BY time DESC";
		Cursor c = db.selectQuery(query, null);
		List<News> news = new ArrayList<News>();
		c.moveToFirst();
		while(!c.isAfterLast()){
			news.add(new News(c));
			c.moveToNext();
		}
		
		return news;
	}
	
	public static News getNewsById(String id){
		DatabaseHelper db = DatabaseHelper.getInstance();
		String query = "SELECT N._id AS _id, N.uid AS uid, N.header AS header, N.content AS content, N.time AS time, U.name AS username FROM "+DatabaseFactory.TABLE_NEWS+" N LEFT JOIN "+DatabaseFactory.TABLE_USERS+" U ON N.uid = U._id WHERE N._id = ? ORDER BY time DESC";
		Cursor c = db.selectQuery(query, new String[]{id});
		if(c != null && c.getCount()>0){
			c.moveToFirst();
			return new News(c);
		}else{
			return null;
		}
	}
	
	public static List<Event> getEvents(){
		DatabaseHelper db = DatabaseHelper.getInstance();
		String query = "SELECT _id, date, name, compulsory, time FROM "+DatabaseFactory.TABLE_EVENTS+" ORDER BY date ASC";
		Cursor c = db.selectQuery(query, new String[]{});
		List<Event> events = new ArrayList<Event>();
		c.moveToFirst();
		while(!c.isAfterLast()){
			events.add(new Event(c));
			c.moveToNext();
		}
		
		return events;
	}
	
	public static EventInfo[] getEvents(Date date){
		DatabaseHelper db = DatabaseHelper.getInstance();
		String query = "SELECT _id, date, name, compulsory, time FROM "+DatabaseFactory.TABLE_EVENTS+" WHERE date=? ORDER BY name ASC";
		Cursor c = db.selectQuery(query, new String[]{DateUtil.dateToString(date)});
		EventInfo[] events = new EventInfo[c.getCount()];
		c.moveToFirst();
		for(int i = 0; i<events.length; i++){
			Event event = new Event(c);
			events[i] = new EventInfo(event, getEventValues(event.getId()));
			c.moveToNext();
		}
		
		return events;
	}
	
	public static EventValue[] getEventValues(int id){
		DatabaseHelper db = DatabaseHelper.getInstance();
		String query = "SELECT V._id AS _id, V.eventID AS eventID, V.propertyID AS propertyID, V.value AS value, P.name AS propertyName FROM "+
						DatabaseFactory.TABLE_EVENT_VALUES+" V LEFT JOIN "+DatabaseFactory.TABLE_EVENT_PROPERTIES+" P ON P._id = V.propertyID WHERE V.eventID = ? ORDER BY _id";
		Cursor c = db.selectQuery(query, new String[]{id+""});
		EventValue[] values = new EventValue[c.getCount()];
		c.moveToFirst();
		for(int i = 0; i<values.length; i++){
			values[i] = new EventValue(c);
			c.moveToNext();
		}
		
		return values;
	}

}
