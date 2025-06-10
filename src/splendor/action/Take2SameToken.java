package splendor.action;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import splendor.model.Board;
import splendor.model.Player;
import splendor.model.Stones;
import splendor.view.DisplayChoice;
import splendor.view.DisplayPrompt;

public record Take2SameToken(Board board, Player player) implements Action<Boolean> {
	public Take2SameToken {
		Objects.requireNonNull(board);
		Objects.requireNonNull(player);
	}
	public Boolean run() {
		var options =Arrays.stream(Stones.values())
				.limit(5)
				.map(s -> "%s●%s %s".formatted(s.getColor(),Stones.resetColor(),s.name()))
				.toList();
		System.out.println(new DisplayChoice(true,false,options));
		System.out.println(new DisplayPrompt("Choisissez une couleur pour prendre 2 jetons (ou 'q' pour quitter)."));

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
				System.err.println("Pas assez de jetons disponibles pour cette couleur.");
			}

		} catch (IllegalArgumentException e) {
			System.err.println("Couleur invalide.");
		}
		return false;
	}
}
