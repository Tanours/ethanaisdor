package splendor.model;

import java.util.ArrayList;
import java.util.List;
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

	    int ruby = wallet.getValue(Stones.RUBY);
	    int saphir = wallet.getValue(Stones.SAPHIR);
	    int diamond = wallet.getValue(Stones.DIAMOND);
	    int emerald = wallet.getValue(Stones.EMERALD);
	    int onyx = wallet.getValue(Stones.ONYX);

	    wallet = new Price(
	        ruby + (stone.equals(Stones.RUBY) ? quantity : 0),
	        saphir + (stone.equals(Stones.SAPHIR) ? quantity : 0),
	        diamond + (stone.equals(Stones.DIAMOND) ? quantity : 0),
	        emerald + (stone.equals(Stones.EMERALD) ? quantity : 0),
	        onyx + (stone.equals(Stones.ONYX) ? quantity : 0)
	    );
	}

	
	
	public boolean canBuy(Card card) {
		Objects.requireNonNull(card);
		return card.price().isBelow(wallet);
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
	    res.append("\t Nom du joueur : ").append(name).append("\n");
	    
	    res.append("\t Points : ").append(points).append("\n");
	    
	    res.append("\t Tokens : ").append(wallet);
	    res.append("\n");
	    
	    res.append("\t Bonus : \n");
	    if (!cards.isEmpty()) {
	    	var buffer = "";
	        for (Card card : cards) {
	            res.append(buffer).append("\t\t-" + card.stone());
	            buffer = "\n";
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
	
	public Price getWallet() {
		return wallet;
	}


	
}
