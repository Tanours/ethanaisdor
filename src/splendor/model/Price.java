package splendor.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class Price {
	private final Map<Stones, Integer> map = new HashMap<>();
	
	public Price() {
		map.putIfAbsent(Stones.RUBY, 0);
		map.putIfAbsent(Stones.SAPHIR, 0);
		map.putIfAbsent(Stones.DIAMOND, 0);
		map.putIfAbsent(Stones.EMERALD, 0);
	}
	public Price(Stones stone,int value) {
		this
		(
				stone.equals(Stones.RUBY) ? value : 0,
				stone.equals(Stones.SAPHIR) ? value : 0,
				stone.equals(Stones.DIAMOND) ? value : 0,
				stone.equals(Stones.EMERALD) ? value : 0
		);
		 
	}
	public Price(int ruby, int saphir, int diamond, int emerald) {
		if(ruby < 0 || saphir < 0 || diamond < 0 || emerald < 0) {
			throw new IllegalArgumentException();
		}
		map.putIfAbsent(Stones.RUBY, ruby);
		map.putIfAbsent(Stones.SAPHIR, saphir);
		map.putIfAbsent(Stones.DIAMOND, diamond);
		map.putIfAbsent(Stones.EMERALD, emerald);
	}
	
	public boolean isBelow(Price price) {
		Objects.requireNonNull(price);
		for(var el : map.entrySet()) {
			if(el.getValue() > price.getValue(el.getKey())) {
				return false;
			}
		}
		return true;
	}
	
	public Price substract(Price price) {
		Objects.requireNonNull(price);
		var newRuby = this.getValue(Stones.RUBY) - price.getValue(Stones.RUBY);
		var newSaphir = this.getValue(Stones.SAPHIR) - price.getValue(Stones.SAPHIR);
		var newDiamond = this.getValue(Stones.DIAMOND) - price.getValue(Stones.DIAMOND);
		var newEmerald = this.getValue(Stones.EMERALD) - price.getValue(Stones.EMERALD);
		
		return new Price(newRuby,newSaphir,newDiamond,newEmerald);
	}
	
	public Integer getValue(Stones stone) {
		return map.get(stone);
	}
	
	@Override
	public String toString() {
		var builder = new StringBuilder("[");
		var buffer = "";
		for(var el : map.entrySet()) {
			builder.append(buffer).append(el.getKey()).append(" : ").append(el.getValue());
			buffer = "\n";
		}
		builder.append("]");
		return builder.toString();
	}
}
