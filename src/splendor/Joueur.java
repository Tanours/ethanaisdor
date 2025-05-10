package splendor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Joueur {
	
	public final String name;
	public final HashMap<Stones, Integer> tokens = new HashMap<Stones, Integer>();
	public final List<String> cards = new ArrayList<String>();
	public final int points;
	
	public Joueur(String name) {
		Objects.requireNonNull(name, "name of player can't be null");
		
		this.name = name;
		this.points = 0;
	}
	
	public void addToken(Stones stone, int quantity) {
		Objects.requireNonNull(stone);
		
		this.tokens.put(stone, tokens.getOrDefault(stone, 0) + quantity);
	}
	
	
	
}
