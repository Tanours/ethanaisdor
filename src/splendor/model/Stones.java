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
	public String getColor(Stones stone) {
		return Color.valueOf(stone.toString()).getValue();
	}
}
