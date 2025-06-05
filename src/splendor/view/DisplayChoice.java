package splendor.view;

import java.util.ArrayList;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import splendor.model.Stones;

public record DisplayChoice(boolean squared,String... args) {
	public DisplayChoice{
		Objects.requireNonNull(args);
	}
	public DisplayChoice(String... args) {
		this(true,args);
	}
	@Override
	public String toString() {
		
		var list = new ArrayList<String>();
		for(var i = 0;i<args.length;i++) {
			
			var number = " %s%1s%s".formatted("\u001B[33m",i+1,Stones.resetColor());
			var text = number+" - "+args[i];
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
