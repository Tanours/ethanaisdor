package splendor.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

import splendor.controller.Complet;


public class Action {

	private final Scanner scanner;

	public Action(Scanner scanner) {
		this.scanner = scanner;

	}

	public boolean one(Board board, Player player) {
	    Set<Stones> pickedColors = new HashSet<>();
	    while (pickedColors.size() < 3) {
	        System.out.println("Choisissez une couleur différente : (" + (pickedColors.size() + 1) + "/3). Changer d'option: q");
	        try {
	            var input = scanner.next().toUpperCase();

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
	        board.takeOneToken(player, s);
	    }
	    return true;
	}


	private boolean two(Board board, Player player) {
		System.out.println("Choisissez une couleur pour prendre 2 jetons. Changer d'option: q");

		try {
			var input = scanner.next().toUpperCase();

			if (input.equals("Q")) {
				return false; 
			}

			Stones stone = Stones.valueOf(input);

			if (board.selectTokens(player, stone, 2)) {
				board.takeMultipleSameTokens(player, stone, 2);
				return true; 
			} else {
				System.out.println("Pas assez de jetons disponibles pour cette couleur.");
			}

		} catch (IllegalArgumentException e) {
			System.out.println("Couleur invalide.");
		}
		return false;

	}

	private boolean three(Board board, Player player) {
		Objects.requireNonNull(board);
		Objects.requireNonNull(player);
		
		System.out.println("\nChoisissez une carte à acheter. Changer d'option : q " );
		board.revealCards(new Complet());
		try {
			var input = scanner.next().toUpperCase();

			if (input.equals("Q")) {
				return false; 
			}
			int cardIndex = Integer.parseInt(input);
			scanner.nextLine();
			var card = board.getCards().get(1).get(cardIndex);
			if (!board.selectCard(player, card)) {
				System.out.println("Vous ne pouvez pas acheter cette carte.");
				Thread.sleep(2000);
				return false;
			}
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
		catch (InterruptedException e) {
			return false;
		}
	}


    public boolean play(int choice, Player player, Board board) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(board);
        
            return switch (choice) {
                case 1 -> one(board, player);
                case 2 -> two(board, player);
                case 3 -> three(board, player);
                default -> {
                    System.out.println("Choix invalide.");
                     yield false;
                }
            };
    }
}
