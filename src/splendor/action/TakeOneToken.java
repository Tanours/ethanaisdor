package splendor.action;

import java.util.Objects;

import splendor.model.Board;
import splendor.model.Player;
import splendor.model.Stones;

public record TakeOneToken(Board board, Player player, Stones stone) implements Action<Boolean>{
	public TakeOneToken {
		Objects.requireNonNull(board);
		Objects.requireNonNull(player);
		Objects.requireNonNull(stone);
	}

	@Override
	public Boolean run() {
		board.getTokens().put(stone, board.getTokens().get(stone) - 1);
	    player.addToken(stone, 1);
		return true;
	}
	
	
}
