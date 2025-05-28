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
		System.out.println("╔═════════════════════════════════════════════╗");
		System.out.println("║ "+"\u001B[33m"+"1"+"\u001B[0m"+"%-42s ║".formatted(" - Prendre 3 jetons différents"));
		System.out.println("║ "+"\u001B[33m"+"2"+"\u001B[0m"+"%-42s ║".formatted(" - Prendre 2 jetons de même couleur"));
		System.out.println("║ "+"\u001B[33m"+"3"+"\u001B[0m"+"%-42s ║".formatted(" - Acheter une carte"));
		System.out.println("╚═════════════════════════════════════════════╝");
	}
}
