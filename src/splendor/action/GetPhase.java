package splendor.action;

import java.util.NoSuchElementException;

import splendor.controller.Base;
import splendor.controller.Complet;
import splendor.controller.GamePhase;

import splendor.model.Board;
import splendor.view.DisplayChoice;
import splendor.view.DisplayInSquare;
import splendor.view.DisplayPrompt;

public record GetPhase() implements Action<GamePhase> {
	
	public GamePhase run() {
		
		System.out.println(new DisplayInSquare(20,"Mode de jeu"));
		System.out.println(new DisplayChoice(false,"Mode simplifé","Mode Complet"));
		
		while (true) {
			try {
				System.out.println(new DisplayPrompt("Choisissez une mode de jeu :"));
				var input = sc.next().trim();
				if(input.matches("\\d+")) {
					var entry = Integer.parseInt(input);
					if(entry != 1 && entry != 2) {
						System.err.println("Ceci n'est pas un chiffre entre 1 et 2");
						continue;
					}
					else return switch (entry) {
						case 1 -> new Base();
						default -> new Complet();
					};
				}
			}catch(NoSuchElementException e) {
				e.printStackTrace();
				return null;
			}
			
		}
	}
}
