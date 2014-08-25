package se.matzlarsson.skuff.database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import se.matzlarsson.skuff.database.data.event.Event;
import se.matzlarsson.skuff.database.data.event.EventInfo;
import se.matzlarsson.skuff.database.data.event.EventValue;
import se.matzlarsson.skuff.database.data.group.Group;
import se.matzlarsson.skuff.database.data.group.GroupInfo;
import se.matzlarsson.skuff.database.data.group.GroupValue;
import se.matzlarsson.skuff.database.data.news.News;
import se.matzlarsson.skuff.database.data.sag.SAG;
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
	
	public static Group[] getGroups(){
		DatabaseHelper db = DatabaseHelper.getInstance();
		String query = "SELECT _id, name FROM "+DatabaseFactory.TABLE_GROUPS+" ORDER BY name ASC";
		Cursor c = db.selectQuery(query, new String[]{});
		Group[] groups = new Group[c.getCount()];
		c.moveToFirst();
		int num = 0;
		while(!c.isAfterLast()){
			groups[num] = new Group(c);
			c.moveToNext();
			num++;
		}
		
		return groups;
	}
	
	public static GroupInfo getGroupInfo(int id){
		DatabaseHelper db = DatabaseHelper.getInstance();
		String query = "SELECT _id, name FROM "+DatabaseFactory.TABLE_GROUPS+" WHERE _id=? ORDER BY name ASC LIMIT 1";
		Cursor c = db.selectQuery(query, new String[]{id+""});
		c.moveToFirst();
		Group group = new Group(c);
		return new GroupInfo(group, getGroupValues(group.getId()));
	}
	
	public static GroupValue[] getGroupValues(int id){
		DatabaseHelper db = DatabaseHelper.getInstance();
		String query = "SELECT V._id AS _id, V.groupID AS groupID, V.propertyID AS propertyID, V.value AS value, P.name AS propertyName FROM "+
						DatabaseFactory.TABLE_GROUP_VALUES+" V LEFT JOIN "+DatabaseFactory.TABLE_GROUP_PROPERTIES+" P ON P._id = V.propertyID WHERE V.groupID = ? ORDER BY _id";
		Cursor c = db.selectQuery(query, new String[]{id+""});
		GroupValue[] values = new GroupValue[c.getCount()];
		c.moveToFirst();
		for(int i = 0; i<values.length; i++){
			values[i] = new GroupValue(c);
			c.moveToNext();
		}
		
		return values;
	}
	
	public static SAG[] getSAGMembers(){
		DatabaseHelper db = DatabaseHelper.getInstance();
		String query = "SELECT _id,name,post,birth,image,displayOrder FROM "+DatabaseFactory.TABLE_SAG+" ORDER BY displayOrder ASC,name ASC";
		Cursor c = db.selectQuery(query, new String[]{});
		SAG[] sag = new SAG[c.getCount()];
		c.moveToFirst();
		int num = 0;
		while(!c.isAfterLast()){
			sag[num] = new SAG(c);
			c.moveToNext();
			num++;
		}
		
		return sag;
	}

}
