package splendor.view;

import java.util.Objects;

import splendor.model.Stones;

public record DisplayInfo(String info) {
	public DisplayInfo {
		Objects.requireNonNull(info);
	}
	@Override
	public final String toString() {
		return "\u001B[32m"+"info : "+info+Stones.resetColor();
	}
}
