package splendor.model;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
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

public class Board {
	private final HashMap<Integer, List<Card>> cards;
	private final List<Noble> nobles;
	private final HashMap<Stones, Integer> tokens;

	public Board() {
		this.cards = this.generateCards();
		this.tokens = this.generateTokens();
		this.nobles = this.initNobles();
	}

	private List<Noble> initNobles() {
		var res = new ArrayList<Noble>();
		
		//res.add(new Noble(0, ))
		
		return res;
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

	public boolean takeOneToken(Player player, Stones stone) {
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
		for(var i = 0; i < count; i++) {
			tokens.put(stone, tokens.get(stone) - 1);
		    player.addToken(stone, 1);
		}
		
		return true;
		
	}

	/*public List<Card> generateCardsList(int startIndex, int level, int numberCard) {
		var res = new ArrayList<Card>();
		if (level < 1 || startIndex < 0 || numberCard < 0) {
			throw new IllegalArgumentException();
		}
		var rng = ThreadLocalRandom.current();
		for (int i = 0; i < numberCard; i++) {
			var bonus = Stones.values()[rng.nextInt(6)];
			var nbNeedStones = rng.nextInt(1, 4);
			var needStones = new ArrayList<Price>();
			for (int j = 0; j < nbNeedStones; j++) {
				if (needStones.putIfAbsent(Stones.values()[rng.nextInt(6)], rng.nextInt(1, level + 1)) != null) {
					j--;
				}

			}
			var prestige = level == 1 ? 0 : level == 2 ? rng.nextInt(1, 3) : rng.nextInt(3, 5);
			var id = startIndex;
			res.add(new Card(id, bonus, needStones, prestige));
			startIndex++;
		}
		return res;
	}*/

	public List<Card> generateCardsListWithCost(int startIndex, int level, int numberCardByColor, int cost) {
		var resCard = new ArrayList<Card>();
		var index = startIndex;
		for (int i = 0; i < numberCardByColor; i++) {
			for (int ColorIndex = 0; ColorIndex < 5; ColorIndex++) {
				var stone = Stones.values()[ColorIndex];

				resCard.add(new Card(index, stone, new Price(stone,cost), 1));
			}
		}
		return resCard;
	}

	public HashMap<Integer, List<Card>> generateCards() {
		var hash = new HashMap<Integer, List<Card>>();
		hash.putIfAbsent(1, generateCardsListWithCost(1, 1, 8, 1));
		return hash;

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

	public void revealCards(GamePhase phase) {
		List<Card> reveal;
		
		switch(phase) {
		case Base b -> {
				reveal = cards.get(1).stream().limit(4).toList();
				System.out.println(Card.displayCards(reveal));
			}
		case Complet c -> {
			for(var level: cards.entrySet()) {
				reveal = cards.get(level.getKey()).stream().limit(4).toList();
				System.out.println(Card.displayCards(reveal));
			}
		}
			
		default -> throw new IllegalArgumentException("Unexpected value: " + phase);
		}
	}

	public boolean selectTokens(Player player, Stones stone, int count) {
	    Objects.requireNonNull(player);
	    Objects.requireNonNull(stone);

	    if (count < 1 || count > 2) return false;

	    int available = tokens.getOrDefault(stone, 0);
	    return (count == 1 && available >= 1) || (count == 2 && available >= 4);
	}

	public boolean selectCard(Player player, Card card) {
		Objects.requireNonNull(player);
		Objects.requireNonNull(card);

		if (!player.canBuy(card)) {

			return false;
		}

		for (List<Card> cardList : cards.values()) {
			if (cardList.remove(card)) {
				break;
			}
		}
		
		player.buyCard(card);

		return true;
	}

	public HashMap<Integer, List<Card>> CardReferenceReader(String filePath) throws IOException {
		Objects.requireNonNull(filePath);
		var res = new HashMap<Integer, List<Card>>();
		var file = Paths.get(filePath);
		var listString = new ArrayList<List<String>>();
		var level = "";
		var gem = "";
		try (var reader = Files.newInputStream(file)) {
			var matcher = lineReferenceReader(reader);
			
			while (matcher.find()) {
				var cellMatcher = cellReferenceReader(matcher);
				var row = new ArrayList<String>();
				var columnIndex = 0;
				while (cellMatcher.find()) {
				var cellValue = cellMatcher.group(1).trim().replaceAll("<[^>]*>", "");
				switch (columnIndex) {
				case 2, 5, 6, 7, 8, 9:
					row.add(cellValue.isEmpty() ? "0" : cellValue);
					break;
				case 1:
					gem = !cellValue.isEmpty() ? cellValue : gem;
					row.add(gem);
					break;
				case 0:
					level = (!cellValue.isEmpty() && cellValue.matches("\\d+")) ? cellValue : level;
					row.add(level);
					break;
				default:
					break;
				}
					columnIndex++;
				}
				row = new ArrayList<>(row.stream().filter(s -> !s.isEmpty()).collect(Collectors.toList()));
				if (!row.isEmpty()) {
					listString.add(row);
				}

			}
			listString = new ArrayList<>(listString.stream().skip(3).collect(Collectors.toList()));
			var index = 1;
			for (var element : listString) {
				System.out.println(element);
				res.merge(Integer.parseInt(element.get(0)),
						new ArrayList<Card>(List.of(this.listToCard(index, element))), (curr, newlist) -> {
							curr.addAll(newlist);
							return curr;
						});
				index++;
			}
		} catch (IOException e) {
			System.out.println( "Error : cannot read file");
		}
		return res;
	}
	private Matcher lineReferenceReader(InputStream reader) throws IOException{
		var byt = reader.readAllBytes();
		var html = new String(byt);
		var rowPattern = Pattern.compile("<tr.*?>(.*?)</tr>");
		var matcher = rowPattern.matcher(html);
		return matcher;
	}
	
	private Matcher cellReferenceReader(Matcher matcher) {
		var content = matcher.group(1);
		var cellPattern = Pattern.compile("<td.*?>(.*?)</td>");
		var cellMatcher = cellPattern.matcher(content);
		return cellMatcher;
	}
	
	public Card listToCard(int id, List<String> list) {
		Objects.requireNonNull(list);
		if (id < 0) {
			throw new IllegalArgumentException();
		}
		Stones gem;
		var hash = new HashMap<Stones, Integer>();
		switch (list.get(1)) {
		case "black":
			gem = Stones.ONYX;
			break;
		case "blue":
			gem = Stones.SAPHIR;
			break;
		case "white":
			gem = Stones.DIAMOND;
			break;
		case "green":
			gem = Stones.EMERALD;
			break;
		default:
			gem = Stones.RUBY;
		}
		var ruby = Integer.parseInt(list.get(6));
		var saphir = Integer.parseInt(list.get(4));
		var diamond = Integer.parseInt(list.get(3));
		var emerald = Integer.parseInt(list.get(5));
		var onyx = Integer.parseInt(list.get(7));
		
		var price = new Price(ruby,saphir,diamond,emerald,onyx);

		return new Card(id, gem, price, Integer.parseInt(list.get(2)));
	}

}
