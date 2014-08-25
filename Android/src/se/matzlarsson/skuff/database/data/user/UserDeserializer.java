package se.matzlarsson.skuff.database.data.user;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class UserDeserializer implements JsonDeserializer<User[]> {

	@Override
	public User[] deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		JsonArray arr = json.getAsJsonArray();
		User[] allUsers = new User[arr.size()];
		
		JsonObject obj = null;
		User user = null;
		for(int i = 0; i<arr.size(); i++){
			obj = arr.get(i).getAsJsonObject();
			user = new User();
			user.setId(obj.get("id").getAsInt());
			user.setName(obj.get("name").getAsString());
			allUsers[i] = user;
		}
		return allUsers;
	}

}
