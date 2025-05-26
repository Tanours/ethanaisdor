package splendor.model;

public enum Color {
	RESET("\u001B[0m"),
	ROSE("\u001B[38;5;200m"),
	SAPHIR("\u001B[38;2;15;82;186m"),
	EMERALD("\u001B[38;2;46;204;113m"),
	RUBY("\u001B[38;2;224;17;95m"),
	DIAMOND("\u001B[38;2;185;242;255m"),
	ONYX("\u001B[38;2;0;0;0m"),
	GOLDJOKER("\u001B[38;2;255;215;0m"),
	RED("\u001B[31m");
	
	private final String value;
	
	Color(String value){
		this.value = value;
	}
	public String getValue() {
		return value;
	}
}
