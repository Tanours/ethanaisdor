package splendor;

public class Main {

	public static void main(String[] args) {
		var board = new Board();
		
		var level1Cards = board.getCards().get(1);
		
		//reveler 4 cartes
		for(int i = 0; i < 4; i++) {
            System.out.print(i+1 + " :\n" + level1Cards.get(i));
        }
		
		//afficher les jetons
		System.out.println("jetons disponible : " + board.getTokens());

	}

}
