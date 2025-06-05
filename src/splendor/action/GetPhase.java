package splendor.action;

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
		System.out.println(new DisplayChoice("Mode simplifé","mode Complet"));
		
		var validate = -1;
		while(validate < 0) {
			sc.nextLine();
			System.out.println(new DisplayPrompt("Choisissez une difficulté :"));
			if(sc.hasNextInt()) {
				
				var input = sc.nextInt();
				if(input != 1 && input != 2) {
					System.err.println("Ceci n'est ni 1 ni un 2");
					
					continue;
				}
				else {
					return switch (input) {
					case 1 -> new Base();
					default -> new Complet();
					};
				}
			}

		}
		return null;
	}
}
