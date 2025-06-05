package splendor.action;

import splendor.model.Board;
import splendor.model.Player;
import splendor.model.Stones;

public record Take2SameToken(Board board, Player player) implements Action<Boolean> {
	
	public Boolean run() {
		System.out.println("Choisissez une couleur pour prendre 2 jetons. Changer d'option: q");

		try {
			var input = Action.sc.next().toUpperCase();

			if (input.equals("Q")) {
				return false; 
			}

			Stones stone = Stones.valueOf(input);

			if (board.selectTokens(player, stone, 2)) {
				board.takeMultipleSameTokens(player, stone, 2);
				return true; 
			} else {
				System.out.println("Pas assez de jetons disponibles pour cette couleur.");
			}

		} catch (IllegalArgumentException e) {
			System.out.println("Couleur invalide.");
		}
		return false;
	}
}
