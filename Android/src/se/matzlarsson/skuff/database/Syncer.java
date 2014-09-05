package se.matzlarsson.skuff.database;

import se.matzlarsson.skuff.ui.StartScreen;
import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

public class Syncer extends AbstractThreadedSyncAdapter{
	
	private static Context context;
	
	public Syncer(Context context, boolean autoInitialize){
		super(context, autoInitialize);
		
		Syncer.context = context;
		Notification.setMainActivity(StartScreen.class);
	}
	
	public static Context getNofificationContext(){
		return context;
	}

	public void startSync(){
		Log.d("SKUFF", "Starting sync...");
		DatabaseHelper.start(context);
		DataSyncer syncer = DataSyncer.getInstance();
		syncer.perform();
		ResourceSyncer resSyncer = ResourceSyncer.getInstance(context.getFilesDir().getAbsolutePath());
		resSyncer.perform();
		UploadSyncer uploader = UploadSyncer.getInstance();
		uploader.perform(context.getFilesDir().getAbsolutePath());
	}

	@Override
	public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult){
		//Yes i know, kind of brutal. But I do not care about the parameters...
		startSync();
	}
	
}
