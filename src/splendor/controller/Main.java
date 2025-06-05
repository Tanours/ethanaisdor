package splendor.controller;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

import splendor.action.GetPhase;
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
	        
	        
    		
        	var gamePhase = new GetPhase().run();
           
            var game = new Game(gamePhase,players);
            game.run();

          
        }
    }
}
