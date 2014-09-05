package se.matzlarsson.skuff.database;

import se.matzlarsson.skuff.R;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.TaskStackBuilder;

public class Notification {
	
	private static Class<? extends Object> mainActivity;
	
	public static void setMainActivity(Class<? extends Object> mainActivity){
		Notification.mainActivity = mainActivity;
	}
	
	public static void addNotification(Context context, int id, String title, String content, String intentClass){
		if(mainActivity == null){
			throw new IllegalArgumentException("Cannot create notification without main class");
		}
		
		Builder builder = new Builder(context);
		builder.setSmallIcon(R.drawable.notification_icon);
		builder.setContentTitle(title);
		builder.setContentText(content);
		builder.setNumber(1);
		builder.setAutoCancel(true);

		// Creates an explicit intent for an Activity
		Intent resultIntent = new Intent(context, mainActivity);
		resultIntent.putExtra("fromNotification", true);
		resultIntent.putExtra("fragment", intentClass);

		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addParentStack(mainActivity);
		stackBuilder.addNextIntent(resultIntent);
		
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(resultPendingIntent);
		
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(id, builder.build());
	}

}
