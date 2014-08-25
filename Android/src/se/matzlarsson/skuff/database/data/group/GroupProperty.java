package se.matzlarsson.skuff.database.data.group;

import se.matzlarsson.skuff.database.DatabaseFactory;
import se.matzlarsson.skuff.database.DatabaseHelper;
import se.matzlarsson.skuff.database.data.Saveable;
import android.database.Cursor;

public class GroupProperty implements Saveable{

	private int id;
	private String name;
	
	public GroupProperty() {
		setId(-1);
		setName("");
	}
	
	public GroupProperty(Cursor cursor){
		this.setId(cursor.getInt(cursor.getColumnIndex("_id")));
		this.setName(cursor.getString(cursor.getColumnIndex("name")));
	}
	
	public GroupProperty(int id, String name){
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
		db.insertOrUpdateQuery(DatabaseFactory.getTable(DatabaseFactory.TABLE_GROUP_PROPERTIES), new String[]{getId()+"", getName()});
	}

	@Override
	public String toString(){
		String s = "GROUPPROPERTY:[ ";
		s += "ID="+getId()+", ";
		s += "name="+getName()+" ]";
		
		return s;
	}

}
