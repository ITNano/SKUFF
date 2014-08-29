package se.matzlarsson.skuff.database.data.contest;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ContestQuestionDeserializer implements JsonDeserializer<ContestQuestion[]> {

	@Override
	public ContestQuestion[] deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		JsonArray arr = json.getAsJsonArray();
		ContestQuestion[] questions = new ContestQuestion[arr.size()];
		
		JsonObject obj = null;
		ContestQuestion tmp = null;
		for(int i = 0; i<arr.size(); i++){
			obj = arr.get(i).getAsJsonObject();
			tmp = ContestFactory.getContestQuestion(obj.get("type").getAsInt());
			tmp.setId(obj.get("id").getAsInt());
			tmp.setContestID(obj.get("contestID").getAsInt());
			tmp.setQuestion(obj.get("question").getAsString());
			questions[i] = tmp;
		}
		
		return questions;
	}

}
