package splendor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Player {
	
	public final String name;
	public final Map<Stones, Integer> tokens;
	public final List<Card> cards;
	public final int points;
	
	public Player(String name) {
		Objects.requireNonNull(name, "name of player can't be null");
		
		this.name = name;
		this.points = 0;
		this.cards = new ArrayList<Card>();
		this.tokens = new HashMap<Stones, Integer>();
	}
	
	
	public Player(String name, int points, Map<Stones, Integer> tokens, List<Card> cards) {
		Objects.requireNonNull(name, "name of player can't be null");
		
		if(points < 0) {
			throw new IllegalArgumentException("point can't be negative");
		}
		
		this.name = name;
		this.points = points;
		this.tokens = tokens;
		this.cards = cards;
	}

	
	public void addToken(Stones stone, int quantity) {
		Objects.requireNonNull(stone);
		
		this.tokens.put(stone, tokens.getOrDefault(stone, 0) + quantity);
	}
	
	
	public boolean canBuy(Card card) {
		Objects.requireNonNull(card);
		
		for(Map.Entry<Stones, Integer> cost :  card.needStones().entrySet()) {
			Stones stone = cost.getKey();
			int required = cost.getValue();
			
			if(tokens.getOrDefault(stone, 0) < required) {
				return false;
			}
		}
		return true;
	}
	
	
	public Player buyCard(Card card) {
		Objects.requireNonNull(card);
		
	    if (!canBuy(card)) {
	        return this;
	    }

	    Map<Stones, Integer> newTokens = new HashMap<>(this.tokens);
	    for (Map.Entry<Stones, Integer> cost : card.needStones().entrySet()) {
	        Stones stone = cost.getKey();
	        newTokens.put(stone, newTokens.get(stone) - cost.getValue());
	    }

	    List<Card> newCards = new ArrayList<>(this.cards);
	    newCards.add(card);

	    return new Player(this.name, this.points + card.prestige(), newTokens, newCards);
	}
	
	@Override
	public String toString() {
	    StringBuilder res = new StringBuilder();
	    res.append(name).append("\n");
	    
	    res.append("\t Points : ").append(points).append("\n");
	    
	    res.append("\t Tokens : ");
	    for (Stones stone : Stones.values()) {
	        res.append(stone).append("=").append(tokens.getOrDefault(stone, 0)).append(" ");
	    }
	    res.append("\n");
	    
	    res.append("\t Cards : \n");
	    if (cards.isEmpty()) {
	        res.append("\t\t none");
	    } else {
	        for (Card card : cards) {
	            res.append("\t\t" + card.toString());
	        }
	    }
	    res.append("\n");
	    return res.toString();
	}


	
}
