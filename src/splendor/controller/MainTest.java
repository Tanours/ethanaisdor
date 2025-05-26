package splendor.controller;

import splendor.model.Board;

public class MainTest {
	public static void main(String[] args) {
		var board = new Board();
		var cards = board.getCards();
		System.out.println(cards);
		
	}
}
