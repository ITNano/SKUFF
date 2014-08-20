package se.matzlarsson.skuff.database.data;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class EventValueDeserializer implements JsonDeserializer<EventValue[] >{

	@Override
	public EventValue[] deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		JsonArray arr = json.getAsJsonArray();
		EventValue[] allValues = new EventValue[arr.size()];
		
		JsonObject obj = null;
		EventValue value = null;
		for(int i = 0; i<arr.size(); i++){
			obj = arr.get(i).getAsJsonObject();
			value = new EventValue();
			value.setId(obj.get("id").getAsInt());
			value.setEventID(obj.get("eventID").getAsInt());
			value.setPropertyID(obj.get("propertyID").getAsInt());
			value.setValue(obj.get("value").getAsString());
			allValues[i] = value;
		}
		return allValues;
	}

}
