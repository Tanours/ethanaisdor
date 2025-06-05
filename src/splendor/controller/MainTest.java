package splendor.controller;



import java.io.IOException;

import splendor.model.Board;
import splendor.model.Card;
import splendor.view.DisplayChoice;
import splendor.action.*;

public class MainTest {
	public static void main(String[] args) {
		var joueur = new GetPlayerInfo().run();
		var phase = new GetPhase().run();
		var board = new Board(phase);
		
		var cardReserved = new ResCard(board,joueur.get(0)).run();
		if(cardReserved) System.out.println(joueur.get(0));
		var cardReserved2 = new ResCard(board,joueur.get(0)).run();
		if(cardReserved2) System.out.println(joueur.get(0));
		
		
		
	}
}
