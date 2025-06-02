package splendor.controller;
import splendor.model.Card;
import splendor.model.Noble;
import java.util.List;
import java.util.Map;

public sealed interface GamePhase permits Base, Complet{
	boolean nobles();
	boolean reservation();
	int getMaxCard();
	int getMaxChoice();
	Map<Integer, List<Card>> initCards();
	List<Noble> initNobles();
	
	
}
