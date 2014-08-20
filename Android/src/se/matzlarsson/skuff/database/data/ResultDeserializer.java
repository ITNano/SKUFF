package se.matzlarsson.skuff.database.data;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ResultDeserializer implements JsonDeserializer<Result>{

	@Override
	public Result deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		JsonObject obj = json.getAsJsonObject();
		Result result = new Result();
		result.setNews(new NewsDeserializer().deserialize(obj.get("news").getAsJsonArray(), type, context));
		result.setUsers(new UserDeserializer().deserialize(obj.get("users").getAsJsonArray(), type, context));
		result.setEvents(new EventDeserializer().deserialize(obj.get("events").getAsJsonArray(), type, context));
		result.setEventProperties(new EventPropertyDeserializer().deserialize(obj.get("eventproperties").getAsJsonArray(), type, context));
		result.setEventValues(new EventValueDeserializer().deserialize(obj.get("eventvalues").getAsJsonArray(), type, context));
		
		return result;
	}
}
