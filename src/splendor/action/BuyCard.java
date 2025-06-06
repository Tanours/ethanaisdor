package splendor.action;

import java.util.Objects;

import splendor.model.Board;
import splendor.model.Player;
import splendor.view.DisplayChoice;
import splendor.view.DisplayPrompt;

public record BuyCard(Board board, Player player) implements Action<Boolean>{
	public BuyCard {
		Objects.requireNonNull(board);
		Objects.requireNonNull(player);
	}
	public Boolean run() {
		if(player.getReserved().isEmpty()) {
			return new BuyBoardCard(board, player).run();
		}
		System.out.println(new DisplayChoice(false,
				"Acheter une carte réservée",
				"Acheter une carte du plateau"));
		while (true) {
			try {
				System.out.println(new DisplayPrompt("Choisissez une option (ou 'q' pour quitter) :"));
				var input = sc.next().trim();
				if(input.equals("q")) return false;
				if(input.matches("\\d+")) {
					var entry = Integer.parseInt(input);
					if(entry != 1 && entry != 2) {
						System.err.println("Je ne crois pas que ça soit une option. Essaie encore");
						continue;
					}
					return switch (entry) {
					case 1 -> new BuyResCard(player).run();
					default -> new BuyBoardCard(board, player).run();
					};
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			
			
		}
	}
}
