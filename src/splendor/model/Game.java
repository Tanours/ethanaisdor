package splendor.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import splendor.view.PrintGame;

public class Game {
	private final Board board = new Board();
	private final List<Player> players;
	private final PrintGame printGame;
	private final Scanner scanner = new Scanner(System.in);
	private final Action action;


	public Game(List<Player> players) {
	    this.players = List.copyOf(players);
	    this.printGame = new PrintGame(board, players);
	    this.action = new Action(scanner); 
	}
	
	public static List<Player> initPlayers(Scanner scanner) {
	    var players = new ArrayList<Player>();

	    System.out.print("Combien de joueurs ? ");
	    int nbPlayers = 0;

	    while (nbPlayers < 2 || nbPlayers > 4) {
	        try {
	            nbPlayers = Integer.parseInt(scanner.nextLine());
	            if (nbPlayers < 2 || nbPlayers > 4) {
	                System.out.println("Veuillez entrer un nombre entre 2 et 4.");
	            }
	        } catch (NumberFormatException e) {
	            System.out.println("Entrée invalide. Veuillez entrer un nombre.");
	        }
	    }

	    for (int i = 0; i < nbPlayers; i++) {
	        System.out.print("Nom du joueur " + (i + 1) + " : ");
	        String name = scanner.nextLine();

	        int age = -1;
	        while (age <= 0) {
	            System.out.print("Âge de " + name + " : ");
	            try {
	                age = Integer.parseInt(scanner.nextLine());
	                if (age <= 0) {
	                    System.out.println("L'âge doit être supérieur à 0.");
	                }
	            } catch (NumberFormatException e) {
	                System.out.println("Entrée invalide. Veuillez entrer un nombre.");
	            }
	        }

	        players.add(new Player(name, age));
	        System.out.println();
	    }

	    players.sort(Comparator.comparingInt(Player::getAge));
	    return players;
	}


	private void playerTurn(Player player) {
		Objects.requireNonNull(player);
		int choice = -1;

		System.out.println(player);

		printGame.printChoice();
		System.out.println("\nChoisissez une option : ");

		if (scanner.hasNextInt()) {
			choice = scanner.nextInt();
			scanner.nextLine();
		}

		while (!action.play(choice, player, board));
	}

	private boolean victory(Player player) {
		return player.getPoints() >= 15;
	}

	public void run() {
		var turn = 1;
		var gameOver = false;
		while (!gameOver) {
			for (int i = 0; i < 50; i++) { 
	            System.out.println("\n");
	        }
			System.out.println(Stones.DIAMOND.getColor()+"═".repeat(147));
			System.out.println("%67s".formatted(" TOUR : " + turn) );
			System.out.println("═".repeat(147)+Stones.resetColor());
			board.revealCards();
			for (var player : players) {
				playerTurn(player);
				if (victory(player)) {
					System.out.println(
							"Le joueur " + player.getName() + " a gagné avec " + player.getPoints() + " points !");
					gameOver = true;
				}
			}
			
			turn++;
		}
	}
}
