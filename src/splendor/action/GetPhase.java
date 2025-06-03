package splendor.action;

import splendor.controller.GamePhase;

import splendor.model.Board;

public record GetPhase(Board board) implements Action<GamePhase> {
	public GamePhase run();
}
