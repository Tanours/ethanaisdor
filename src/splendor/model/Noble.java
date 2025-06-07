package splendor.model;

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
		
		return cost.isBelow(player.getBonusesCards());
	}
	
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
	    res.append("[");
	    res.append("R:%-2s O:%-2s S:%-2s E:%-2s D:%-2s".formatted(
	    		this.cost.getValue(Stones.RUBY),
	    		this.cost.getValue(Stones.ONYX),
	    		this.cost.getValue(Stones.SAPHIR),
	    		this.cost.getValue(Stones.EMERALD),
	    		this.cost.getValue(Stones.DIAMOND)
	    		));
	    res.append("]");
	    return res.toString();
	}
	
	
	
}
