package splendor.view;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.github.forax.zen.Application;
import com.github.forax.zen.ApplicationContext;
import com.github.forax.zen.Event;
import com.github.forax.zen.PointerEvent;

import splendor.model.Board;
import splendor.model.Card;
import splendor.model.Player;
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
		for (var level : board.getCards().entrySet()) {
			var cards = level.getValue().stream().limit(4).toList();
			var viewCardHeight = (int) (screenHeight * 0.23);
			var viewCardWidth = (int) (screenWidth * 0.1);
			var x = (int) screenWidth / 2;
			var y = (int) (level.getKey() * 40 + (level.getKey() - 1) * viewCardHeight);
			System.out.println(cards);
			this.drawCards(cards, x, y, viewCardWidth, viewCardHeight, 20);
		}
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
	    if (event == null) return null;

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


	public void drawCards(List<Card> cards, int x, int y, int cardWidth, int cardHeight, int gap) {
		var realCaerds = cards.stream().limit(4).toList();
		ctx.renderFrame(g2d -> {
			for (var i = 0; i < realCaerds.size(); i++) {
				final int index = i;
				if (realCaerds.get(index).image() != null) {
					var card = realCaerds.get(index);
					var image = realCaerds.get(index).image();
					int cardX = x + index * (cardWidth + gap);
					int cardY = y;
					card.graphicInfo().setX(cardX);
					card.graphicInfo().setY(cardY);
					card.graphicInfo().setHeight(cardHeight);
					card.graphicInfo().setWidth(cardWidth);
					g2d.drawImage(image, cardX, cardY, cardWidth, cardHeight, null);
				}
			}
		});
	}

	public int getHeight() {
		return this.screenHeight;
	}

	public int getWidth() {
		return this.screenWidth;
	}
}
