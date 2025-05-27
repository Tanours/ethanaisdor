package splendor.controller;

import java.util.List;
import java.util.Scanner;

import splendor.model.Game;
import splendor.model.Player;

public class Main {
	public static void main(String[] args) {
		
		try(var scanner = new Scanner(System.in)) {
			List<Player> players = Game.initPlayers(scanner);
	        Game game = new Game(players);
	        game.run();
		}
		
		
	}
}
