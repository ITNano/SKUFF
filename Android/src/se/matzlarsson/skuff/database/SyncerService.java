package se.matzlarsson.skuff.database;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SyncerService extends Service{
	
	private static Syncer syncAdapter = null;
	private static final Object syncAdapterLock = new Object();

	@Override
	public void onCreate(){
		synchronized(syncAdapterLock){
			if(syncAdapter==null){
				syncAdapter = new Syncer(this.getApplicationContext(), true);
			}
		}
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return syncAdapter.getSyncAdapterBinder();
	}

}
