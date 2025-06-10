package splendor.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import splendor.model.Stones;

public record DisplayChoice(boolean squared,boolean indexes,String... args) {
	public DisplayChoice{
		Objects.requireNonNull(args);
	}
	public DisplayChoice(String... args) {
		this(true,true,args);
	}
	public DisplayChoice(boolean squared,String... args) {
		this(squared,true,args);
	}
	public DisplayChoice(List<String> args) {
		this(true,true,args.toArray(new String[0]));
	}
	public DisplayChoice(boolean squared,List<String> args) {
		this(squared,true,args.toArray(new String[0]));
	}
	public DisplayChoice(boolean squared,boolean indexes,List<String> args) {
		this(squared,indexes,args.toArray(new String[0]));
	}
	@Override
	public String toString() {
		
		var list = new ArrayList<String>();
		for(var i = 0;i<args.length;i++) {
			String text;
			if(indexes) {
				var number = " %s%1s%s".formatted("\u001B[33m",i+1,Stones.resetColor());
				text = number+" - "+args[i];
			}
			else {
				text =" - "+args[i];
			}
			list.add(text);
			
			
		}
		String res;
		if(squared) {
			 res = new DisplayInSquare(1,list).toString();
		}
		else {
			 res = list.stream().collect(Collectors.joining("\n"));
		}
		
		return res;
	}
}
