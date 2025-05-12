package splendor;

import java.util.HashMap;
import java.util.Map;
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
        res.append(stone).append(" | ").append(prestige).append(" point | cost: ");
        
        for (Map.Entry<Stones, Integer> entry : needStones.entrySet()) {
        	res.append(entry.getKey()).append("=").append(entry.getValue()).append(" ");
            
        }
        res.append("\n");
        return res.toString();
    }
}
