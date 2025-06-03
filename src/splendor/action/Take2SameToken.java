package splendor.action;

import splendor.model.Board;
public record Take2SameToken(Board board) implements Action<Void> {
	public Void run();
}
