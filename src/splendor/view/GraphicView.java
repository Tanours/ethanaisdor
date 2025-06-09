package splendor.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.github.forax.zen.Application;
import com.github.forax.zen.ApplicationContext;
import com.github.forax.zen.Event;
import com.github.forax.zen.PointerEvent;

import splendor.model.Board;
import splendor.model.Card;
import splendor.model.GraphicInfo;
import splendor.model.Noble;
import splendor.model.Player;
import splendor.model.Price;
import splendor.model.Stones;

public class GraphicView {
	private final List<Player> players;
	private final Board board;
	private final ApplicationContext ctx;
	private final int screenWidth;
	private final int screenHeight;

	public GraphicView(List<Player> players, Board board, ApplicationContext ctx) {
		Objects.requireNonNull(players);
		Objects.requireNonNull(board);
		this.players = players;
		this.board = board;
		this.ctx = ctx;
		this.screenWidth = ctx.getScreenInfo().width();
		this.screenHeight = ctx.getScreenInfo().height();
	}

	public void drawBoard() {
		Player player = new Player("Anaïs", 23);
		player.addToken(Stones.RUBY, 2);
		player.addToken(Stones.DIAMOND, 1);
		player.addToken(Stones.SAPHIR, 3);
		player.addToken(Stones.EMERALD, 0);
		player.addToken(Stones.GOLDJOKER, 1);
		player.addToken(Stones.ONYX, 1);
		var c1 = new Card(1, Stones.DIAMOND, new Price(1, 0, 0, 0, 0), 2);
		var c2 = new Card(1, Stones.DIAMOND, new Price(0, 1, 0, 0, 0), 2);
		board.selectCard(player, c1);
		board.selectCard(player, c2);

		var viewCardHeight = (int) (screenHeight * 0.23);
		var viewCardWidth = (int) (screenWidth * 0.1);
		var x = screenWidth / 2;
		var topMargin = 40;
		var verticalGap = 40;

		ctx.renderFrame(g2d -> {

			var levelIndex = 0;
			for (var level : board.getCards().entrySet()) {
				var cards = level.getValue().stream().limit(4).toList();
				var y = topMargin + levelIndex * (viewCardHeight + verticalGap);
				drawCards(g2d, cards, x, y, viewCardWidth, viewCardHeight, 20);
				levelIndex++;
			}

			var lastCardY = topMargin + (levelIndex - 1) * (viewCardHeight + verticalGap);
			var tokensY = lastCardY + viewCardHeight + 40;
			var tokensX = x + 3 * (20);
			drawTokens(g2d, board.getTokens(), tokensX, tokensY, 60, 25);
			drawPlayerInfo(g2d, player, screenWidth / 6, 50);

		});
	}

	public Card getCardFromView(PointerEvent event, List<Card> cards) {
		if (event == null)
			return null;
		var x = event.location().x();
		var y = event.location().y();
		for (var card : cards) {
			var x_min = card.graphicInfo().getX();
			var y_min = card.graphicInfo().getY();
			var x_max = x_min + card.graphicInfo().getWidth();
			var y_max = y_min + card.graphicInfo().getHeight();
			if ((x >= x_min && x <= x_max) && (y >= y_min && y <= y_max)) {
				return card;
			}

		}
		return null;
	}

	public Card getCardFromView(PointerEvent event, Map<Integer, List<Card>> cards) {
		if (event == null)
			return null;

		int x = event.location().x();
		int y = event.location().y();

		for (List<Card> cardList : cards.values()) {
			for (Card card : cardList) {
				var info = card.graphicInfo();
				int xMin = info.getX();
				int yMin = info.getY();
				int xMax = xMin + info.getWidth();
				int yMax = yMin + info.getHeight();

				if (x >= xMin && x <= xMax && y >= yMin && y <= yMax) {
					return card;
				}
			}
		}
		return null;
	}

