package splendor.action;

import java.io.IOError;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import splendor.model.Player;
import splendor.model.Stones;
import splendor.view.DisplayInSquare;
import splendor.view.DisplayPrompt;

public record GetPlayerInfo() implements Action<List<Player>> {
	
	private int getNbPlayer() {
		int nbJoueur = -1;
		while(nbJoueur < 0) {
			System.out.println(new DisplayPrompt(" Entrez le nombre de joueur (2-4) :"));
			if(sc.hasNextInt()) {
				nbJoueur = sc.nextInt();
				if(nbJoueur < 2  || nbJoueur > 4) {
					nbJoueur = -1;
					System.err.println("Le nombre de joueur doit être compris entre 2 et 4 pour plus de fun :)");
				}
			
			}
			else {
				System.err.println("Je pense que vous savez qu'un nombre c'est un nombre (^-^)!");
				sc.nextLine();
			}
			
		}
		return nbJoueur;
	}
	
	private String getPlayerName() {
		System.out.println(new DisplayPrompt("Nom :"));
		var joueurName = sc.next();
		return joueurName;
	}
	private int getPlayerAge() {
		while(true) {
			System.out.println(new DisplayPrompt("Age :"));
			
			var input =  sc.next().trim();
			if(input.matches("\\d+")) {
				return Integer.parseInt(input);
			}
			else {
				System.err.println("Tu connais pas ton age ?");
			}
		}
	}
	private String getRandomEmoji() {
		var emojis = new ArrayList<String>();
		emojis.add("(>.<)");
		emojis.add("(^_^)");
		emojis.add("(-.-)");
		emojis.add("(-o-)");
		emojis.add("('-')");
		emojis.add("(o.o)");
		emojis.add("(⌐■_■)");
		emojis.add("(╥_╥)");
		emojis.add("=^_^=");
		emojis.add("(▒_▒)");
		return emojis.get(ThreadLocalRandom.current().nextInt(emojis.size()-1));
	}
	public List<Player> run(){
		var res = new ArrayList<Player>();
		try {
			System.out.println(new DisplayInSquare(10,"Informations des joueurs"));
			int nbPlayer = getNbPlayer();
			for(var i = 0;i<nbPlayer;i++) {
				System.out.println("-".repeat(15));
				System.out.println("%s Joueur %d".formatted(getRandomEmoji(),i+1));
				System.out.println("-".repeat(15));
				var name = getPlayerName();
				var age = getPlayerAge();
				res.add(new Player(name,age));
			}
			return res;
			
			
		}
		catch(IOError e) {
			return null;
		}
		
	}
}
