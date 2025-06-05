package splendor.action;

import java.io.IOException;
import java.util.NoSuchElementException;

import splendor.controller.Base;
import splendor.controller.Complet;
import splendor.model.Board;
import splendor.model.Card;
import splendor.model.Player;
import splendor.model.Stones;
import splendor.view.DisplayCards;
import splendor.view.DisplayPrompt;


public record ResCard(Board board,Player player) implements Action<Boolean>{
	private int findLevelByNumber(int number,Board board) {
		if( number > 4 && board.getCards().size() == 1) return -1;
		return (number > 8 && number <= 12) ? 3 : (number > 4 && number <= 8) ? 2 : (number > 0 && number <= 4) ? 1 : -1;
	}
	private int findIndexCardByNumber(int number,Board board) {
		return switch (findLevelByNumber(number,board)) {
			case 1 -> number - 1;
			case 2 -> number - 5;
			case 3 -> number - 9;
			default -> -1;
		};
	}
	private Card selectCardByNumber(int number,Board board) {
		int level = findLevelByNumber(number,board);
		if(level < 0) return null;
		int index = findIndexCardByNumber(number,board);
		return switch (board.getCards().keySet().size()) {
			case 1 -> board.getCards().get(1).get(index);
			default -> board.getCards().get(level).get(index);	
		};
	}
	
	public Boolean run() {
		System.out.println(new DisplayCards(board.getCards()));
		if(player.getReserved().size() >= 3) {
			System.err.println("Trop de réservation, tue la réservervation. Essaie autre chose");
			return null;
		}
		try {
			while (true) {
				System.out.println(new DisplayPrompt("Choisissez une carte (ou 'q' pour quitter) :"));
				var input = sc.next();
				input = input.trim();
				if("q".equals(input)) {
					return null;
				}
				if(input.matches("\\d+")) {
					var number = Integer.parseInt(input);
					var card = selectCardByNumber(number, board);
					if(card == null) {
						System.err.println("L'index ne correspond pas à une des cartes mon ami ");
						continue;
					}
					if(board.reserveCard(player, card)) {
						player.addToken(Stones.GOLDJOKER, 1);
						return true;
					};
					
				}
			}
		}catch(NoSuchElementException e){
			e.printStackTrace();
			return null;
		}
	}
}
