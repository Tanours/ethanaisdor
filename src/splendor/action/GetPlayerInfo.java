package splendor.action;

import java.io.IOError;
import java.util.ArrayList;
import java.util.List;

import splendor.model.Player;
import splendor.model.Stones;

public record GetPlayerInfo() implements Action<List<Player>> {
	
	private int getNbPlayer(String arrow) {
		int nbJoueur = -1;
		while(nbJoueur < 0) {
			System.out.println(arrow+" Entrez le nombre de joueur (2-4) :");
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
	
	private String getPlayerName(String arrow) {
		System.out.println(arrow+" Nom :");
		var joueurName = sc.next();
		return joueurName;
	}
	private int getPlayerAge(String arrow) {
		var joueurAge = -1;
		while(joueurAge < 0) {
			System.out.println(arrow+" Age :");
			
			sc.nextLine();
			if(sc.hasNextInt()) {
				joueurAge = sc.nextInt();
			}
			else {
				System.err.println("Tu connais pas ton age ?");
			}
		}
		
		
		return joueurAge;
	}
	public List<Player> run(){
		var res = new ArrayList<Player>();
		try {
			var arrow = "%s%1s%s".formatted(Stones.DIAMOND.getColor(),"▶",Stones.resetColor());
			System.out.println("╔═════════════════════════════════════════════╗");
			System.out.println("║          Informations des joueurs           ║");
			System.out.println("╚═════════════════════════════════════════════╝");
			int nbPlayer = getNbPlayer(arrow);
			for(var i = 0;i<nbPlayer;i++) {
				System.out.println("-".repeat(12));
				System.out.println("👤 Joueur %d".formatted(i+1));
				System.out.println("-".repeat(12));
				var name = getPlayerName(arrow);
				var age = getPlayerAge(arrow);
				res.add(new Player(name,age));
			}
			return res;
			
			
		}
		catch(IOError e) {
			return null;
		}
		
	}
}
