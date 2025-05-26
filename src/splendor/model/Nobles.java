package splendor.model;

import java.util.Objects;

public class Nobles {
	private final Price cost;
	private final int prestige = 3;
	
	public Nobles(Price cost) {
		Objects.requireNonNull(cost);
		
		this.cost = cost;
	}
}
