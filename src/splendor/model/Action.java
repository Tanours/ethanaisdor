package splendor.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

import splendor.controller.Base;
import splendor.controller.Complet;
import splendor.controller.GamePhase;


public class Action {

	private final Scanner scanner;
	private final GamePhase gamePhase;

	public Action(Scanner scanner,GamePhase gamePhase) {
		Objects.requireNonNull(scanner);
		Objects.requireNonNull(gamePhase);
		this.scanner = scanner;
		this.gamePhase = gamePhase;

	}
	private int findLevelByNumber(int number) {
		return number > 12 ? 3 : number > 8 ? 2 : 1;
	}
	private int findIndexCardByNumber(int number) {
		return switch (findLevelByNumber(number)) {
			case 1 -> number - 1;
			case 2 -> number - 5;
			case 3 -> number - 9;
			default -> -1;
		};
	}
	private Card selectCardByNumber(int number,Board board) {
		return switch (gamePhase) {
		case Base b ->  board.getCards().get(1).get(number);
		case Complet c -> board.getCards().get(findLevelByNumber(number)).get(findIndexCardByNumber(number));
		};
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
		board.revealCards();
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
	private boolean four(Board board,Player player) {
		Objects.requireNonNull(board);
		Objects.requireNonNull(player);
		try {
			board.revealCards();
			var valid = false;
			while (!valid) {
				System.out.println("Choisissez la carte à réserver (ou 'q' pour quitter) : ");
				var input = scanner.next();
				if("q".equals(input)) {
					return false;
				}
				if(input.trim().matches("\\d+")) {
					var cardChoice = Integer.parseInt(input);
					if(cardChoice >= 1 && cardChoice < gamePhase.getMaxCard()) {
						var card = selectCardByNumber(cardChoice, board);
						if(!board.reserveCard(player,card)) {
							System.out.println("Vous ne pouvez plus reservez de carte sans en avoir acheter une des vôtre au préalable");
							try {
								Thread.sleep(2000);
								
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						else valid = true;
						
					}
				}
			
				
			}
			return true;
		}
		catch(IllegalArgumentException e){
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
                case 4 -> four(board,player);
                default -> {
                    System.out.println("Choix invalide.");
                     yield false;
                }
            };
    }

}
