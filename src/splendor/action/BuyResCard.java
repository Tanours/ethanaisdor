package splendor.action;

import java.util.NoSuchElementException;
import java.util.Objects;

import splendor.model.Board;
import splendor.model.Card;
import splendor.model.Player;
import splendor.view.DisplayCards;
import splendor.view.DisplayInfo;
import splendor.view.DisplayPrompt;

public record BuyResCard(Player player,Board board) implements Action<Boolean>{
	public BuyResCard{
		Objects.requireNonNull(player);
	}
	private boolean buyReserved(Card card,Board board) {
		if(player.buyCard(card,board)) {
			player.getReserved().remove(card);
			return true;
		}
		return false;
	}
	public Boolean run(){
		if(player.getReserved().isEmpty()) return false;
		if(player.getReserved().size() == 1) {
			if(!buyReserved(player.getReserved().get(0),board)) {
				System.err.println("Ta seule carte réservée peut pas être achetée :(");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return false;
			}
			return true;
		}
		System.out.println("Vos cartes réservées :");
		System.out.println(new DisplayCards(player.getReserved()));
		while(true) {
			try {
				System.out.println(new DisplayPrompt("Choisisser une carte (ou 'q' pour quitter) :"));
				var input = sc.next();
				input = input.trim();
				if(input.equals("q")) return false;
				if(input.matches("\\d+")) {
					var id = Integer.parseInt(input);
					if(id > player.getReserved().size() || id < 1) {
						System.err.println("Le chiffre que tu veux ne semble pas être une option");
						continue;
					}
					if(buyReserved(player.getReserved().get(id-1),board)) {
						return true;
					}
					System.err.println("Vous êtes trop peu enrichi pour cette carte");
				}	
			}
			catch(NoSuchElementException e) {
				e.printStackTrace();
				return false;
			}
		}
		
	}
}
