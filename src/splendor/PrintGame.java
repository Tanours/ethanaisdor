package splendor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public record PrintGame(Board board, List<Player> players) {
	public PrintGame {
		Objects.requireNonNull(board);
		Objects.requireNonNull(players);
	}
	
	public static List<Player> initPlayers(Scanner scanner) {
        List<Player> players = new ArrayList<>();

        System.out.print("Combien de joueurs ? ");
        int nbPlayers = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < nbPlayers; i++) {
            System.out.print("Nom du joueur " + (i + 1) + " : ");
            String name = scanner.nextLine();

            System.out.print("Âge de " + name + " : ");
            int age = scanner.nextInt();
            scanner.nextLine();

            players.add(new Player(name, age));
            System.out.println();
        }

        players.sort(Comparator.comparingInt(Player::getAge));
        return players;
    }
	
	public int printChoice(Player player, Scanner scanner) {
        System.out.println("Tour de " + player.getName());
        System.out.println("Points : " + player.getPoints());

        System.out.println("Actions possibles :");
        System.out.println("1 - Prendre 3 jetons de couleurs différentes");
        System.out.println("2 - Prendre 2 jetons de la même couleur");
        System.out.println("3 - Acheter une carte");
        System.out.print("Choix : ");

        return scanner.nextInt();
    }
}
