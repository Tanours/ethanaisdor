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

	public void printChoice(GamePhase gamePhase,Player player) {
		switch (gamePhase) {
			case Base b -> {
				System.out.println(new DisplayChoice(
						"Prendre 3 jetons différents",
						"Prendre 2 jetons de même couleur",
						"Acheter une carte"
						));
			}
			case Complet c -> {
				if(player.getReserved().isEmpty()) {
					System.out.println(new DisplayChoice(
							"Prendre 3 jetons différents",
							"Prendre 2 jetons de même couleur",
							"Acheter une carte",
							"Reserver une carte"
							));
				}
				else {
					System.out.println(new DisplayChoice(
							"Prendre 3 jetons différents",
							"Prendre 2 jetons de même couleur",
							"Acheter une carte",
							"Reserver une carte",
							"Afficher les cartes réservés"
							));
				}
				
			}
		}
		
	}
}
