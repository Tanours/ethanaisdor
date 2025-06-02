package splendor.model;


import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public record Card(int id, Stones stone , Price price, int prestige) {
	public Card{
		if(id<0||prestige<0) {
			throw new IllegalArgumentException();
		}
		Objects.requireNonNull(stone);
		Objects.requireNonNull(price);
		
	}
	
	//@Override
//	public String toString() {
//		return "[" + stone + ", " + prestige + ", " + price + "]\n";
//	}
	private static String getNbToken(int nb) {
		var build = new StringJoiner(" ");
		for(var i = 0; i<nb;i++) build.add("●");
		return build.toString();
	}
	private static String getCardElement(String value, List<Card> cards) {
		var res = new StringBuilder();
		res.append("\n");
		
		if(value.equals("header")) {
			var firstCardId = cards.get(0).id();
			var cardGap = firstCardId >= 70 ? 8 : firstCardId >= 40 ? 4 : 0;
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
					var price = card.price.getValue(stone);
					var build = getNbToken(price);
					var stoneElement = "║ %-8s : %s%-21s%s ║".formatted(stone,stone.getColor(),build.toString(),Stones.resetColor());
					res.append(stoneElement).append(" ");
				}
				res.append("\n");
			}
		}
			
		
		
		return res.toString();
		
	}
	public static String displayCards(List<Card> cards) {
		var res = new StringBuilder();
		var boardHorizontal = "╔══════════════════════════════════╗"; 
		var boardHorizontalEnd = "╚══════════════════════════════════╝";
		for(var card : cards) {
			res.append(boardHorizontal).append(" ");
			
		};
		var info = getCardElement("header", cards);
		res.append(info);
		res.append("\n");
		for(var card : cards) {
			res.append("╠------------- cost ---------------╣").append(" ");
			
		}
		var cost = getCardElement("cost", cards);
		res.append(cost);
		for(var card : cards) {
			res.append(boardHorizontalEnd).append(" ");
			
		}
		
		
		return res.toString();
	}
	
	@Override
	public String toString() {
	    StringBuilder res = new StringBuilder();
	    res.append("[");
	    res.append("S:%-2s R:%-2s D:%-2s O:%-2s".formatted(
	    		this.price.getValue(Stones.SAPHIR),
	    		this.price.getValue(Stones.EMERALD),
	    		this.price.getValue(Stones.RUBY),
	    		this.price.getValue(Stones.DIAMOND),
	    		this.price.getValue(Stones.ONYX)
	    		));
	    res.append("]");
	    return res.toString();
	}
	
}
