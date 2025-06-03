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

		            if (pickedColors.contains(stone))
		                continue;

		            if (!board.selectTokens(player, stone, 1)) {
		                System.out.println("Pas assez de jetons disponibles pour cette couleur.");
		                continue;
		            }

		            pickedColors.add(stone);

		        } catch (IllegalArgumentException e) {
		            System.out.println("Couleur invalide.");
		        }
		    }


		 for (var s : pickedColors) {
	            var takeAction = new TakeOneToken(board, player, s);
	            takeAction.run();
	        }
		    return true;
	}
}
