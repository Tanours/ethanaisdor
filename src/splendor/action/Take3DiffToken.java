package splendor.action;

import java.util.HashSet;
import java.util.Objects;

import splendor.model.Board;
import splendor.model.Player;
import splendor.model.Stones;

public record Take3DiffToken(Board board, Player player) implements Action<Boolean> {
	
	public Take3DiffToken {
		Objects.requireNonNull(board);
		Objects.requireNonNull(player);
	}
	
	@Override
	public Boolean run() {
		var pickedColors = new HashSet<Stones>();
		
		while (pickedColors.size() < 3) {
			System.out.println("Choisissez une couleur différente : (" + (pickedColors.size() + 1) + "/3). Changer d'option: q");

			try {
				var input = Action.sc.next().toUpperCase();

				if (input.equals("Q")) {
					return false;
				}

				Stones stone = Stones.valueOf(input);

				if (pickedColors.contains(stone)) {
					System.out.println("Vous avez déjà choisi cette couleur.");
					continue;
				}

				int available = board.getTokens().getOrDefault(stone, 0);
				if (available < 1) {
					System.out.println("Pas assez de jetons disponibles pour cette couleur.");
					continue;
				}

				pickedColors.add(stone);

			} catch (IllegalArgumentException e) {
				System.out.println("Couleur invalide.");
			}
		}

		for (var stone : pickedColors) {
	    	board.takeToken(player, stone);
		}


		return true;
	}
}
