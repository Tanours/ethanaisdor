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
		map.putIfAbsent(Stones.ONYX, 0);
	}
	public Price(Stones stone,int value) {
		this
		(
				stone.equals(Stones.RUBY) ? value : 0,
				stone.equals(Stones.SAPHIR) ? value : 0,
				stone.equals(Stones.DIAMOND) ? value : 0,
				stone.equals(Stones.EMERALD) ? value : 0,
				stone.equals(Stones.ONYX) ? value : 0
		);
		 
	}
	public Price(int ruby, int saphir, int diamond, int emerald,int onyx) {
		if(ruby < 0 || saphir < 0 || diamond < 0 || emerald < 0 || onyx < 0) {
			throw new IllegalArgumentException();
		}
		map.putIfAbsent(Stones.RUBY, ruby);
		map.putIfAbsent(Stones.SAPHIR, saphir);
		map.putIfAbsent(Stones.DIAMOND, diamond);
		map.putIfAbsent(Stones.EMERALD, emerald);
		map.putIfAbsent(Stones.ONYX, onyx);
	}
	
	public boolean isBelow(Price price) {
		Objects.requireNonNull(price);
		return this.map.entrySet().stream()
				.allMatch(s -> s.getValue() <= price.getValue(s.getKey()));
		
	}
	
	public Price substract(Price price) {
		Objects.requireNonNull(price);
		var newRuby = this.getValue(Stones.RUBY) - price.getValue(Stones.RUBY);
		var newSaphir = this.getValue(Stones.SAPHIR) - price.getValue(Stones.SAPHIR);
		var newDiamond = this.getValue(Stones.DIAMOND) - price.getValue(Stones.DIAMOND);
		var newEmerald = this.getValue(Stones.EMERALD) - price.getValue(Stones.EMERALD);
		var newOnyx = this.getValue(Stones.ONYX) - price.getValue(Stones.ONYX);
		
		
		return new Price(newRuby,newSaphir,newDiamond,newEmerald,newOnyx);
	}
	
	public Integer getValue(Stones stone) {
		return map.getOrDefault(stone, 0);
	}
	
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
	    res.append("[");
	    res.append(" S:%-2s E:%-2s R:%-2s D:%-2s O:%-2s".formatted(
	    		this.getValue(Stones.SAPHIR),
	    		this.getValue(Stones.EMERALD),
	    		this.getValue(Stones.RUBY),
	    		this.getValue(Stones.DIAMOND),
	    		this.getValue(Stones.ONYX)
	    		));
	    res.append("]");
	    return res.toString();
	}
	@Override 
	public boolean equals(Object o) {
		return o instanceof Price price
				&& price.map.equals(this.map);
	}
	
	@Override
	public int hashCode() {
		return map.hashCode();
	}
}
