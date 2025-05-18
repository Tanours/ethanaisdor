package splendor;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;



import java.io.IOException;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        var gameOver = false;
        int turn = 1;
        var board = new Board();
		try {
			board.CardReferenceReader(Path.of("cardReference.html"));
		} catch (IOException e) {
			e.printStackTrace();
		}

        try (Scanner scanner = new Scanner(System.in)) {
            List<Player> players = PrintGame.initPlayers(scanner);
            var printGame = new PrintGame(board, players);

            while (!gameOver) {
                System.out.println("\n=== Tour " + turn + " ===");

                for (Player player : players) {
                    int action = printGame.printChoice(player, scanner);
                    scanner.nextLine();

                    switch (action) {
                    case 1 -> {
                        System.out.println(player.getName() + " choisit de prendre 3 jetons de couleurs différentes.");
                        System.out.println("Jetons disponibles : " + board.getTokens());

                        Set<Stones> pickedColors = new HashSet<>();
                        while (pickedColors.size() < 3) {
                            System.out.print("Choisir une couleur différente (" + (pickedColors.size() + 1) + "/3) : \n" + board.getTokens());
                            String input = scanner.nextLine().toUpperCase();

                            Stones stone;
                            try {
                                stone = Stones.valueOf(input);
                            } catch (IllegalArgumentException e) {
                                System.out.println("Couleur invalide.");
                                continue;
                            }

                            if (pickedColors.contains(stone)) {
                                System.out.println("Cette couleur a déjà été choisie.");
                                continue;
                            }

                            if (!board.selectTokens(player, stone, 1)) {
                                System.out.println("Jeton indisponible pour cette couleur.");
                                continue;
                            }
                            pickedColors.add(stone);
                        }

                        System.out.println(player);
                    }


                    case 2 -> {
                        boolean jetonsPris = false;
                        while (!jetonsPris) {
                            System.out.print("Choisir une couleur pour prendre 2 jetons : \n" + board.getTokens());
                            String input = scanner.nextLine().toUpperCase();

                            Stones stone;
                            try {
                                stone = Stones.valueOf(input);
                            } catch (IllegalArgumentException e) {
                                System.out.println("Couleur invalide.");
                                continue;
                            }

                            if (board.selectTokens(player, stone, 2)) {
                            	System.out.println(player);
                                jetonsPris = true;
                            } else {
                                System.out.println("Aucun jeton disponible pour cette couleur.");
                            }

                        } 
                    }
                    case 3 -> {
                        System.out.println("Voici les 4 cartes disponibles :");
                        board.revealCards();
                        System.out.print("Quelle carte acheter (1-4) ? ");
                        int cardIndex = scanner.nextInt() - 1;
                        scanner.nextLine();
                        
                        var card = board.getCards().get(1).get(cardIndex);
                        var i = players.indexOf(player);
                        Player updatedPlayer = board.selectCard(player, card);
                        System.out.println("updated player = " + updatedPlayer);
                        
                        players.set(i, updatedPlayer);
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
            }
            turn++;
        }
    }
}

