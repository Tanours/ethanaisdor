package splendor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Board {
	private final HashMap<Integer, List<Card>> cards;
	private final HashMap<Stones, Integer> tokens;

	public Board() {
		this.cards = this.generateCards();
		this.tokens = this.generateTokens();
	}

	public Map<Integer, List<Card>> getCards() {
		return Map.copyOf(cards);
	}

	public Map<Stones, Integer> getTokens() {
		return Map.copyOf(tokens);
	}

	public boolean takeToken(Player player, Map<Stones, Integer> stones) {
		return false;

	}

	public List<Card> generateCardsList(int startIndex, int level, int numberCard) {
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
	}

	public List<Card> generateCardsListWithCost(int startIndex, int level, int numberCardByColor, int cost) {
		var resCard = new ArrayList<Card>();
		var index = startIndex;
		for (int i = 0; i < numberCardByColor; i++) {
			for (int ColorIndex = 0; ColorIndex < 5; ColorIndex++) {
				var stone = Stones.values()[ColorIndex];

				resCard.add(new Card(index, stone, new HashMap<>(Map.of(stone, cost)), 1));
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

	public void revealCards() {
		for (int i = 0; i < 4; i++) {
			System.out.print(i + 1 + " : " + cards.get(1).get(i));
		}
	}

	public boolean selectTokens(Player player, Stones stone, int count) {
		Objects.requireNonNull(player);
		Objects.requireNonNull(stone);

		if (count <= 0 || count > 2) {
			return false;
		}

		int available = tokens.getOrDefault(stone, 0);

		if (count == 2) {
			if (available < 4) {
				return false;
			}
		} else {
			if (available < 1) {
				return false;
			}
		}

		tokens.computeIfPresent(stone, (s, curr) -> curr = curr - count);
		player.tokens.merge(stone, count, Integer::sum);
		
		return true;
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

	private HashMap<Integer, List<Card>> CardReferenceReader(String filePath) throws IOException {
		Objects.requireNonNull(filePath);
		var res = new HashMap<Integer, List<Card>>();
		var file = Paths.get(filePath);
		var listString = new ArrayList<List<String>>();

		try (var reader = Files.newInputStream(file)) {
			var byt = reader.readAllBytes();
			var html = new String(byt);
			var rowPattern = Pattern.compile("<tr.*?>(.*?)</tr>");
			var matcher = rowPattern.matcher(html);
			var level = "";
			var gem = "";
			while (matcher.find()) {
				var row = new ArrayList<String>();
				var content = matcher.group(1);
				var cellPattern = Pattern.compile("<td.*?>(.*?)</td>");
				var cellMatcher = cellPattern.matcher(content);
				var priceRow = 3;
				var illustrationRow = 4;
				var columnIndex = 0;
				while (cellMatcher.find()) {
					if (columnIndex != priceRow && columnIndex != illustrationRow) {
						var cellValue = cellMatcher.group(1).trim().replaceAll("<[^>]*>", "");
						switch (columnIndex) {
						case 2, 5, 6, 7, 8, 9:
							if (cellValue.isEmpty()) {
								cellValue = "0";
							}
							row.add(cellValue);
							break;
						case 1:
							if (!cellValue.isEmpty()) {
								gem = cellValue;
							} else {
								cellValue = gem;
							}
							row.add(cellValue);
							break;
						case 0:
							if (!cellValue.isEmpty() && cellValue.matches("\\d+")) {
								level = cellValue;
							} else {
								cellValue = level;
							}
							row.add(cellValue);
							break;
						default:
							break;
						}

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
				res.merge(Integer.parseInt(element.get(0)),
						new ArrayList<Card>(List.of(this.listToCard(index, element))), (curr, newlist) -> {
							curr.addAll(newlist);
							return curr;
						});
				index++;
			}
		} catch (IOException e) {
			System.out.println(Color.RED.getValue() + "Error : " + Color.RESET.getValue() + "cannot read file");
		}
		return res;
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
		if (Integer.parseInt(list.get(3)) != 0)
			hash.putIfAbsent(Stones.DIAMOND, Integer.parseInt(list.get(3)));
		if (Integer.parseInt(list.get(4)) != 0)
			hash.putIfAbsent(Stones.SAPHIR, Integer.parseInt(list.get(4)));
		if (Integer.parseInt(list.get(5)) != 0)
			hash.putIfAbsent(Stones.EMERALD, Integer.parseInt(list.get(5)));
		if (Integer.parseInt(list.get(6)) != 0)
			hash.putIfAbsent(Stones.RUBY, Integer.parseInt(list.get(6)));
		if (Integer.parseInt(list.get(7)) != 0)
			hash.putIfAbsent(Stones.ONYX, Integer.parseInt(list.get(7)));

		return new Card(id, gem, hash, Integer.parseInt(list.get(2)));
	}

}
