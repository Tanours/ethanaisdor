package splendor.view;

import java.util.List;
import java.util.Objects;

import splendor.controller.Base;
import splendor.controller.Complet;
import splendor.controller.GamePhase;
import splendor.model.Board;
import splendor.model.Player;

public record PrintGame(Board board, List<Player> players) {
	public PrintGame {
		Objects.requireNonNull(board);
		Objects.requireNonNull(players);
	}

	public void printChoice(GamePhase gamePhase) {
		switch (gamePhase) {
			case Base b -> {
				System.out.println("╔═════════════════════════════════════════════╗");
				System.out.println("║ "+"\u001B[33m"+"1"+"\u001B[0m"+"%-42s ║".formatted(" - Prendre 3 jetons différents"));
				System.out.println("║ "+"\u001B[33m"+"2"+"\u001B[0m"+"%-42s ║".formatted(" - Prendre 2 jetons de même couleur"));
				System.out.println("║ "+"\u001B[33m"+"3"+"\u001B[0m"+"%-42s ║".formatted(" - Acheter une carte"));
				System.out.println("╚═════════════════════════════════════════════╝");
			}
			case Complet c -> {
				System.out.println("╔═════════════════════════════════════════════╗");
				System.out.println("║ "+"\u001B[33m"+"1"+"\u001B[0m"+"%-42s ║".formatted(" - Prendre 3 jetons différents"));
				System.out.println("║ "+"\u001B[33m"+"2"+"\u001B[0m"+"%-42s ║".formatted(" - Prendre 2 jetons de même couleur"));
				System.out.println("║ "+"\u001B[33m"+"3"+"\u001B[0m"+"%-42s ║".formatted(" - Acheter une carte"));
				System.out.println("║ "+"\u001B[33m"+"4"+"\u001B[0m"+"%-42s ║".formatted(" - Reserver une carte"));
				System.out.println("╚═════════════════════════════════════════════╝");
			}
		}
		
	}
}
