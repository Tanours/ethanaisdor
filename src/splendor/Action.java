package splendor;

import java.util.HashSet;
import java.util.Set;

public class Action {
	public boolean choiceOne() {
		Set<Stones> pickedColors = new HashSet<>();
        while (pickedColors.size() < 3) {
            

            Stones stone;
            try {
                stone = Stones.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Couleur invalide.");
                continue;
            }

            if (pickedColors.contains(stone)) {
                System.out.println("Cette couleur a déjà été choisie.");
                continue;
            }

            if (!board.selectTokens(player, stone, 1)) {
                System.out.println("Jeton indisponible pour cette couleur.");
                continue;
            }
            pickedColors.add(stone);
        }

        System.out.println(player);
	}
	
	public boolean choiceTwo() {
		boolean jetonsPris = false;
        while (!jetonsPris) {

            String input = scanner.nextLine().toUpperCase();

            Stones stone;
            try {
                stone = Stones.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Couleur invalide.");
                continue;
            }

            if (board.selectTokens(player, stone, 2)) {
            	System.out.println(player);
                jetonsPris = true;
            } else {
                System.out.println("Aucun jeton disponible pour cette couleur.");
            }

        } 
	}

}
