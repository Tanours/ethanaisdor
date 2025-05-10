package splendor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Plateau {
	private final HashMap<Integer, List<Card>> cards;
	private final HashMap<Stones, Integer> tokens;
	
	public Plateau() {
		this.cards = new HashMap<Integer, List<Card>>();
		this.tokens = new HashMap<Stones, Integer>();
	}
	public List<Card> GenerateCardsList(int startIndex,int level,int numberCard){
		var res = new ArrayList<Card>();
		if(level < 1) {
			throw new IllegalArgumentException("Prestige cannot be negative");
		}
		var rng = ThreadLocalRandom.current();
		for(int i = 0;i<numberCard;i++) {
			var bonus = Stones.values()[rng.nextInt(6)];
			var nbNeedStones = rng.nextInt(1,4);
			var needStones = new HashMap<Stones,Integer>();
			for (int j = 0;j<nbNeedStones;j++) {
				if(needStones.putIfAbsent(Stones.values()[rng.nextInt(6)], rng.nextInt(1,level+1))!=null) {
					j--;
				}
				
			}
			var prestige = level>1?rng.nextInt(level-1,level):0;
			var id = startIndex;
			res.add(new Card(id,bonus,needStones,prestige));
			startIndex++;
		}
		return res;
	}
	public HashMap<Integer, List<Card>> GenerateCards(){
		var res = new HashMap<Integer, List<Card>>();
		res.put(1, this.GenerateCardsList(0,1,40));
		res.put(2, this.GenerateCardsList(40,2,30));
		res.put(3, this.GenerateCardsList(70,3,20));
		
		return res;
	}
}
