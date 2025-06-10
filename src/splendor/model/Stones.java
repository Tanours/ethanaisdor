package splendor.model;

import java.util.Objects;

public enum Stones {
	SAPHIR("SAPHIR"),
	EMERALD("EMERALD"),
	RUBY("RUBY"),
	DIAMOND("DIAMOND"),
	ONYX("ONYX"),
	GOLDJOKER("JOKER");
	
	private Stones(String stone) {
		Objects.requireNonNull(stone);
	}
	public static String resetColor() {
		return "\u001B[0m";
	}
	public String getColor() {
		return switch (this) {
			case SAPHIR -> "%s".formatted("\u001B[94m");
			case EMERALD -> "\u001B[92m";
			case RUBY -> "%s".formatted("\u001B[91m");
			case DIAMOND -> "\u001B[36m";
			case GOLDJOKER -> "\u001B[33m";
			default -> "\u001B[90m";
		};
	}
}
