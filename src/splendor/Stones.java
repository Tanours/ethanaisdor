package splendor;

public enum Stones {
	SAPHIR,
	EMERALD,
	RUBY,
	DIAMOND,
	ONYX,
	GOLDJOKER;
	
	public String getColor(Stones stone) {
		return Color.valueOf(stone.toString()).getValue();
	}
}
