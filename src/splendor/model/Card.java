package splendor.model;


import java.util.List;
import java.util.Objects;

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
	private static String getCardElement(String value, List<Card> cards) {
		var res = new StringBuilder();
		res.append("\n");
		
		if(value.equals("header")) {
			for(var i = 1;i<= cards.size();i++) {
				var card = cards.get(i-1);
				var stoneElement = "║ %-8s PRESTIGE +%-2s   %8s ║".formatted(i,card.prestige(),card.stone());
				res.append(stoneElement).append(" ");
			}
		}
		else {
			for(int i = 0; i<5;i++) {
				var stone = Stones.values()[i].toString();
				for(var card : cards) {
					var stoneElement = "║ %-8s : %-21s ║".formatted(stone,card.price.getValue(Stones.valueOf(stone)));
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
