package splendor.action;

import java.util.Objects;

import splendor.model.Board;
import splendor.model.Player;
import splendor.view.DisplayPrompt;

public record BuyBoardCard(Board board,Player player) implements Action<Boolean> {
	public BuyBoardCard {
		Objects.requireNonNull(board);
		Objects.requireNonNull(player);
		
	}
	public Boolean run() {
		try {
			String input;
			int cardIndex;
			
			do {
				board.revealCards();
				System.out.println(new DisplayPrompt("Choisissez une carte à acheter (ou 'q' pour quitter) :"));
				
				
				input = Action.sc.next().toUpperCase();
				cardIndex = Integer.parseInt(input);
				
				if (input.equals("Q")) {
					return false; 
				}
				
				
			} while(!input.matches("\\d+") || cardIndex > 4);
			
			
			
			
			Action.sc.nextLine();
			var card = board.getCards().get(1).get(cardIndex-1);
			if (!board.selectCard(player, card)) {
				return false;
			}
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
	

}
