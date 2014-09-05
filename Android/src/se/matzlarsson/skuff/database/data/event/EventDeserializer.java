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

public class EventDeserializer implements JsonDeserializer<Event[]> {

	private boolean noNotifications = false;
	
	public EventDeserializer(boolean noNotifications){
		this.noNotifications = noNotifications;
	}
	
	@Override
	public Event[] deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		JsonArray arr = json.getAsJsonArray();
		Event[] allEvents = new Event[arr.size()];
		
		JsonObject obj = null;
		Event event = null;
		for(int i = 0; i<arr.size(); i++){
			obj = arr.get(i).getAsJsonObject();
			event = new Event();
			event.setId(obj.get("id").getAsInt());
			event.setDate(obj.get("date").getAsString());
			event.setName(StringUtil.swedify(obj.get("name").getAsString()));
			event.setCompulsory(obj.get("compulsory").getAsBoolean());
			event.setTime(obj.get("time").getAsString());
			allEvents[i] = event;
			
			if(obj.get("notification") != null && !noNotifications){
				Context c = Syncer.getNofificationContext();
				String content = StringUtil.swedify(obj.get("notification").getAsString());
				String title = c.getResources().getString(R.string.notification_event_title_created);
				Notification.addNotification(c, NotificationConstants.NOTIFICATION_EVENT, title, content, StartScreen.FRAGMENT_CALENDER);
			}
		}
		return allEvents;
	}

}
