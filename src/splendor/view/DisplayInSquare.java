package splendor.view;

import java.util.Objects;

public record DisplayInSquare(String text,int gap) {
	public DisplayInSquare{
		Objects.requireNonNull(text);
	}
	@Override
	public String toString() {
		var build = new StringBuilder();
		build.append("╔").append("═".repeat(2*gap+text.length())).append("╗").append("\n");
		build.append("║").append(" ".repeat(gap)).append(text).append(" ".repeat(gap)).append("║").append("\n");
		build.append("╚").append("═".repeat(2*gap+text.length())).append("╝");
		return build.toString();
	}
}
