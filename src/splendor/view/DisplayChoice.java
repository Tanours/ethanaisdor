package splendor.view;

import java.util.ArrayList;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.regex.Pattern;

import splendor.model.Stones;

public record DisplayChoice(String... args) {
	public DisplayChoice{
		Objects.requireNonNull(args);
	}
	@Override
	public String toString() {
		
		var list = new ArrayList<String>();
		for(var i = 0;i<args.length;i++) {
			
			var number = "%s%1s%s".formatted(Stones.EMERALD.getColor(),i+1,Stones.resetColor());
			var text = number+" - "+args[i];
			list.add(text);
			
			
		}
		var res = new DisplayInSquare(1, list.toArray(new String[0]));
		return res.toString();
	}
}
