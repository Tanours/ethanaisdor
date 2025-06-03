package splendor.controller;



import java.io.IOException;

import splendor.model.Board;
import splendor.model.Card;
import splendor.view.DisplayChoice;
import splendor.action.*;

public class MainTest {
	public static void main(String[] args) {
		var phase = new GetPhase();
		System.out.println(new DisplayChoice("prendre 3 jetons différents","prendre 2 même jeton","Acheter une carte"));
		phase.run();
		
		
		
	}
}
