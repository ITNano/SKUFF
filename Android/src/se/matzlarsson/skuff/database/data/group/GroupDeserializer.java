package se.matzlarsson.skuff.database.data.group;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class GroupDeserializer implements JsonDeserializer<Group[]> {

	@Override
	public Group[] deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		JsonArray arr = json.getAsJsonArray();
		Group[] allGroups = new Group[arr.size()];
		
		JsonObject obj = null;
		Group group = null;
		for(int i = 0; i<arr.size(); i++){
			obj = arr.get(i).getAsJsonObject();
			group = new Group();
			group.setId(obj.get("id").getAsInt());
			group.setName(obj.get("name").getAsString());
			allGroups[i] = group;
		}
		return allGroups;
	}

}
