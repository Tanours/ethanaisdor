package splendor.controller;

import java.io.IOException;

import splendor.model.Board;
import splendor.model.Card;

public class MainTest {
	public static void main(String[] args) {
		var board = new Board();
		var cards = board.getCards();
		System.out.println(Card.displayCards(cards.get(1)));
		
	}
}
