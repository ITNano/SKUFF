package se.matzlarsson.skuff.database.data.group;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class GroupValueDeserializer implements JsonDeserializer<GroupValue[] >{

	@Override
	public GroupValue[] deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		JsonArray arr = json.getAsJsonArray();
		GroupValue[] allValues = new GroupValue[arr.size()];
		
		JsonObject obj = null;
		GroupValue value = null;
		for(int i = 0; i<arr.size(); i++){
			obj = arr.get(i).getAsJsonObject();
			value = new GroupValue();
			value.setId(obj.get("id").getAsInt());
			value.setGroupID(obj.get("groupID").getAsInt());
			value.setPropertyID(obj.get("propertyID").getAsInt());
			value.setValue(obj.get("value").getAsString());
			allValues[i] = value;
		}
		return allValues;
	}

}
