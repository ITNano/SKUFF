package se.matzlarsson.skuff.database.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import se.matzlarsson.skuff.database.DatabaseHelper;

public class Result {
	
	private List<Saveable> data;
	
	public Result(){
		data = new ArrayList<Saveable>();
	}
	
	public void addData(Saveable[] list){
		addData(Arrays.asList(list));
	}
	
	public void addData(List<? extends Saveable> list){
		data.addAll(list);
	}
	
	public String getUpdatesInfo(){
		return "Found "+data.size()+" updates";
	}
	
	public void saveToDb(DatabaseHelper db){
		for(int i = 0; i<data.size(); i++){
			data.get(i).saveToDb(db);
		}
	}
	
	@Override
	public String toString(){
		String s = "RESULT:[\n";
		for(int i = 0; i<data.size(); i++){
			s += "\t"+data.get(i).toString().replaceAll("\n", "\n\t")+"\n";
		}
		s += "]";
		return s;
	}
}
