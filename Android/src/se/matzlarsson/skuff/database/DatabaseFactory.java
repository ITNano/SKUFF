package se.matzlarsson.skuff.database;

public class DatabaseFactory {
	
	private static DBTable[] tables;
	
	private static final int VERSION = 5;

	public static final String TABLE_UPDATES = "updates";
	public static final String TABLE_NEWS = "news";
	public static final String TABLE_USERS = "users";
	public static final String TABLE_EVENTS = "events";
	public static final String TABLE_EVENT_PROPERTIES = "eventproperties";
	public static final String TABLE_EVENT_VALUES = "eventvalues";

	private static void initiateTables(){
		tables = new DBTable[6];
		tables[0] = new DBTable(TABLE_UPDATES);
		tables[0].addValue(new DBValue("_id", DBValue.DATATYPE_INT, true, true));
		tables[0].addValue(new DBValue("time", DBValue.DATATYPE_TEXT));
		tables[1] = new DBTable(TABLE_USERS);
		tables[1].addValue(new DBValue("_id", DBValue.DATATYPE_INT, true, true));
		tables[1].addValue(new DBValue("name", DBValue.DATATYPE_TEXT));
		tables[2] = new DBTable(TABLE_NEWS);
		tables[2].addValue(new DBValue("_id", DBValue.DATATYPE_INT, true, true));
		tables[2].addValue(new DBValue("uid", DBValue.DATATYPE_INT));
		tables[2].addValue(new DBValue("header", DBValue.DATATYPE_TEXT));
		tables[2].addValue(new DBValue("content", DBValue.DATATYPE_TEXT));
		tables[2].addValue(new DBValue("time", DBValue.DATATYPE_TEXT));
		tables[3] = new DBTable(TABLE_EVENTS);
		tables[3].addValue(new DBValue("_id", DBValue.DATATYPE_INT, true, true));
		tables[3].addValue(new DBValue("date", DBValue.DATATYPE_TEXT));
		tables[3].addValue(new DBValue("name", DBValue.DATATYPE_TEXT));
		tables[3].addValue(new DBValue("compulsory", DBValue.DATATYPE_INT));
		tables[3].addValue(new DBValue("time", DBValue.DATATYPE_TEXT));
		tables[4] = new DBTable(TABLE_EVENT_PROPERTIES);
		tables[4].addValue(new DBValue("_id", DBValue.DATATYPE_INT, true, true));
		tables[4].addValue(new DBValue("name", DBValue.DATATYPE_TEXT));
		tables[5] = new DBTable(TABLE_EVENT_VALUES);
		tables[5].addValue(new DBValue("_id", DBValue.DATATYPE_INT, true, true));
		tables[5].addValue(new DBValue("eventID", DBValue.DATATYPE_INT));
		tables[5].addValue(new DBValue("propertyID", DBValue.DATATYPE_INT));
		tables[5].addValue(new DBValue("value", DBValue.DATATYPE_TEXT));
	}
	
	public static DBTable[] getTables(){
		if(tables == null){
			initiateTables();
		}
		
		return tables;
	}
	
	public static DBTable getTable(String name){
		if(tables == null){
			initiateTables();
		}

		for(int i = 0; i<tables.length; i++){
			if(tables[i].getName().equals(name)){
				return tables[i];
			}
		}

		return null;
	}
	
	public static int getVersion(){
		return VERSION;
	}

}
