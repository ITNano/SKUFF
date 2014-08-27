package se.matzlarsson.skuff.database.data;

import java.util.ArrayList;
import java.util.List;

public class ResourceResult {

	List<String> paths;
	
	public ResourceResult(){
		paths = new ArrayList<String>();
	}
	
	public void addPath(String path){
		this.paths.add(path);
	}
	
	public String getPath(int index){
		return this.paths.get(index);
	}
	
	public List<String> getPaths(){
		return paths;
	}
	
	public int getCount(){
		return this.paths.size();
	}
	
}
