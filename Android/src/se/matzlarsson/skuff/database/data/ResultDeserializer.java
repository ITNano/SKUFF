package se.matzlarsson.skuff.database.data;

import java.lang.reflect.Type;

import se.matzlarsson.skuff.database.data.contact.ContactDeserializer;
import se.matzlarsson.skuff.database.data.contest.ContestDeserializer;
import se.matzlarsson.skuff.database.data.contest.ContestQuestionDeserializer;
import se.matzlarsson.skuff.database.data.contest.QuestionOptionDeserializer;
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

	private boolean noNotifications = false;
	
	public ResultDeserializer(boolean noNotifications){
		this.noNotifications = noNotifications;
	}
	
	@Override
	public Result deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		JsonObject obj = json.getAsJsonObject();
		Result result = new Result();
		result.addData(new NewsDeserializer(noNotifications).deserialize(obj.get("news").getAsJsonArray(), type, context));
		result.addData(new UserDeserializer().deserialize(obj.get("users").getAsJsonArray(), type, context));
		result.addData(new EventDeserializer(noNotifications).deserialize(obj.get("events").getAsJsonArray(), type, context));
		result.addData(new EventPropertyDeserializer().deserialize(obj.get("eventproperties").getAsJsonArray(), type, context));
		result.addData(new EventValueDeserializer(noNotifications).deserialize(obj.get("eventvalues").getAsJsonArray(), type, context));
		result.addData(new GroupDeserializer(noNotifications).deserialize(obj.get("groups").getAsJsonArray(), type, context));
		result.addData(new GroupPropertyDeserializer().deserialize(obj.get("groupproperties").getAsJsonArray(), type, context));
		result.addData(new GroupValueDeserializer(noNotifications).deserialize(obj.get("groupvalues").getAsJsonArray(), type, context));
		result.addData(new SAGDeserializer().deserialize(obj.get("sag").getAsJsonArray(), type, context));
		result.addData(new ContestDeserializer(noNotifications).deserialize(obj.get("contests").getAsJsonArray(), type, context));
		result.addData(new ContestQuestionDeserializer().deserialize(obj.get("contestquestions").getAsJsonArray(), type, context));
		result.addData(new QuestionOptionDeserializer().deserialize(obj.get("contestoptions").getAsJsonArray(), type, context));
		result.addData(new ContactDeserializer().deserialize(obj.get("contacts").getAsJsonArray(), type, context));
		
		return result;
	}
}
