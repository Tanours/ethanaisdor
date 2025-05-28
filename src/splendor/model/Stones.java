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
			case SAPHIR -> "\u001B[94m";
			case EMERALD -> "\u001B[92m";
			case RUBY -> "\u001B[91m";
			case DIAMOND -> "\u001B[36m";
			default -> "\u001B[90m";
		};
	}
}
