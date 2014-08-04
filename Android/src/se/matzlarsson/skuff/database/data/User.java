package se.matzlarsson.skuff.database.data;

import se.matzlarsson.skuff.database.DatabaseFactory;
import se.matzlarsson.skuff.database.DatabaseHelper;
import android.database.Cursor;

public class User {
	
	private int id;
	private String name;
	
	public User(){
		this.id = -1;
		this.name = "";
	}
	
	public User(Cursor cursor){
		setId(cursor.getInt(cursor.getColumnIndex("_id")));
		setName(cursor.getString(cursor.getColumnIndex("name")));
	}

	public User(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void saveToDb(DatabaseHelper db){
		db.insertOrUpdateQuery(DatabaseFactory.getTable(DatabaseFactory.TABLE_USERS), new String[]{getId()+"", getName()});
	}
	
	@Override
	public String toString(){
		String s = "USER:[ ";
		s += "ID="+getId()+", ";
		s += "name="+getName();
		s += " ]";
		
		return s;
	}
}
