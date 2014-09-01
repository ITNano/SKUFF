package se.matzlarsson.skuff.database;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Connectivity {

	public static boolean getNetworkStatus(Context context){
		ConnectivityManager conMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo i = conMgr.getActiveNetworkInfo();
		if (i == null){
			return false;
		}if (!i.isConnected()){
			return false;
		}if (!i.isAvailable()){
			return false;
		}
		return true;
	}
}
