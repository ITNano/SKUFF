package se.matzlarsson.skuff.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class SKUFFUtil {

	public static void showAlert(Context c, String title, String msg){
    	new AlertDialog.Builder(c)
        .setTitle(title)
        .setMessage(msg)
        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { 
                // continue with delete
            }
         })
        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { 
                // do nothing
            }
         })
        .setIcon(android.R.drawable.ic_dialog_alert)
         .show();
	}

}
