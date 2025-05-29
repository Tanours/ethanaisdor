package splendor.controller;

import java.util.List;
import java.util.Scanner;

import splendor.model.Game;
import splendor.model.Player;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
        	
			List<Player> players = Game.initPlayers(scanner);
	        Game game = new Game(players);
	        game.run();
    		
        	
            System.out.println("Bienvenue dans Splendor !");
            System.out.println("1. Phase 1 - Jeu simplifié");
            System.out.println("2. Phase 2 - Jeu complet");
            System.out.print("Votre choix : ");

//            int choix = scanner.nextInt();
//            scanner.nextLine();
//
//            var gamePhase = switch (choix) {
//                case 1 -> new Base();
//                case 2 -> new Complet();
//                default -> {
//                    System.out.println("Choix invalide");
//                    yield null;
//                }
//            };
//
//            if (gamePhase != null) {
//                gamePhase.run(scanner);
//            }
        }
    }
}
