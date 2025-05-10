package splendor;

import java.util.HashMap;

public class Main {

	public static void main(String[] args) {
		var count = new HashMap<Stones, Integer>();
		count.put(Stones.GOLDJOKER, 2);
		count.put(Stones.DIAMOND, 1);
		var card = new Card(Stones.RUBY, count , 16);
		
		var count2 = new HashMap<Stones, Integer>();
		count.put(Stones.GOLDJOKER, 8);
		count.put(Stones.RUBY, 3);
		var card2 = new Card(Stones.SAPHIR, count, 6);
		
		var joueur1 = new Joueur("anaïs");
		System.out.println(joueur1);
		joueur1.cards.add(card);
		joueur1.cards.add(card2);
		System.out.println(joueur1);

	}

}
