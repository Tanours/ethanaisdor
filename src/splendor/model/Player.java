package splendor.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Player {
	
	private final String name;
	private final int age;
	//public Map<Stones, Integer> tokens;
	private final List<Card> cards;
	private int points;
	private Price wallet;
	
	public Player(String name, int age) {
		Objects.requireNonNull(name, "name of player can't be null");
		
		if(age < 0) {
			throw new IllegalArgumentException("age of player can't be negative");
		}
		
		this.name = name;
		this.age = age;

		points = 0;
		cards = new ArrayList<Card>();
		wallet = new Price();
	}
	
	
	public Player(String name, int age, int points,Price wallet, List<Card> cards) {
		Objects.requireNonNull(name, "name of player can't be null");
		Objects.requireNonNull(wallet);
		if(points < 0) {
			throw new IllegalArgumentException("point can't be negative");
		}
		
		this.name = name;
		this.age = age;
		this.points = points;
		this.cards = cards;
		this.wallet = wallet;
	}

	
	public void addToken(Stones stone, int quantity) {
		Objects.requireNonNull(stone);
		
		var ruby = wallet.getValue(Stones.RUBY);
		var saphir = wallet.getValue(Stones.SAPHIR);
		var diamond = wallet.getValue(Stones.DIAMOND);
		var emerald = wallet.getValue(Stones.EMERALD);
		
		wallet = switch (stone) {
			case RUBY -> new Price(ruby+quantity,saphir,diamond,emerald);
			case SAPHIR -> new Price(ruby,saphir+quantity,diamond,emerald);
			case DIAMOND -> new Price(ruby,saphir,diamond+quantity,emerald);
			case EMERALD -> new Price(ruby,saphir,diamond,emerald+quantity);
			default -> null;
		};
	}
	
	
	public boolean canBuy(Card card) {
		Objects.requireNonNull(card);
		return wallet.isBelow(card.price());
	}
	
	
	public void buyCard(Card card) {
		Objects.requireNonNull(card);
		if(this.canBuy(card)) {
			wallet = wallet.substract(card.price());
		}
	    
	    cards.add(card);
	    points+=card.prestige();
	    
	}
	
	@Override
	public String toString() {
	    var res = new StringBuilder();
	    res.append(name).append("\n");
	    
	    res.append("\t Points : ").append(points).append("\n");
	    
	    res.append("\t Tokens : ").append(wallet);
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
