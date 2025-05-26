package splendor.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Player {
	
	public final String name;
	public final int age;
	public Map<Stones, Integer> tokens;
	public List<Card> cards;
	private int points;
	private final Price bourse;
	
	public Player(String name, int age) {
		Objects.requireNonNull(name, "name of player can't be null");
		
		if(age < 0) {
			throw new IllegalArgumentException("age of player can't be negative");
		}
		
		this.name = name;
		this.age = age;
		this.points = 0;
		this.cards = new ArrayList<Card>();
		this.tokens = new HashMap<Stones, Integer>();
		this.bourse = null; //TODO
	}
	
	
	public Player(String name, int age, int points, Map<Stones, Integer> tokens, List<Card> cards) {
		Objects.requireNonNull(name, "name of player can't be null");
		
		if(points < 0) {
			throw new IllegalArgumentException("point can't be negative");
		}
		
		this.name = name;
		this.age = age;
		this.points = points;
		this.tokens = tokens;
		this.cards = cards;
		this.bourse = null;
	}

	
	public void addToken(Stones stone, int quantity) {
		Objects.requireNonNull(stone);
		
		tokens.put(stone, tokens.getOrDefault(stone, 0) + quantity);
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
	
	
	public void buyCard(Card card) {
		Objects.requireNonNull(card);

	    for (var cost : card.needStones().entrySet()) {
	        var stone = cost.getKey();
	        if(tokens.get(stone) - cost.getValue() > 0) {
	        	tokens.replace(stone,tokens.get(stone) - cost.getValue());
	        }
	        else {
	        	tokens.remove(stone);
	        }
	    }
	    cards.add(card);
	    points+=card.prestige();
	    
	}
	
	@Override
	public String toString() {
	    var res = new StringBuilder();
	    res.append(name).append("\n");
	    
	    res.append("\t Points : ").append(points).append("\n");
	    
	    res.append("\t Tokens : ");
	    for (var stone : Stones.values()) {
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
	
	public String getName() {
		return name;
	}
	
	public int getPoints() {
		return points;
	}
	
	public int getAge() {
        return age;
    }


	
}
