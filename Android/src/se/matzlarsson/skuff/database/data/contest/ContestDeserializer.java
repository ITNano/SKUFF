package se.matzlarsson.skuff.database.data.contest;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ContestDeserializer implements JsonDeserializer<Contest[]>{

	@Override
	public Contest[] deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		JsonArray arr = json.getAsJsonArray();
		Contest[] contests = new Contest[arr.size()];
		
		JsonObject obj = null;
		Contest tmp = null;
		for(int i = 0; i<arr.size(); i++){
			obj = arr.get(i).getAsJsonObject();
			tmp = new Contest();
			tmp.setId(obj.get("id").getAsInt());
			tmp.setTheme(obj.get("theme").getAsString());
			tmp.setPrice(obj.get("price").getAsString());
			tmp.setEndDate(obj.get("ends").getAsString());
			tmp.setTime(obj.get("time").getAsString());
			contests[i] = tmp;
		}
		
		return contests;
	}

}
