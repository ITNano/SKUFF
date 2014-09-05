package se.matzlarsson.skuff.database.data.contest;

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

public class ContestDeserializer implements JsonDeserializer<Contest[]>{

	private boolean noNotifications = false;
	
	public ContestDeserializer(boolean noNotifications){
		this.noNotifications = noNotifications;
	}
	
	@Override
	public Contest[] deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		JsonArray arr = json.getAsJsonArray();
		Contest[] contests = new Contest[arr.size()];
		
		JsonObject obj = null;
		Contest tmp = null;
		for(int i = 0; i<arr.size(); i++){
			obj = arr.get(i).getAsJsonObject();
			tmp = new Contest();
			tmp.setId(obj.get("id").getAsInt());
			tmp.setTheme(StringUtil.swedify(obj.get("theme").getAsString()));
			tmp.setPrice(StringUtil.swedify(obj.get("price").getAsString()));
			tmp.setEndDate(obj.get("ends").getAsString());
			tmp.setTime(obj.get("time").getAsString());
			contests[i] = tmp;
			
			if(obj.get("notification") != null && !noNotifications){
				Context c = Syncer.getNofificationContext();
				String content = StringUtil.swedify(obj.get("notification").getAsString());
				String title = c.getResources().getString(R.string.notification_contest_title);
				Notification.addNotification(c, NotificationConstants.NOTIFICATION_CONTEST, title, content, StartScreen.FRAGMENT_CONTEST);
			}
		}
		
		return contests;
	}

}
