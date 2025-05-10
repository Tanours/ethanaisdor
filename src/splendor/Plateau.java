package splendor;

import java.util.HashMap;
import java.util.List;

public class Plateau {
	private final HashMap<Integer, List<Card>> cards;
	private final HashMap<Stones, Integer> tokens;
	
	public Plateau() {
		this.cards = new HashMap<Integer, List<Card>>();
		this.tokens = new HashMap<Stones, Integer>();
	}
}
