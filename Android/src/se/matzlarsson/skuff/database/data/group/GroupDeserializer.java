package se.matzlarsson.skuff.database.data.group;

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

public class GroupDeserializer implements JsonDeserializer<Group[]> {

	private boolean noNotifications = false;
	
	public GroupDeserializer(boolean noNotifications){
		this.noNotifications = noNotifications;
	}
	
	@Override
	public Group[] deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		JsonArray arr = json.getAsJsonArray();
		Group[] allGroups = new Group[arr.size()];
		
		JsonObject obj = null;
		Group group = null;
		for(int i = 0; i<arr.size(); i++){
			obj = arr.get(i).getAsJsonObject();
			group = new Group();
			group.setId(obj.get("id").getAsInt());
			group.setName(StringUtil.swedify(obj.get("name").getAsString()));
			allGroups[i] = group;
			
			if(obj.get("notification") != null && !noNotifications){
				Context c = Syncer.getNofificationContext();
				String content = StringUtil.swedify(obj.get("notification").getAsString());
				String title = c.getResources().getString(R.string.notification_group_title_created);
				Notification.addNotification(c, NotificationConstants.NOTIFICATION_GROUP, title, content, StartScreen.FRAGMENT_GROUPS);
			}
		}
		return allGroups;
	}

}
