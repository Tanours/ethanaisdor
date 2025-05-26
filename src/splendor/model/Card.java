package splendor.model;

import java.util.HashMap;
import java.util.Objects;

public record Card(int id,Stones stone ,Price needStones,int prestige) {
	public Card{
		if(id<0||prestige<0) {
			throw new IllegalArgumentException();
		}
		Objects.requireNonNull(stone);
		Objects.requireNonNull(needStones);
		
	}
	
	@Override
	public String toString() {
		return "[" + stone + ", " + prestige + ", " + needStones + "]\n";
	}
	
//	@Override
//	public String toString() {
//	    StringBuilder res = new StringBuilder();
//	    res.append("-".repeat(18)).append("\n");
//	    res.append(String.format("| %-15s|\n", stone));
//	    res.append(String.format("| %-15s|\n", "Prestige: " + prestige));
//	    res.append(String.format("| %-15s|\n", "Cost:"));
//	    for (var entry : needStones.entrySet()) {
//	        String line = entry.getKey() + " x" + entry.getValue();
//	        res.append(String.format("|   %-13s|\n", line));
//	    }
//	    res.append("-".repeat(18)).append("\n");
//	    return res.toString();
//	}
	
}
