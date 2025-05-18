package splendor;

import java.util.HashMap;
import java.util.Objects;

public record Card(int id,Stones stone ,HashMap<Stones,Integer> needStones,int prestige) {
	public Card{
		if(id<0||prestige<0) {
			throw new IllegalArgumentException();
		}
		Objects.requireNonNull(stone);
		Objects.requireNonNull(needStones);
		
	}
	
	@Override
	public String toString() {
	    StringBuilder res = new StringBuilder();
	    res.append("------------------\n");
	    res.append(String.format("| %-15s|\n", stone));
	    res.append(String.format("| %-15s|\n", "Prestige: " + prestige));
	    res.append(String.format("| %-15s|\n", "Cost:"));
	    for (var entry : needStones.entrySet()) {
	        String line = entry.getKey() + " x" + entry.getValue();
	        res.append(String.format("|   %-13s|\n", line));
	    }
	    res.append("------------------\n");
	    return res.toString();
	}
}
