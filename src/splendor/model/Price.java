package splendor.model;

import java.util.HashMap;
import java.util.Map;


public class Price {
	private final Map<Stones, Integer> map = new HashMap<>();
	
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
		for(var el : map.entrySet()) {
			if(el.getValue() > price.getValue(el.getKey())) {
				return false;
			}
		}
		return true;
	}
	
	
	public Integer getValue(Stones stone) {
		return map.get(stone);
	}
}
