package splendor;

import java.util.HashMap;
import java.util.Map;

public record Card(Stones stone ,HashMap<Stones,Integer> needStones,int prestige) {
	public Card{
		
	}
	
	@Override
	public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(stone).append(" | ").append(prestige).append(" point | cost: ");
        
        for (Map.Entry<Stones, Integer> entry : needStones.entrySet()) {
        	res.append(entry.getKey()).append("=").append(entry.getValue()).append(" ");
            
        }
        res.append("\n");
        return res.toString();
    }
}
