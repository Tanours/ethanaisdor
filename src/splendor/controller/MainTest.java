package splendor.controller;

import java.io.IOException;

import splendor.model.Board;

public class MainTest {
	public static void main(String[] args) {
		var board = new Board();
		var cards = board.getCards();
		try {
			System.out.println(board.CardReferenceReader("ressources/cardReferences.html"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(cards);
		
	}
}
