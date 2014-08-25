package se.matzlarsson.skuff.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class TextFormatter {
	
	private static Typeface customFont = null;
	
	private static void initFont(Context context){
		customFont = Typeface.createFromAsset(context.getAssets(), "fonts/lato.ttf");
	}

	public static void useCustomFont(Context context, TextView view){
		if(customFont == null){
			initFont(context);
		}
		
		view.setTypeface(customFont);
	}
}
