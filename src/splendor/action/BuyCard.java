package splendor.action;

import java.util.Objects;

import splendor.model.Board;
import splendor.model.Player;

public record BuyCard(Board board, Player player) implements Action<Boolean>{
	public Boolean run() {
		Objects.requireNonNull(board);
		Objects.requireNonNull(player);
		
		
		try {
			String input;
			int cardIndex;
			
			do {
				System.out.println("\nChoisissez une carte à acheter. Changer d'option : q " );
				board.revealCards();
				
				input = Action.sc.next().toUpperCase();
				cardIndex = Integer.parseInt(input);
				
				if (input.equals("Q")) {
					return false; 
				}
				
				
			} while(!input.matches("\\d+") || cardIndex > 4);
			
			
			
			
			Action.sc.nextLine();
			var card = board.getCards().get(1).get(cardIndex-1);
			if (!board.selectCard(player, card)) {
				System.out.println("Vous ne pouvez pas acheter cette carte.");
				Thread.sleep(2000);
				return false;
			}
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
		catch (InterruptedException e) {
			return false;
		}
	}
}
