package se.matzlarsson.skuff.database.data.news;

import java.lang.reflect.Type;

import se.matzlarsson.skuff.R;
import se.matzlarsson.skuff.database.Notification;
import se.matzlarsson.skuff.database.Syncer;
import se.matzlarsson.skuff.database.data.NotificationConstants;
import se.matzlarsson.skuff.ui.StartScreen;
import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class NewsDeserializer implements JsonDeserializer<News[]>{

	@Override
	public News[] deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		JsonArray arr = json.getAsJsonArray();
		News[] allNews = new News[arr.size()];
		
		JsonObject obj = null;
		News news = null;
		for(int i = 0; i<arr.size(); i++){
			obj = arr.get(i).getAsJsonObject();
			news = new News();
			news.setId(obj.get("id").getAsInt());
			news.setUserID(obj.get("userID").getAsInt());
			news.setHeader(obj.get("header").getAsString());
			news.setContent(obj.get("content").getAsString());
			news.setTime(obj.get("time").getAsString());
			allNews[i] = news;
			
			if(obj.get("notification") != null){
				Context c = Syncer.getNofificationContext();
				String content = obj.get("notification").getAsString();
				String title = c.getResources().getString(R.string.notification_news_title);
				Notification.addNotification(c, NotificationConstants.NOTIFICATION_NEWS, title, content, StartScreen.FRAGMENT_NEWS);
			}
		}
		return allNews;
	}

}
