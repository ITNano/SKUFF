package se.matzlarsson.skuff.database.data.contest;

import java.lang.reflect.Type;

import se.matzlarsson.skuff.database.data.StringUtil;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class QuestionOptionDeserializer implements
		JsonDeserializer<QuestionOption[]> {

	@Override
	public QuestionOption[] deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		JsonArray arr = json.getAsJsonArray();
		QuestionOption[] options = new QuestionOption[arr.size()];
		
		JsonObject obj = null;
		QuestionOption tmp = null;
		for(int i = 0; i<arr.size(); i++){
			obj = arr.get(i).getAsJsonObject();
			tmp = new QuestionOption();
			tmp.setId(obj.get("id").getAsInt());
			tmp.setQuestionID(obj.get("questionID").getAsInt());
			tmp.setOption(StringUtil.swedify(obj.get("option").getAsString()));
			options[i] = tmp;
		}
		
		return options;
	}

}
