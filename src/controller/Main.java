package splendor;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


public class Main {
    public static void main(String[] args) {
    	

        try (Scanner scanner = new Scanner(System.in)) {
            List<Player> players = PrintGame.initPlayers(scanner);
            
            var game = new Game(players);
            
            game.run();
            
           
            
        }
    }
}

