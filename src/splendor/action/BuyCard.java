package splendor.action;

import splendor.model.Board;

public record BuyCard(Board board) implements Action<Void>{
	public Void run();
}
