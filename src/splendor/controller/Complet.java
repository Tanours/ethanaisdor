package splendor.controller;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import splendor.model.Card;
import splendor.model.Noble;
import splendor.model.Parser;

public record Complet() implements GamePhase {

	@Override
	public boolean nobles() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reservation() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Map<Integer, List<Card>> initCards() {
		var res = Parser.getDeveloppementCard(Path.of("card.csv"), StandardCharsets.UTF_8);
		for (var entry : res.entrySet()) {
	        Collections.shuffle(entry.getValue());
	    }
		return res;
	}
	public List<Noble> initNobles() {
		var res = Parser.getNobles(Path.of("nobles.csv"), StandardCharsets.UTF_8);
		
		return res;
	}
	public int getMaxCard() {
		return 12;
	}
	public int getMaxChoice() {
		return 5;
	}

	

}
