package se.matzlarsson.skuff.database.data.sag;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class SAGDeserializer implements JsonDeserializer<SAG[]>{

	@Override
	public SAG[] deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		JsonArray arr = json.getAsJsonArray();
		SAG[] allInfo = new SAG[arr.size()];
		
		JsonObject obj = null;
		SAG member = null;
		for(int i = 0; i<arr.size(); i++){
			obj = arr.get(i).getAsJsonObject();
			member = new SAG();
			member.setId(obj.get("id").getAsInt());
			member.setName(obj.get("name").getAsString());
			member.setPost(obj.get("post").getAsString());
			member.setBirth(obj.get("birth").getAsString());
			member.setImageName(obj.get("image").getAsString());
			member.setDisplayOrder(obj.get("displayOrder").getAsInt());
			allInfo[i] = member;
		}
		return allInfo;
	}

}
