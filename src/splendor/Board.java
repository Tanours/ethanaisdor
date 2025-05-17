package splendor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Board {
	private final HashMap<Integer, List<Card>> cards;
	private final HashMap<Stones, Integer> tokens;
	
	public Board() {
		this.cards = this.generateCards();
		this.tokens = new HashMap<Stones, Integer>();
	
	}
	
	public Map<Integer, List<Card>> getCards() {
		return Map.copyOf(cards);
	}
	
	public Map<Stones,Integer> getTokens(){
		return Map.copyOf(tokens);
	}
	
	
	public List<Card> generateCardsList(int startIndex,int level,int numberCard){
		var res = new ArrayList<Card>();
		if(level < 1 || startIndex < 0 || numberCard < 0) {
			throw new IllegalArgumentException();
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
			var prestige = level==1?0:level==2?rng.nextInt(1,3):rng.nextInt(3,5);
			var id = startIndex;
			res.add(new Card(id,bonus,needStones,prestige));
			startIndex++;
		}
		return res;
	}
	
	public List<Card> generateCardsListWithCost(int startIndex,int level,int numberCardByColor,int cost){
		var resCard = new ArrayList<Card>();
		var index = startIndex;
		for(int i = 0;i<numberCardByColor;i++) {
			for(int ColorIndex = 0;ColorIndex<5;ColorIndex++) {
				var stone = Stones.values()[ColorIndex];
				
				resCard.add(new Card(index,stone,new HashMap<>(Map.of(stone, cost)),1));
			}
		}
		return resCard;
	}
	public HashMap<Integer, List<Card>> generateCards(){
		var res = new HashMap<Integer, List<Card>>();
		res.put(1, this.generateCardsListWithCost(1,1,8,3));
		
		return res;
	}
	
	public HashMap<Stones, Integer> generateTokens() {
		var res = new HashMap<Stones, Integer>();
		
		res.put(Stones.SAPHIR, 7);
		res.put(Stones.EMERALD, 7);
		res.put(Stones.RUBY, 7);
		res.put(Stones.DIAMOND, 7);
		res.put(Stones.ONYX, 7);
		res.put(Stones.GOLDJOKER, 5);
		
		return res;
	}
	
	
	public void revealCards() {
		
	}
	public boolean selectTokens(Player player,Stones stone,int count) {
		Objects.requireNonNull(player);
		if(count <= 0) {
			throw new IllegalArgumentException();
		}
		if(tokens.get(stone) < 4) {
			return false;
		}
		tokens.computeIfPresent(stone, (s,curr) -> curr = curr-count);
		player.tokens.merge(stone,count,Integer::sum);
		return true;
		
	}
}
