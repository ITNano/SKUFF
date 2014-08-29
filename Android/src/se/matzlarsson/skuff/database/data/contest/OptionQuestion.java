package se.matzlarsson.skuff.database.data.contest;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

public class OptionQuestion extends ContestQuestion{
	
	public static final int TYPE_INDEX = 1;
	
	private List<QuestionOption> options;
	
	public OptionQuestion(){
		super();
		initOptions();
		setTypeIndex(TYPE_INDEX);
	}
	
	public OptionQuestion(Cursor c){
		super(c);
		initOptions();
		setTypeIndex(TYPE_INDEX);
	}
	
	public OptionQuestion(int id, int contestID, String question){
		super(id, contestID, question, TYPE_INDEX);
		initOptions();
		setTypeIndex(TYPE_INDEX);
	}
	
	public void initOptions(){
		options = new ArrayList<QuestionOption>();
	}
	
	public boolean addOption(QuestionOption option){
		if(option != null){
			return options.add(option);
		}
		return false;
	}
	
	public boolean removeOption(QuestionOption option){
		if(option != null){
			return options.remove(option);
		}
		return false;
	}
	
	public int getOptionCount(){
		return (options==null?0:options.size());
	}
	
	public QuestionOption getOption(int index){
		if(index>=0 && options!=null && index<options.size()){
			return options.get(index);
		}
		return null;
	}

	public String toString(){
		String s = "OPTIONQUESTION: [\n";
		s += "\t"+super.toString().replaceAll("\n", "\n\t")+"\n";
		s += "\tOptions:\n";
		for(int i = 0; i<getOptionCount(); i++){
			s += "\t\t"+getOption(i).toString().replaceAll("\n", "\n\t\t")+(i+1<getOptionCount()?"\n":" ]");
		}

		return s;
	}
}
