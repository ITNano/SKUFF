package se.matzlarsson.skuff.database.data;

public class StringUtil {

	public static String swedify(String input){
		String[] signs = {"&aring;", "�", "&Aring;", "�", "&auml;", "�", "&Auml;", "�", "&ouml;", "�", "&Ouml;", "�"};
		for(int i = 0; i<signs.length; i += 2){
			input = input.replaceAll(signs[i], signs[i+1]);
		}
		
		return input;
	}

}
