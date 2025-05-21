package splendor;

import java.io.IOException;
import java.util.StringJoiner;

public class MainTest {
	public static void main(String[] args) {
		var board = new Board();
		var cards = board.getCards();
		System.out.println(cards);
		
	}
}
