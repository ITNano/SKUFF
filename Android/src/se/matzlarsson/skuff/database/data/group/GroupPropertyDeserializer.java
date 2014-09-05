package se.matzlarsson.skuff.database.data.group;

import java.lang.reflect.Type;

import se.matzlarsson.skuff.database.data.StringUtil;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class GroupPropertyDeserializer implements JsonDeserializer<GroupProperty[] >{

	@Override
	public GroupProperty[] deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		JsonArray arr = json.getAsJsonArray();
		GroupProperty[] allProps = new GroupProperty[arr.size()];
		
		JsonObject obj = null;
		GroupProperty prop = null;
		for(int i = 0; i<arr.size(); i++){
			obj = arr.get(i).getAsJsonObject();
			prop = new GroupProperty();
			prop.setId(obj.get("id").getAsInt());
			prop.setName(StringUtil.swedify(obj.get("name").getAsString()));
			allProps[i] = prop;
		}
		return allProps;
	}

}
