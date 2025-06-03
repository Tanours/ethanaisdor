package splendor.action;

import splendor.model.Board;
public record Take3DiffToken(Board board) implements Action<Void> {
	public Void run() {
		System.out.println("Anais à raison ? ");
		var input = sc.next();
		System.out.println("oui".equals(input));
		return null;
	}
}
