package splendor.action;

import splendor.model.Board;

public record ResCard(Board board) implements Action<Void>{
	public Void run();
}
