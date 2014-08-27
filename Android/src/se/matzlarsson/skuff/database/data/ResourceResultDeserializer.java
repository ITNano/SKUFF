package se.matzlarsson.skuff.database.data;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class ResourceResultDeserializer implements JsonDeserializer<ResourceResult>{

	@Override
	public ResourceResult deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		JsonArray arr = json.getAsJsonArray();
		ResourceResult r = new ResourceResult();
		for(int i = 0; i<arr.size(); i++){
			r.addPath(arr.get(i).getAsString());
		}
		
		return r;
	}

}
