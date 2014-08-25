package se.matzlarsson.skuff.database.data;

import se.matzlarsson.skuff.database.DatabaseFactory;
import se.matzlarsson.skuff.database.DatabaseHelper;
import android.database.Cursor;

public class EventProperty implements Saveable{

	private int id;
	private String name;
	
	public EventProperty() {
		setId(-1);
		setName("");
	}
	
	public EventProperty(Cursor cursor){
		this.setId(cursor.getInt(cursor.getColumnIndex("_id")));
		this.setName(cursor.getString(cursor.getColumnIndex("name")));
	}
	
	public EventProperty(int id, String name){
		setId(id);
		setName(name);
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

	@Override
	public void saveToDb(DatabaseHelper db){
		db.insertOrUpdateQuery(DatabaseFactory.getTable(DatabaseFactory.TABLE_EVENT_PROPERTIES), new String[]{getId()+"", getName()});
	}

	@Override
	public String toString(){
		String s = "EVENTPROPERTY:[ ";
		s += "ID="+getId()+", ";
		s += "name="+getName()+" ]";
		
		return s;
	}

}
