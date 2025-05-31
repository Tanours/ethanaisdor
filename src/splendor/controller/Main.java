package splendor.controller;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

import splendor.model.Card;
import splendor.model.Game;
import splendor.model.Parser;
import splendor.model.Player;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
        	
//        	var cards = Parser.getDeveloppementCard(Path.of("card.csv"), StandardCharsets.UTF_8);
//        	
//        	for(var level : cards.entrySet()) {
//        		System.out.println("level " + level.getKey());
//        		System.out.println(Card.displayCards(level.getValue()));
//        	}
//        	
//        	var nobles = Parser.getNobles(Path.of("nobles.csv"), StandardCharsets.UTF_8);
//        	for(var n: nobles) {
//        		System.out.println(n);
//        	}
        	
			List<Player> players = Game.initPlayers(scanner);
	        Game game = new Game(players);
	        
    		
        	
            System.out.println("Bienvenue dans Splendor !");
            System.out.println("1. Phase 1 - Jeu simplifié");
            System.out.println("2. Phase 2 - Jeu complet");
            System.out.print("Votre choix : ");
            game.run();

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
