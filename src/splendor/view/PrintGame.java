package splendor.view;

import java.util.List;
import java.util.Objects;

import splendor.model.Board;
import splendor.model.Player;

public record PrintGame(Board board, List<Player> players) {
	public PrintGame {
		Objects.requireNonNull(board);
		Objects.requireNonNull(players);
	}

	public void printChoice() {
		System.out.println("1 - Prendre 3 jetons de couleurs différentes");
		System.out.println("2 - Prendre 2 jetons de la même couleur");
		System.out.println("3 - Acheter une carte");
	}
}
