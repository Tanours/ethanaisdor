package splendor;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Game {
	private final Board board;
	private final List<Player> players;
	
	public Game(Board board, List<Player> players) {
		this.board = board;
		this.players = List.copyOf(players);
	}

	public Board getBoard() {
		return board;
	}

	public List<Player> getPlayers() {
		return players;
	}
}
