package splendor.action;

import splendor.controller.GamePhase;

import splendor.model.Board;
import splendor.view.DisplayInSquare;
import splendor.view.DisplayPrompt;

public record GetPhase() implements Action<GamePhase> {
	
	public GamePhase run() {
		
		System.out.println(new DisplayInSquare("Difficulté",20));
		System.out.println(new DisplayPrompt("Choisissez une difficulté :"));
		return null;
	}
}
