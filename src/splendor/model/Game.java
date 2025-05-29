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
	    nbPlayers = Integer.parseInt(scanner.nextLine());

	    /*while (nbPlayers < 2 || nbPlayers > 4) {
	        try {
	            nbPlayers = Integer.parseInt(scanner.nextLine());
	            if (nbPlayers < 2 || nbPlayers > 4) {
	                System.out.println("Veuillez entrer un nombre entre 2 et 4.");
	            }
	        } catch (NumberFormatException e) {
	            System.out.println("Entrée invalide. Veuillez entrer un nombre.");
	        }
	    }*/

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

	private void printTurn(Integer turn,Player currPlayer,List<Player> players) {
		var res = new StringBuilder();
		var startLine = "╔════════════════════════════════╗\n";
		var endLine = "╚════════════════════════════════╝\n";
		res.append(startLine);
		res.append("║ Tour : %-23s ║\n".formatted(turn)).append("║ %-30s ║\n".formatted(""))		;
		
		for(var player : players) {
			var selected_player = "\u001B[45m"+ "JOUEUR : %-15s".formatted(player.getName())+"\t"+Stones.resetColor();
			res.append("║ %-30s ║\n".formatted(player.equals(currPlayer) ? 
						selected_player
					: "JOUEUR : %-15s".formatted(player.getName())
					));
		}
		res.append(endLine);
		System.out.println(res.toString());
	}
	private void playerTurn(Player player) {
	    Objects.requireNonNull(player);
	    int choice = -1;

	    boolean validAction = false;
	    
	    while (!validAction) {
	        System.out.println(player);
	        printGame.printChoice();
	        System.out.println("\nChoisissez une option : ");

	        if (scanner.hasNextInt()) {
	            choice = scanner.nextInt();
	            scanner.nextLine();
	            validAction = action.play(choice, player, board);
	        } else {
	            scanner.nextLine();
	            System.out.println("Veuillez entrer un nombre valide.");
	        }
	    }
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
			System.out.println("Plateau de jeu : \n");
			board.revealCards();
			try {
				Thread.sleep(5000);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (var player : players) {
				printTurn(turn,player,players);
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
