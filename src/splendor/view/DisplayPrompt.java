package splendor.view;

import java.util.Objects;

import splendor.model.Stones;

public record DisplayPrompt(String text) {
	public DisplayPrompt{
		Objects.requireNonNull(text);
	}
	
	@Override
	public String toString(){
		var arrow = "%s%1s%s ".formatted(Stones.DIAMOND.getColor(),"▶",Stones.resetColor());
		return arrow+text;
	}
}
