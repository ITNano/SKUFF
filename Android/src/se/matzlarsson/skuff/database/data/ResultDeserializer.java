package se.matzlarsson.skuff.database.data;

import java.lang.reflect.Type;

import se.matzlarsson.skuff.database.data.contact.ContactDeserializer;
import se.matzlarsson.skuff.database.data.event.EventDeserializer;
import se.matzlarsson.skuff.database.data.event.EventPropertyDeserializer;
import se.matzlarsson.skuff.database.data.event.EventValueDeserializer;
import se.matzlarsson.skuff.database.data.group.GroupDeserializer;
import se.matzlarsson.skuff.database.data.group.GroupPropertyDeserializer;
import se.matzlarsson.skuff.database.data.group.GroupValueDeserializer;
import se.matzlarsson.skuff.database.data.news.NewsDeserializer;
import se.matzlarsson.skuff.database.data.sag.SAGDeserializer;
import se.matzlarsson.skuff.database.data.user.UserDeserializer;

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
		result.setGroups(new GroupDeserializer().deserialize(obj.get("groups").getAsJsonArray(), type, context));
		result.setGroupProperties(new GroupPropertyDeserializer().deserialize(obj.get("groupproperties").getAsJsonArray(), type, context));
		result.setGroupValues(new GroupValueDeserializer().deserialize(obj.get("groupvalues").getAsJsonArray(), type, context));
		result.setSAG(new SAGDeserializer().deserialize(obj.get("sag").getAsJsonArray(), type, context));
		result.setContacts(new ContactDeserializer().deserialize(obj.get("contacts").getAsJsonArray(), type, context));
		
		return result;
	}
}
