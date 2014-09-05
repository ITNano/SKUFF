package se.matzlarsson.skuff.database.data.event;

import java.lang.reflect.Type;

import se.matzlarsson.skuff.R;
import se.matzlarsson.skuff.database.Notification;
import se.matzlarsson.skuff.database.Syncer;
import se.matzlarsson.skuff.database.data.NotificationConstants;
import se.matzlarsson.skuff.database.data.StringUtil;
import se.matzlarsson.skuff.ui.StartScreen;
import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class EventValueDeserializer implements JsonDeserializer<EventValue[] >{

	private boolean noNotifications = false;
	
	public EventValueDeserializer(boolean noNotifications){
		this.noNotifications = noNotifications;
	}
	
	@Override
	public EventValue[] deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		JsonArray arr = json.getAsJsonArray();
		EventValue[] allValues = new EventValue[arr.size()];
		
		JsonObject obj = null;
		EventValue value = null;
		for(int i = 0; i<arr.size(); i++){
			obj = arr.get(i).getAsJsonObject();
			value = new EventValue();
			value.setId(obj.get("id").getAsInt());
			value.setEventID(obj.get("eventID").getAsInt());
			value.setPropertyID(obj.get("propertyID").getAsInt());
			value.setValue(StringUtil.swedify(obj.get("value").getAsString()));
			allValues[i] = value;
			
			if(obj.get("notification") != null && !noNotifications){
				Context c = Syncer.getNofificationContext();
				String content = StringUtil.swedify(obj.get("notification").getAsString());
				String title = c.getResources().getString(R.string.notification_event_title_updated);
				Notification.addNotification(c, NotificationConstants.NOTIFICATION_EVENTVALUE, title, content, StartScreen.FRAGMENT_CALENDER);
			}
		}
		return allValues;
	}

}
