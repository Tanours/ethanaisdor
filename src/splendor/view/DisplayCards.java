package splendor.view;

import splendor.model.Card;
import splendor.model.Stones;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Objects;
import java.util.StringJoiner;

public record DisplayCards(TreeMap<Integer,List<Card>> cardsMap) {
	public DisplayCards{
		Objects.requireNonNull(cardsMap);
	}
	public DisplayCards(Map<Integer,List<Card>> cardsMap){
		this(new TreeMap<Integer, List<Card>>(cardsMap));
	}
	public DisplayCards(List<Card> cards) {
		this(Map.of(1,cards));
	}
	
	private String getNbToken(int nb) {
		var build = new StringJoiner(" ");
		for(var i = 0; i<nb;i++) build.add("●");
		return build.toString();
	}
	private String getCardElement(String value,List<Card> cards, int level) {
		Objects.requireNonNull(value);
		Objects.requireNonNull(cards);
		
		var res = new StringBuilder();
		res.append("\n");
		
		if(value.equals("header")) {
			var firstCardId = cards.get(0).id();
			//var cardGap = firstCardId >= 70 ? 8 : firstCardId >= 40 ? 4 : 0;
			var cardGap = switch(level) {
			case 2 -> 4;
			case 3 -> 8;
			default -> 0;
			};
			for(var i = 1;i<= cards.size();i++) {
				var card = cards.get(i-1);
				var stoneElement = "║ %-8s PRESTIGE +%-2s   %8s ║".formatted(cardGap+i,card.prestige(),card.stone());
				res.append(stoneElement).append(" ");
			}
		}
		else {
			for(int i = 0; i<5;i++) {
				var stone = Stones.values()[i];
				for(var card : cards) {
					var price = card.price().getValue(stone);
					var build = getNbToken(price);
					var stoneElement = "║ %-8s : %s%-21s%s ║".formatted(stone,stone.getColor(),build.toString(),Stones.resetColor());
					res.append(stoneElement).append(" ");
				}
				res.append("\n");
			}
		}
			
		
		
		return res.toString();
		
	}
	private String getLine(List<Card> cards, int level) {
		var cardsLimited = cards.stream().limit(4).toList();
		var res = new StringBuilder();
		var boardHorizontal = "╔══════════════════════════════════╗"; 
		var boardHorizontalEnd = "╚══════════════════════════════════╝";
		for(var card : cardsLimited) {
			res.append(boardHorizontal).append(" ");
			
		};
		var info = getCardElement("header",cardsLimited, level);
		res.append(info);
		res.append("\n");
		for(var card : cardsLimited) {
			res.append("╠------------- cost ---------------╣").append(" ");
			
		}
		var cost = getCardElement("cost",cardsLimited, level);
		res.append(cost);
		for(var card : cardsLimited) {
			res.append(boardHorizontalEnd).append(" ");
			
		}
		
		
		return res.toString();
	}
	@Override
	public String toString() {
		var joiner = new StringJoiner("\n");
	
		for(var el : cardsMap.entrySet()) {
			joiner.add(getLine(el.getValue(), el.getKey()));
		}
		
		return joiner.toString();
	}
}
