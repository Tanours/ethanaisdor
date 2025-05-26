package splendor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class Action {

    private final Scanner scanner;

    public Action(Scanner scanner) {
        this.scanner = scanner;
    }

    public boolean one(Board board, Player player) {
        Set<Stones> pickedColors = new HashSet<>();
        while (pickedColors.size() < 3) {
            System.out.println("Choisissez une couleur différente : (" + (pickedColors.size()+1) + "/3)");
            try {
                Stones stone = Stones.valueOf(scanner.next().toUpperCase());
                if (pickedColors.contains(stone)) continue;
                if (!board.selectTokens(player, stone, 1)) continue;
                pickedColors.add(stone);
            } catch (IllegalArgumentException e) {
                System.out.println("Couleur invalide.");
            }
        }
        return true;
    }

    private boolean two(Board board, Player player) {
        System.out.println("Choisissez une couleur pour prendre 2 jetons :");
        try {
            Stones stone = Stones.valueOf(scanner.next().toUpperCase());
            return board.selectTokens(player, stone, 2);
        } catch (IllegalArgumentException e) {
            System.out.println("Couleur invalide.");
        }
        return false;
    }

    private boolean three(Board board, Player player) {
        System.out.println("\nChoisissez une carte à acheter : ");
        board.revealCards();
        if (scanner.hasNextInt()) {
            int cardIndex = scanner.nextInt() - 1;
            scanner.nextLine();
            var card = board.getCards().get(1).get(cardIndex);
            if (!board.selectCard(player, card)) {
                System.out.println("Vous ne pouvez pas acheter cette carte.");
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean play(int choice, Player player, Board board) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(board);
            return switch (choice) {
                case 1 -> one(board, player);
                case 2 -> two(board, player);
                case 3 -> three(board, player);
                default -> {
                    System.out.println("Choix invalide.");
                    yield false;
                }
            };
    }
}
