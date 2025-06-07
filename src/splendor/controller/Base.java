
package splendor.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import splendor.model.Card;
import splendor.model.Noble;
import splendor.model.Price;
import splendor.model.Stones;

public record Base() implements GamePhase {

	@Override
	public boolean nobles() {
		// TODO Auto-generated method stub
		return false;
	}
	private List<Card> generateCardsListWithCost(int startIndex, int level, int numberCardByColor, int cost) {
		var resCard = new ArrayList<Card>();
		var index = startIndex;
		for (int i = 0; i < numberCardByColor; i++) {
			for (int ColorIndex = 0; ColorIndex < 5; ColorIndex++) {
				var stone = Stones.values()[ColorIndex];

				resCard.add(new Card(index, stone, new Price(stone,cost), 1));
			}
		}
		return resCard;
	}

	
	
	@Override
	public boolean reservation() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Map<Integer, List<Card>> initCards() {
	    var hash = new HashMap<Integer, List<Card>>();
	    var level1Cards = generateCardsListWithCost(1, 1, 8, 1);
	    //Collections.shuffle(level1Cards); 
	    hash.put(1, level1Cards);
	    return hash;
	}

	public List<Noble> initNobles(){
		return List.of();
	}
	public int getMaxCard() {
		return 4;
	}
	public int getMaxChoice() {
		return 3;
	}
}
