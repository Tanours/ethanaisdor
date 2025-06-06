package splendor.action;

import java.util.List;
import java.util.Objects;

import splendor.model.Board;
import splendor.model.Noble;
import splendor.model.Player;
import splendor.view.DisplayPrompt;

public record NobleVisit(Board board, Player player) implements Action<Boolean>{
	
	public NobleVisit {
		Objects.requireNonNull(board);
		Objects.requireNonNull(player);
	}
	
	private List<Noble> allNobleVisit() {
	    return board.getNobles().stream()
	        .filter(noble -> noble.canVisit(player) && !player.getNobles().contains(noble))
	        .toList();
	}

	
	private Noble selectNoble(List<Noble> nobles) {
		System.out.println(new DisplayPrompt("Choisissez un noble à acquerir :"));
	    for (int i = 0; i < nobles.size(); i++) {
	        System.out.println("[" + (i + 1) + "]" + nobles.get(i).name());
	    }
	    //System.out.println(new DisplayCards(nobles));
	    var choice = -1;
	    while (choice < 1 || choice > nobles.size()) {
	    	System.out.println(new DisplayPrompt("Entrez le numéro du noble : "));
	        if (Action.sc.hasNextInt()) {
	            choice = Action.sc.nextInt();
	            Action.sc.nextLine();
	        } else {
	        	Action.sc.nextLine();
	        	System.err.println("Entrée invalide");
	        }
	    }
	    return nobles.get(choice - 1);
	}
	
	public boolean visitNobles() {
		Objects.requireNonNull(player);
		
		var allNoble = this.allNobleVisit();
		
		if(allNoble.isEmpty()) return false;
		
		System.out.println("Vous avez la visite de %s : ".formatted(allNoble.size() > 1 ? "plusieurs nobles" : "d'un noble"));
		
		var noble = (allNoble.size() == 1) ? allNoble.get(0) : this.selectNoble(allNoble);

	    System.out.println(noble);
	    player.getNobles().add(noble);
	    player.addPrestige(noble.prestige());
	    System.out.println(new DisplayPrompt("Vous avez acquis un noble"));
	    
	    return true;
	}

	@Override
	public Boolean run() {
		return visitNobles();
	}

}
