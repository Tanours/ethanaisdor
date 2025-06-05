package splendor.view;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public record DisplayInSquare(int gap,String... args) {
	public DisplayInSquare{
		Objects.requireNonNull(args);
		if(gap < 0) throw new IllegalArgumentException();
	}
	
	public DisplayInSquare(String... args) {
		this(10,args);
	}
	
	public DisplayInSquare(List<String> args) {
		this(args.toArray(new String[0]));
	}
	public DisplayInSquare(int gap,List<String> args) {
		this(gap,args.toArray(new String[0]));
	}
	
	private int getMaxSize() {
		var max = 0;
		for(var arg : args) {
			var length = getRealSize(arg);
			if(length > max) max = length;
		}
		return max;
	}
	
	private int getRealSize(String arg) {
		var pattern = Pattern.compile("\u001B\\[[0-9;]*m");
		var res = arg.length();
		if(pattern.matcher(arg).replaceAll("").length() != arg.length()){
			res = pattern.matcher(arg).replaceAll("").length() ;
		}
		return res;
		 
	}
	
	@Override
	public String toString() {
		
		
		var build = new StringBuilder();
		var maxSize = getMaxSize();
		
		
		build.append("╔").append("═".repeat(2*gap+maxSize)).append("╗").append("\n");
		for(var arg : args) {
			
			build.append("║").append(" ".repeat(gap)).append(arg).append(" ".repeat(maxSize-getRealSize(arg)+gap)).append("║").append("\n");
		}
		build.append("╚").append("═".repeat(2*gap+maxSize)).append("╝");
		return build.toString();
	}
}
