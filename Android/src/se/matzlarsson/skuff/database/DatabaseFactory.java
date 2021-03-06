package se.matzlarsson.skuff.database;

public class DatabaseFactory {
	
	private static DBTable[] tables;
	
	private static final int VERSION = 21;

	public static final String TABLE_UPDATES = "updates";
	public static final String TABLE_NEWS = "news";
	public static final String TABLE_USERS = "users";
	public static final String TABLE_EVENTS = "events";
	public static final String TABLE_EVENT_PROPERTIES = "eventproperties";
	public static final String TABLE_EVENT_VALUES = "eventvalues";
	public static final String TABLE_GROUPS = "groups";
	public static final String TABLE_GROUP_PROPERTIES = "groupproperties";
	public static final String TABLE_GROUP_VALUES = "groupvalues";
	public static final String TABLE_SAG = "sag";
	public static final String TABLE_CONTEST = "contests";
	public static final String TABLE_CONTEST_QUESTION = "contestquestions";
	public static final String TABLE_CONTEST_OPTION = "contestoptions";
	public static final String TABLE_CONTACTS = "contacts";

	private static void initiateTables(){
		tables = new DBTable[14];
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
		tables[6] = new DBTable(TABLE_GROUPS);
		tables[6].addValue(new DBValue("_id", DBValue.DATATYPE_INT, true, true));
		tables[6].addValue(new DBValue("name", DBValue.DATATYPE_TEXT));
		tables[7] = new DBTable(TABLE_GROUP_PROPERTIES);
		tables[7].addValue(new DBValue("_id", DBValue.DATATYPE_INT, true, true));
		tables[7].addValue(new DBValue("name", DBValue.DATATYPE_TEXT));
		tables[8] = new DBTable(TABLE_GROUP_VALUES);
		tables[8].addValue(new DBValue("_id", DBValue.DATATYPE_INT, true, true));
		tables[8].addValue(new DBValue("groupID", DBValue.DATATYPE_INT));
		tables[8].addValue(new DBValue("propertyID", DBValue.DATATYPE_INT));
		tables[8].addValue(new DBValue("value", DBValue.DATATYPE_TEXT));
		tables[9] = new DBTable(TABLE_SAG);
		tables[9].addValue(new DBValue("_id", DBValue.DATATYPE_INT, true, true));
		tables[9].addValue(new DBValue("name", DBValue.DATATYPE_TEXT));
		tables[9].addValue(new DBValue("post", DBValue.DATATYPE_TEXT));
		tables[9].addValue(new DBValue("birth", DBValue.DATATYPE_TEXT));
		tables[9].addValue(new DBValue("image", DBValue.DATATYPE_TEXT));
		tables[9].addValue(new DBValue("displayOrder", DBValue.DATATYPE_INT));
		tables[10] = new DBTable(TABLE_CONTACTS);
		tables[10].addValue(new DBValue("_id", DBValue.DATATYPE_INT, true, true));
		tables[10].addValue(new DBValue("name", DBValue.DATATYPE_TEXT));
		tables[10].addValue(new DBValue("phone", DBValue.DATATYPE_TEXT));
		tables[10].addValue(new DBValue("mail", DBValue.DATATYPE_TEXT));
		tables[10].addValue(new DBValue("image", DBValue.DATATYPE_TEXT));
		tables[11] = new DBTable(TABLE_CONTEST);
		tables[11].addValue(new DBValue("_id", DBValue.DATATYPE_INT, true, true));
		tables[11].addValue(new DBValue("theme", DBValue.DATATYPE_TEXT));
		tables[11].addValue(new DBValue("price", DBValue.DATATYPE_TEXT));
		tables[11].addValue(new DBValue("ends", DBValue.DATATYPE_TEXT));
		tables[11].addValue(new DBValue("time", DBValue.DATATYPE_TEXT));
		tables[12] = new DBTable(TABLE_CONTEST_QUESTION);
		tables[12].addValue(new DBValue("_id", DBValue.DATATYPE_INT, true, true));
		tables[12].addValue(new DBValue("contestID", DBValue.DATATYPE_INT));
		tables[12].addValue(new DBValue("question", DBValue.DATATYPE_TEXT));
		tables[12].addValue(new DBValue("type", DBValue.DATATYPE_INT));
		tables[13] = new DBTable(TABLE_CONTEST_OPTION);
		tables[13].addValue(new DBValue("_id", DBValue.DATATYPE_INT, true, true));
		tables[13].addValue(new DBValue("questionID", DBValue.DATATYPE_INT));
		tables[13].addValue(new DBValue("option", DBValue.DATATYPE_TEXT));
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
