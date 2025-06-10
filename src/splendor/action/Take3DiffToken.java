package splendor.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

import splendor.model.Board;
import splendor.model.Player;
import splendor.model.Stones;
import splendor.view.DisplayChoice;
import splendor.view.DisplayPrompt;

public record Take3DiffToken(Board board, Player player) implements Action<Boolean> {
	
	public Take3DiffToken {
		Objects.requireNonNull(board);
		Objects.requireNonNull(player);
	}
	
	@Override
	public Boolean run() {
		var pickedColors = new HashSet<Stones>();
		var options = new ArrayList<String>(Arrays.stream(Stones.values())
				.limit(5)
				.map(s -> "%s●%s %s".formatted(s.getColor(),Stones.resetColor(),s.name()))
				.toList());
		
		while (pickedColors.size() < 3) {
			System.out.println(new DisplayChoice(true,false,options));
			System.out.println(new DisplayPrompt("Choisissez une couleur différente (ou 'q' pour quitter) :\t (" + (pickedColors.size() + 1) + "/3). "));

			try {
				var input = Action.sc.next().toUpperCase();

				if (input.equals("Q")) {
					return false;
				}

				Stones stone = Stones.valueOf(input);
				
				if (pickedColors.contains(stone)) {
					System.err.println("Vous avez déjà choisi cette couleur.");
					continue;
				}
				
				int available = board.getTokens().getOrDefault(stone, 0);
				if (available < 1) {
					System.err.println("Pas assez de jetons disponibles pour cette couleur.");
					continue;
				}

				pickedColors.add(stone);
				options.remove("%s●%s %s".formatted(stone.getColor(),Stones.resetColor(),stone.name()));

			} catch (IllegalArgumentException e) {
				System.err.println("Couleur invalide.");
			}
		}

		for (var stone : pickedColors) {
	    	board.takeToken(player, stone);
		}


		return true;
	}
}
