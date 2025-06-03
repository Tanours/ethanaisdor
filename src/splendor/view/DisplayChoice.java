package splendor.view;

import java.util.Objects;
import java.util.StringJoiner;

import splendor.model.Stones;

public record DisplayChoice(String... args) {
	public DisplayChoice{
		Objects.requireNonNull(args);
	}
	@Override
	public String toString() {
		var joiner = new StringJoiner("\n");
		for(var i = 0;i<args.length;i++) {
			var number = "%s%1s%s".formatted(Stones.EMERALD.getColor(),i+1,Stones.resetColor());
			var text = number+" - "+args[i];
			joiner.add(text);
		}
		return joiner.toString();
	}
}
