package splendor.action;

import java.util.Objects;

import splendor.model.Player;
import splendor.view.DisplayCards;

public record DisplayPlayerResCard(Player player) implements Action<Boolean>{
	public DisplayPlayerResCard{
		Objects.requireNonNull(player);
		
	}

	@Override
	public Boolean run() {
		if(player.getReserved().isEmpty()) {
			System.err.println("Tu cherches une option fantôme ? ne va pas trop vite mon ami");
			return false;
		}
		System.out.println(new DisplayCards(player.getReserved()));
		
		return false;
	}
}
