package se.matzlarsson.skuff.database;

public class DatabaseFactory {
	
	private static DBTable[] tables;

	public static final String TABLE_UPDATES = "updates";
	public static final String TABLE_NEWS = "news";
	public static final String TABLE_USERS = "users";

	private static void initiateTables(){
		tables = new DBTable[3];
		tables[0] = new DBTable("updates");
		tables[0].addValue(new DBValue("_id", DBValue.DATATYPE_INT, true, true));
		tables[0].addValue(new DBValue("time", DBValue.DATATYPE_TEXT));
		tables[1] = new DBTable("users");
		tables[1].addValue(new DBValue("_id", DBValue.DATATYPE_INT, true, true));
		tables[1].addValue(new DBValue("name", DBValue.DATATYPE_TEXT));
		tables[2] = new DBTable("news");
		tables[2].addValue(new DBValue("_id", DBValue.DATATYPE_INT, true, true));
		tables[2].addValue(new DBValue("uid", DBValue.DATATYPE_INT));
		tables[2].addValue(new DBValue("header", DBValue.DATATYPE_TEXT));
		tables[2].addValue(new DBValue("content", DBValue.DATATYPE_TEXT));
		tables[2].addValue(new DBValue("time", DBValue.DATATYPE_TEXT));
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

}
