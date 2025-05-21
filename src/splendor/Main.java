package splendor;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


public class Main {
    public static void main(String[] args) {
        var gameOver = false;
        int turn = 1;
        var board = new Board();

        try (Scanner scanner = new Scanner(System.in)) {
            List<Player> players = PrintGame.initPlayers(scanner);
            var printGame = new PrintGame(board, players);

            while (!gameOver) {
                System.out.println("\n=== Tour " + turn + " ===");

                for (Player player : players) {
                    var action = printGame.printChoice(player, scanner);
                    scanner.nextLine();

                    switch (action) {
                    case 1 -> {
                    	System.out.print("Choisir une couleur différente ("  + "/3) : \n" + board.getTokens());
                        String input = scanner.nextLine().toUpperCase();
                    }


                    case 2 -> {
                        System.out.print("Choisir une couleur pour prendre 2 jetons : \n" + board.getTokens());
                        
                    }
                    case 3 -> {
                        System.out.println("Voici les 4 cartes disponibles :");
                        board.revealCards();
                        System.out.print("Quelle carte acheter (1-4) ? ");
                        int cardIndex = scanner.nextInt() - 1;
                        scanner.nextLine();
                        
                        var card = board.getCards().get(1).get(cardIndex);
                        var i = players.indexOf(player);
                        
                        if(!board.selectCard(player, card)) {
                        	System.out.println("Vous ne pouvez pas acheter cette carte.");
                        }
                        
                        
                        var test = player.buyCard(card);
                        players.set(i, test);
                        System.out.println(players);
                   
                        System.out.println("cartes du jeu = " + board.getCards().get(1).size());
                        
                    }
                    default -> System.out.println("Choix invalide.");
                }

                    if (player.getPoints() >= 15) {
                        System.out.println(player.getName() + " a gagné avec " + player.getPoints() + " points !");
                        gameOver = true;
                        break;
                    }
                } 
                turn++;
            }
            
        }
    }
}

