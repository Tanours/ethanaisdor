package splendor.model;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import splendor.controller.Base;
import splendor.controller.Complet;
import splendor.controller.GamePhase;
import splendor.view.DisplayCards;

public class Board {
	private final Map<Integer, List<Card>> cards;
	private final List<Noble> nobles;
	private final HashMap<Stones, Integer> tokens;
	
	public Board() {
		this(new Base());
	}
	public Board(GamePhase phase) {
		this.cards = phase.initCards();
		this.tokens = this.generateTokens();
		this.nobles = phase.initNobles();
	}
	
	public List<Noble> allNobleVisit(Player player) {
		Objects.requireNonNull(player);
		
		var res = new ArrayList<Noble>();
		
		for(var noble : nobles) {
			if(noble.canVisit(player)) {
				res.add(noble);
			}
		}
		
		return res;
	}
	
	public List<Noble> getNobles() {
		return List.copyOf(nobles);
	}
	
	public Map<Integer, List<Card>> getCards() {
		return Map.copyOf(cards);
	}

	public Map<Stones, Integer> getTokens() {
		return Map.copyOf(tokens);
	}

	public boolean takeToken(Player player, Stones stone) {
		Objects.requireNonNull(player);
		Objects.requireNonNull(stone);
		
		tokens.put(stone, tokens.get(stone) - 1);
	    player.addToken(stone, 1);
	    
		return true;
	}
	
	public boolean takeMultipleSameTokens(Player player, Stones stone, int count) {
		Objects.requireNonNull(player);
		Objects.requireNonNull(stone);
		
		if(count < 0) {
			return false;
		}
		
		tokens.put(stone, tokens.get(stone) - count);
		player.addToken(stone, count);
		
		return true;	
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
		System.out.println(new DisplayCards(cards));
	}

	public boolean selectTokens(Player player, Stones stone, int count) {
	    Objects.requireNonNull(player);
	    Objects.requireNonNull(stone);

	    if (count < 1 || count > 2) return false;

	    int available = tokens.getOrDefault(stone, 0);
	    return (count == 1 && available >= 1) || (count == 2 && available >= 4);
	}
	public boolean reserveCard(Player player, Card card) {
		Objects.requireNonNull(player);
		Objects.requireNonNull(card);
		for (var cardList : cards.values()) {
			if (cardList.remove(card)) {
				break;
			}
		}
		
		return player.addReserved(card) && this.takeToken(player, Stones.GOLDJOKER);
	}
	
	public void addPrice(Price price) {
		Objects.requireNonNull(price);
		
	    for (var stone : Stones.values()) {
	        var toAdd = price.getValue(stone);
	        tokens.merge(stone,toAdd,Integer::sum);
	    }
	}
	public void revealTokens() {
		var build = new StringBuilder();
		build.append("Token : ");
		for(var token : tokens.entrySet()) {
			var stone = token.getKey();
			build.append("%s●%s %s ".formatted(stone.getColor(),Stones.resetColor(),token.getValue()));

		}
		System.out.println(build.toString());
	}
	public boolean selectCard(Player player, Card card) {
		Objects.requireNonNull(player);
		Objects.requireNonNull(card);
		if (!player.buyCard(card,this)) {
			
			System.out.println(card.price());
			return false;
		}
		


		for (var cardList : cards.values()) {
			if (cardList.remove(card)) {
				break;
			}
		}

		return true;
	}

}
