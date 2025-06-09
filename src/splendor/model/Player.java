package splendor.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Player {

	private final String name;
	private final int age;
	private final List<Card> cards;
	private final List<Card> reservedCards;
	private final List<Noble> nobles;
	private int points;
	private Price wallet;

	public Player(String name, int age) {
		Objects.requireNonNull(name, "name of player can't be null");

		if (age < 0) {
			throw new IllegalArgumentException("age of player can't be negative");
		}

		this.name = name;
		this.age = age;
		nobles = new ArrayList<>();
		points = 0;
		cards = new ArrayList<>();
		reservedCards = new ArrayList<>();
		wallet = new Price();

	}

	public Player(String name, int age, int points, Price wallet, List<Card> cards, List<Card> reservedCard,
			List<Noble> nobles) {
		Objects.requireNonNull(name, "name of player can't be null");
		Objects.requireNonNull(wallet);
		if (points < 0) {
			throw new IllegalArgumentException("point can't be negative");
		}

		this.name = name;
		this.age = age;
		this.points = points;
		this.cards = cards;
		this.wallet = wallet;
		this.nobles = nobles;
		this.reservedCards = reservedCard;
	}

	public boolean addToken(Stones stone, int quantity) {
		Objects.requireNonNull(stone);

		int ruby = wallet.getValue(Stones.RUBY);
		int saphir = wallet.getValue(Stones.SAPHIR);
		int diamond = wallet.getValue(Stones.DIAMOND);
		int emerald = wallet.getValue(Stones.EMERALD);
		int onyx = wallet.getValue(Stones.ONYX);
		int goldJoker = wallet.getValue(Stones.GOLDJOKER);

		wallet = new Price(ruby + (stone.equals(Stones.RUBY) ? quantity : 0),
				saphir + (stone.equals(Stones.SAPHIR) ? quantity : 0),
				diamond + (stone.equals(Stones.DIAMOND) ? quantity : 0),
				emerald + (stone.equals(Stones.EMERALD) ? quantity : 0),
				onyx + (stone.equals(Stones.ONYX) ? quantity : 0),
				goldJoker + (stone.equals(Stones.GOLDJOKER) ? quantity : 0));
		return true;
	}

	public boolean canBuy(Card card) {
		Objects.requireNonNull(card);

		return card.price().isBelow(wallet);
	}

	public boolean canBuyWithJoker(Card card) {
		Objects.requireNonNull(card);
		var price = card.price();
		var missingTotal = calculateMissingTokens(price).values().stream().mapToInt(Integer::intValue).sum();
		var jokers = wallet.getValue(Stones.GOLDJOKER);

		return missingTotal <= jokers;
	}

	private Map<Stones, Integer> calculateMissingTokens(Price price) {
		Objects.requireNonNull(price);
		var missing = new HashMap<Stones, Integer>();

		for (var stone : List.of(Stones.RUBY, Stones.SAPHIR, Stones.DIAMOND, Stones.EMERALD, Stones.ONYX)) {
			var required = price.getValue(stone);
			var owned = wallet.getValue(stone);
			var diff = required - owned;
			if (diff > 0) {
				missing.put(stone, diff);
			}
		}
		return missing;
	}

	public List<Stones> getBonus() {
		return cards.stream().map(Card::stone).filter(stone -> !stone.equals(Stones.GOLDJOKER)).toList();
	}

	private Card getCardSubstractByBonus(Card card) {
		var bonus = this.getBonus();
		var realPrice = card.price();
		if (bonus.isEmpty())
			return card;
		for (var stone : bonus) {
			realPrice = realPrice.getValue(stone) == 0 ? realPrice : realPrice.substract(new Price(stone, 1));
		}
		return new Card(card.id(), card.stone(), realPrice, card.prestige());
	}

	public boolean buyCard(Card card) {
		Objects.requireNonNull(card);
		var realCard = getCardSubstractByBonus(card);
		if (this.canBuy(realCard)) {
			wallet = wallet.substract(realCard.price());
			cards.add(card);
			this.addPrestige(card.prestige());
			return true;
		} else if (this.canBuyWithJoker(realCard)) {
			var missing = calculateMissingTokens(card.price());
			for (var miss : missing.entrySet()) {
				this.addToken(miss.getKey(), 1);
			}
			wallet = wallet.substract(new Price(0, 0, 0, 0, 0, missing.entrySet().stream()
					.mapToInt(entry -> entry.getValue()).sum()
					));
			wallet = wallet.substract(realCard.price());
			cards.add(card);
			this.addPrestige(card.prestige());
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		var res = new StringBuilder();
		var boardHorizontal = "╔════════════════════════════════CARTE DE JOUEUR═══╗\n";
		var boardHorizontalEnd = "╚══════════════════════════════════════════════════╝\n";
		var centre = "".repeat(6);

		res.append(centre).append(boardHorizontal);

		res.append(centre).append("╠ Nom du joueur : %-32s ║\n️".formatted(name));

		res.append(centre).append("╠ Points : %-39s ║\n".formatted(points));

		res.append(centre).append("╠ Tokens : %-39s ║\n".formatted(wallet));
		if (reservedCards.size() > 0) {
			res.append(centre).append("╠ ReservedCards : %-32s ║\n".formatted(reservedCards.size()));
		}
		res.append(centre).append("╠ Bonus : %-40s ║\n".formatted(""));
		if (!cards.isEmpty()) {
			var buffer = "";
			for (Card card : cards) {
				res.append(buffer).append(centre).append("║\t\t- %-32s ║".formatted(card.stone()));
				buffer = "\n";
			}
			res.append("\n");
		}
		res.append(centre).append(boardHorizontalEnd).append(Stones.resetColor());
		return res.toString();
	}

	public boolean addReserved(Card card) {

		return (reservedCards.size() >= 3) ? false : reservedCards.add(card);// && addToken(Stones.GOLDJOKER, 1);
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Player player && player.age == this.age && player.points == this.points
				&& player.name.equals(this.name) && player.cards.equals(this.cards)
				&& player.wallet.equals(this.wallet);

	}

	@Override
	public int hashCode() {
		return name.hashCode() ^ Integer.hashCode(age) ^ cards.hashCode() ^ Integer.hashCode(points)
				^ wallet.hashCode();
	}

	public String getName() {
		return name;
	}

	public int getPoints() {
		return points;
	}

	public int getAge() {
		return age;
	}

	public Price getWallet() {
		return wallet;
	}

	public List<Noble> getNobles() {
		return nobles;
	}

	public List<Card> getReserved() {
		return this.reservedCards;
	}

	public void addPrestige(int points) {
		this.points += points;
	}

	public Price getBonusAsPrice() {
		var ruby = 0;
		var saphir = 0;
		var diamond = 0;
		var emerald = 0;
		var onyx = 0;

		for (Card card : cards) {
			Stones stone = card.stone();
			switch (stone) {
			case RUBY -> ruby++;
			case SAPHIR -> saphir++;
			case DIAMOND -> diamond++;
			case EMERALD -> emerald++;
			case ONYX -> onyx++;
			default -> {
			}
			}
		}
		return new Price(ruby, saphir, diamond, emerald, onyx);
	}

}
