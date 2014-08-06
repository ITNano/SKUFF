package se.matzlarsson.skuff.database;

import java.util.ArrayList;
import java.util.List;

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

}
