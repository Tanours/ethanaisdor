package splendor;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

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

	private void playerTurn(Player player) {
		Objects.requireNonNull(player);
		int choice = -1;

		System.out.println("Tour de " + player.getName());
		System.out.println("Points : " + player.getPoints());

		printGame.printChoice();
		System.out.println("\nChoisissez une option : ");

		if (scanner.hasNextInt()) {
			choice = scanner.nextInt();
			scanner.nextLine();
		}

		action.play(choice, player, board);
	}

	private boolean victory(Player player) {
		return player.getPoints() >= 15;
	}

	public void run() {
		var turn = 1;
		var gameOver = false;
		while (!gameOver) {
			System.out.println("=".repeat(18) + "TOUR : " + turn + "=".repeat(18));
			board.revealCards();
			for (var player : players) {
				playerTurn(player);
				if (victory(player)) {
					System.out.println(
							"Le joueur " + player.getName() + " a gagné avec " + player.getPoints() + " points !");
					gameOver = true;
				}
				System.out.println(player);
			}
			
			turn++;
		}
	}
}
