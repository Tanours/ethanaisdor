package splendor;

import java.util.HashMap;

public record Card(Stones bonus,HashMap<Stones,Integer> needStones,int prestige) {
	public Card{
		
	}
}
