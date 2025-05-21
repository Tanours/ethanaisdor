package splendor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class Action {
	
	public boolean one(Board board, Player player) {
		
		Set<Stones> pickedColors = new HashSet<>();
		try (var sc = new Scanner(System.in)) {
			while (pickedColors.size() < 3) {
				System.out.println("Choissisez une couleur différente :(" + pickedColors.size() + "/3)");
				Stones stone = Stones.DIAMOND;
				stone = Stones.valueOf(sc.next());
				

				if (pickedColors.contains(stone))
					continue;

				if (!board.selectTokens(player, stone, 1))
					continue;
				
				pickedColors.add(stone);
			}
		}
		return true;
	}

	private boolean two(Board board, Player player) {

		try (var sc = new Scanner(System.in)) {
			Stones stone;
			try {
				stone = Stones.valueOf(sc.next().toUpperCase());
				return board.selectTokens(player, stone, 2);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	private boolean three(Board board, Player player) {
		
		try (var sc = new Scanner(System.in)) {
			System.out.println("\nChoissisez une carte : ");
			board.revealCards();
			int cardIndex = sc.nextInt() - 1;
			System.out.println(cardIndex);
			sc.nextLine();
			
			var card = board.getCards().get(1).get(cardIndex);
			
			if(!board.selectCard(player, card)) {
	        	return false;
	        }
			
			
		}
		
	
       
       
        
//        var i = players.indexOf(player);
//        
//        if(!board.selectCard(player, card)) {
//        	System.out.println("Vous ne pouvez pas acheter cette carte.");
//        }
//        
//        
//        var test = player.buyCard(card);
//        players.set(i, test);
//        System.out.println(players);
//   
//        System.out.println("cartes du jeu = " + board.getCards().get(1).size());
		return false;
	}

	

	public void play(int choice, Player player, Board board) {
		Objects.requireNonNull(player);
		Objects.requireNonNull(board);

		var valid = false;
		while (!valid) {
			valid = switch (choice) {
			case 1 -> this.one(board, player);
			case 2 -> this.two(board, player);
			case 3 -> this.three(board, player);
			default -> false;
			};
		}

	}

	

}
