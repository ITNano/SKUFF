package se.matzlarsson.skuff.database.data.event;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class EventDeserializer implements JsonDeserializer<Event[]> {

	@Override
	public Event[] deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		JsonArray arr = json.getAsJsonArray();
		Event[] allEvents = new Event[arr.size()];
		
		JsonObject obj = null;
		Event event = null;
		for(int i = 0; i<arr.size(); i++){
			obj = arr.get(i).getAsJsonObject();
			event = new Event();
			event.setId(obj.get("id").getAsInt());
			event.setDate(obj.get("date").getAsString());
			event.setName(obj.get("name").getAsString());
			event.setCompulsory(obj.get("compulsory").getAsBoolean());
			event.setTime(obj.get("time").getAsString());
			allEvents[i] = event;
		}
		return allEvents;
	}

}
