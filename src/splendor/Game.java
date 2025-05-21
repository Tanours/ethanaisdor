package splendor;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class Game {
	private final Board board = new Board();
	private final List<Player> players;
	private final PrintGame printGame ;
	private final Action action = new Action();
	
	public Game(List<Player> players) {
		this.players = List.copyOf(players);
		this.printGame = new PrintGame(board,players);
	}

	public Board getBoard() {
		return board;
	}

	public List<Player> getPlayers() {
		return players;
	}
	private void playerTurn(Player player) {
	    Objects.requireNonNull(player);
	    int choice = -1;
	    try(var sc = new Scanner(System.in)){
	        System.out.println("Tour de " + player.getName());
	        System.out.println("Points : " + player.getPoints());
	        

	        printGame.printChoice();
	        System.out.println("\nChoisissez une option : ");

	        
	        choice = sc.nextInt();
	       
	        System.out.println("ici");
	        
	    }
	    action.play(choice, player, board);
	}
	
	public void run() {
		
		var turn = 1;
		var gameOver = false;
		while(!gameOver) {
			System.out.println("=".repeat(18)+"TOUR : "+turn+"=".repeat(18));
			board.revealCards();
			for(var player : players) {
				playerTurn(player);
			}
			turn++;
		}
		
	}
}
