package splendor;

import java.io.IOException;
import java.nio.file.Path;

public class Main {

	public static void main(String[] args) {
		var board = new Board();
		try {
			board.CardReferenceReader(Path.of("cardReference.html"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		var level1Cards = board.getCards().get(1);
		
		//reveler 4 cartes
		for(int i = 0; i < 4; i++) {
            System.out.print(i+1 + " :\n" + level1Cards.get(i));
        }
		
		//afficher les jetons
		System.out.println("jetons disponible : " + board.getTokens());

	}

}
