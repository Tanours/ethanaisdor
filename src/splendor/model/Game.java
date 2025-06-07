package splendor.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import splendor.action.BuyCard;
import splendor.action.BuyResCard;
import splendor.action.NobleVisit;
import splendor.action.Action;
import splendor.action.ResCard;
import splendor.action.Take2SameToken;
import splendor.action.Take3DiffToken;
import splendor.controller.GamePhase;
import splendor.view.PrintGame;

public class Game {
	private final Board board;
	private final List<Player> players;
	private final PrintGame printGame;
	private final Scanner scanner = new Scanner(System.in);
	//private final Action action;
	private final GamePhase phase;


	public Game(GamePhase phase,List<Player> players) {
		this.phase = phase;
		board = new Board(phase);
	    this.players = List.copyOf(players);
	    printGame = new PrintGame(board, players);
	    //action = new Action(); 
	    
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
	
	private Action<?> getActionFromChoice(int choice, Player player) {
	    return switch (choice) {
	        case 1 -> new Take3DiffToken(board, player);
	        case 2 -> new Take2SameToken(board, player);
	        case 3 -> new BuyCard(board, player);
	        case 4 -> new ResCard(board,player);
	        default -> throw new IllegalArgumentException("Choix invalide : " + choice);
	    };
	}

	
	private void playerTurn(Player player) {
	    Objects.requireNonNull(player);
	    int choice = -1;
	    boolean actionDone = false;

	    while (!actionDone) {
	        System.out.println(player);
	        printGame.printChoice(phase);
	        System.out.println("\nChoisissez une option : ");

	        if (scanner.hasNextInt()) {
	            choice = scanner.nextInt();
	            scanner.nextLine();

	            if (choice < 1 || choice > phase.getMaxChoice()) {
	                System.out.println("Veuillez entrer un nombre valide.");
	                continue;
	            }

	            var action = getActionFromChoice(choice, player);
	            var result = action.run();
	            if(result.equals(true)) {
	            	actionDone = true;
	            }
	        } else {
	            scanner.nextLine();
	            System.out.println("Veuillez entrer un nombre valide.");
	        }
	    }
	    new NobleVisit(board, player).run();
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
				Thread.sleep(2000);
				
			} catch (InterruptedException e) {
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
				System.out.println(board.getTokens());
			}
			
			turn++;
		}
	}
}
