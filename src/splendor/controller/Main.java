package splendor.controller;

import java.util.List;
import java.util.Scanner;

import splendor.model.Game;
import splendor.model.GameMode;
import splendor.model.Player;

public class Main {
	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			
			var running = true;
			while (running) {
                System.out.println("\n=== SPLENDOR ===");
                System.out.println("1. Lancer Phase 1 (jeu simplifié)");
                System.out.println("2. Lancer Phase 2 (jeu complet)");
                System.out.println("0. Quitter");
                System.out.print("Votre choix : ");
                
                
                var input = scanner.nextInt();
                scanner.nextLine();

	            switch (input) {
				case 1 -> Main.startGame(scanner, GameMode.BASE);
				case 2 -> Main.startGame(scanner, GameMode.FULL);
				case 0 -> running = false;
				}
			}
		}
	}
	
	private static void startGame(Scanner scanner, GameMode mode) {
        List<Player> players = Game.initPlayers(scanner);
        Game game = new Game(players, mode);
        game.run();
    }
}
