package splendor.controller;

import java.util.List;
import java.util.Scanner;

import splendor.model.Game;
import splendor.model.Player;
import splendor.view.PrintGame;


public class Main {
    public static void main(String[] args) {
    	

        try (Scanner scanner = new Scanner(System.in)) {
            List<Player> players = PrintGame.initPlayers(scanner);
            
            var game = new Game(players);
            
            game.run();
            
           
            
        }
    }
}

