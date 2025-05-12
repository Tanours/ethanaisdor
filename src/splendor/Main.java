package splendor;

public class Main {

	public static void main(String[] args) {
		var board = new Board();
		var cards = board.generateCardsListWithCost(1,1 ,8, 3);
		System.out.println(cards);

	}

}
