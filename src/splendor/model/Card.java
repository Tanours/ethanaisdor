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
	
	
	@Override
	public String toString() {
	    StringBuilder res = new StringBuilder();
	    res.append("[");
	    res.append("R:%-2s O:%-2s S:%-2s E:%-2s D:%-2s".formatted(
	    		this.price.getValue(Stones.RUBY),
	    		this.price.getValue(Stones.ONYX),
	    		this.price.getValue(Stones.SAPHIR),
	    		this.price.getValue(Stones.EMERALD),
	    		this.price.getValue(Stones.DIAMOND)
	    		));
	    res.append("]");
	    return res.toString();
	}
	
}
