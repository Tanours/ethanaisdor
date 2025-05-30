package splendor.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record Noble(int id, String name, Price cost, int prestige) {

	
	public Noble {
		Objects.requireNonNull(name);
		Objects.requireNonNull(cost);
		
		if(prestige < 0) {
			throw new IllegalArgumentException("prestige can't be negative");
		}
	}
	
	
	public boolean canVisit(Player player) {
		Objects.requireNonNull(player);
		
		return cost.isBelow(player.getWallet());
	}
	
	public void displayNoble() {
		System.out.println(name);
	}
	
	
}
