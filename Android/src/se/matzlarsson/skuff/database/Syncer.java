package se.matzlarsson.skuff.database;

import android.support.v7.app.ActionBarActivity;

public class Syncer {

	public static void startSync(ActionBarActivity activity){
		DataSyncer syncer = DataSyncer.getInstance(activity);
		syncer.execute();
		ResourceSyncer resSyncer = ResourceSyncer.getInstance(activity);
		resSyncer.execute();
		UploadSyncer uploader = UploadSyncer.getInstance();
		uploader.execute(activity.getApplicationContext().getFilesDir().getAbsolutePath());
	}
	
}