	public void drawCards(Graphics2D g2d, List<Card> cards, int x, int y, int cardWidth, int cardHeight, int gap) {
		Objects.requireNonNull(cards);
		Objects.requireNonNull(g2d);

		var realCards = cards.stream().limit(4).toList();
		for (int i = 0; i < realCards.size(); i++) {
			var card = realCards.get(i);
			if (card.image() != null) {
				int cardX = x + i * (cardWidth + gap);
				int cardY = y;
				card.graphicInfo().setX(cardX);
				card.graphicInfo().setY(cardY);
				card.graphicInfo().setHeight(cardHeight);
				card.graphicInfo().setWidth(cardWidth);
				g2d.drawImage(card.image(), cardX, cardY, cardWidth, cardHeight, null);
			}
		}
	}

	private void drawTokens(Graphics2D g2d, Price wallet, int startX, int startY, int size, int gap) {
		int x = startX;
		for (var stone : Stones.values()) {
			var quantity = wallet.getValue(stone);
			if (quantity <= 0)
				continue;

			var color = getColorStone(stone, x, startX, startY, size);

			g2d.setPaint(color);
			g2d.fillOval(x, startY, size, size);

			g2d.setColor(Color.BLACK);
			g2d.drawString("x" + quantity, (x + size / 4) + 1, (startY + size / 2 + 4) + 1);

			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font("Arial", Font.BOLD, 16));
			g2d.drawString("x" + quantity, x + size / 4, startY + size / 2 + 4);

			x += size + gap;
		}
	}

	public void drawTokens(Graphics2D g2d, Map<Stones, Integer> tokens, int startX, int startY, int size, int gap) {
		int x = startX;
		for (var entry : tokens.entrySet()) {
			var stone = entry.getKey();
			var quantity = entry.getValue();

			var color = getColorStone(stone, x, startX, startY, size);
			g2d.setPaint(color);
			g2d.fillOval(x, startY, size, size);

			g2d.setColor(Color.BLACK);
			g2d.drawString("x" + quantity, (x + size / 4) + 1, (startY + size / 2 + 4) + 1);

			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font("Arial", Font.BOLD, 16));
			g2d.drawString("x" + quantity, x + size / 4, startY + size / 2 + 4);

			x += size + gap;
		}
	}

	private GradientPaint getColorStone(Stones stone, int x, int startX, int startY, int size) {
		return switch (stone) {
		case RUBY ->
			new GradientPaint(x, startY, new Color(255, 100, 100), x + size, startY + size, new Color(128, 0, 0));
		case SAPHIR ->
			new GradientPaint(x, startY, new Color(100, 100, 255), x + size, startY + size, new Color(0, 0, 128));
		case EMERALD ->
			new GradientPaint(x, startY, new Color(100, 255, 100), x + size, startY + size, new Color(0, 128, 0));
		case DIAMOND ->
			new GradientPaint(x, startY, new Color(240, 240, 255), x + size, startY + size, new Color(150, 150, 180));
		case ONYX ->
			new GradientPaint(x, startY, new Color(80, 80, 80), x + size, startY + size, new Color(20, 20, 20));
		case GOLDJOKER ->
			new GradientPaint(x, startY, new Color(255, 255, 200), x + size, startY + size, new Color(200, 170, 0));
		};
	}

	public void drawPlayerInfo(Graphics2D g2d, Player player, int startX, int startY) {
		Objects.requireNonNull(g2d);
		Objects.requireNonNull(player);

		int boxWidth = 220;
		int boxHeight = 60;
		int padding = 10;

		g2d.setColor(new Color(240, 240, 240));
		g2d.fillRoundRect(startX, startY, boxWidth, boxHeight, 15, 15);

		g2d.setColor(Color.BLACK);
		g2d.drawRoundRect(startX, startY, boxWidth, boxHeight, 15, 15);

		g2d.setFont(new Font("Arial", Font.BOLD, 16));
		g2d.drawString("Joueur : " + player.getName(), startX + padding, startY + 20);
		g2d.setFont(new Font("Arial", Font.PLAIN, 14));
		g2d.drawString("Prestige : " + player.getPoints(), startX + padding, startY + 40);

		int tokenStartY = startY + boxHeight + 10;
		int tokenSize = 50;
		int tokenGap = 5;

		int cardStartY = tokenStartY + tokenSize + 15;

		g2d.setColor(new Color(240, 240, 240));
		g2d.fillRoundRect(startX, cardStartY, 325, 120, 30, 30);
		g2d.setColor(Color.BLACK);
		g2d.drawRoundRect(startX, cardStartY, 325, 120, 30, 30);

		drawTokensAndBonus(g2d, player, startX, cardStartY, tokenSize, 25, tokenGap);
	}

	public void drawTokensAndBonus(Graphics2D g2d, Player player, int startX, int startY, int tokenSize, int cardSize, int gap) {
		List<Stones> stoneOrder = List.of(Stones.RUBY, Stones.SAPHIR, Stones.EMERALD, Stones.DIAMOND, Stones.ONYX,
				Stones.GOLDJOKER);
		Comparator<Stones> stonesComparator = Comparator.comparingInt(stoneOrder::indexOf);

		var x = startX;
		var y = startY;
		
		//tokens
		var tokensCount = Arrays.stream(Stones.values())
			    .sorted(stonesComparator)
			    .collect(Collectors.toMap(
			        stone -> stone,
			        stone -> player.getWallet().getValue(stone),
			        (a, b) -> a,
			        LinkedHashMap::new
			    ));


		for (var entry : tokensCount.entrySet()) {
			Stones stone = entry.getKey();
			int quantity = entry.getValue();
			drawOneMiniToken(g2d, stone, quantity, x, startX, y, tokenSize);
			x += tokenSize + gap;
		}
		
		x = startX;
		y += tokenSize + 2 * gap;
		//cartes
		var bonusCount = Arrays.stream(Stones.values()).filter(s -> s != Stones.GOLDJOKER)
				.sorted(stonesComparator)
				.collect(Collectors.toMap(stone -> stone,
						stone -> player.getBonus().stream().filter(s -> s == stone).count(), (a, b) -> a,
						LinkedHashMap::new));
		

		bonusCount.entrySet().stream().sorted(Map.Entry.comparingByKey(stonesComparator)).toList();

		for (var entry : bonusCount.entrySet()) {
			Stones stone = entry.getKey();
			int quantity = entry.getValue().intValue();
			drawOneMiniCard(g2d, stone, quantity, x, startX, y, cardSize);
			x += tokenSize +(gap/2);
		}

	}

	private void drawOneMiniToken(Graphics2D g2d, Stones stone, int quantity, int x, int startX, int startY, int size) {
		var color = getColorStone(stone, x, startX, startY, size);

		g2d.setPaint(color);
		g2d.fillOval(x, startY, size, size);

		g2d.setColor(Color.BLACK);
		g2d.drawString("x" + quantity, (x + size / 4) + 1, (startY + size / 2 + 4) + 1);

		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("Arial", Font.BOLD, 16));
		g2d.drawString("x" + quantity, x + size / 4, startY + size / 2 + 4);
	}

	private void drawOneMiniCard(Graphics2D g2d, Stones card, int quantity, int x, int startX, int startY, int size) {
		if (card == Stones.GOLDJOKER) {
			return;
		}

		g2d.setPaint(getColorStone(card, x, startX, startY, size));
		g2d.fillRect(x, startY, size, 2*size);

		g2d.setColor(Color.DARK_GRAY);
		g2d.drawRect(x, startY, size, 2*size);

		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("Arial", Font.BOLD, 14));
		g2d.drawString("x" + quantity, x + 4, startY + size / 2 - 4);
	}

	public int getHeight() {
		return this.screenHeight;
	}

	public int getWidth() {
		return this.screenWidth;
	}
}
