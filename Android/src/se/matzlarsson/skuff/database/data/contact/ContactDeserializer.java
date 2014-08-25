package se.matzlarsson.skuff.database.data.contact;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ContactDeserializer implements JsonDeserializer<Contact[]>{

	@Override
	public Contact[] deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		JsonArray arr = json.getAsJsonArray();
		Contact[] contacts = new Contact[arr.size()];
		
		JsonObject obj = null;
		Contact contact = null;
		for(int i = 0; i<arr.size(); i++){
			obj = arr.get(i).getAsJsonObject();
			contact = new Contact();
			contact.setId(obj.get("id").getAsInt());
			contact.setName(obj.get("name").getAsString());
			contact.setPhone(obj.get("phone").getAsString());
			contact.setMail(obj.get("mail").getAsString());
			contact.setImageName(obj.get("image").getAsString());
			contacts[i] = contact;
		}
		return contacts;
	}

}
