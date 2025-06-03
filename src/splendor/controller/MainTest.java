package splendor.controller;



import java.io.IOException;

import splendor.model.Board;
import splendor.model.Card;
import splendor.action.*;

public class MainTest {
	public static void main(String[] args) {
		var info = new GetPlayerInfo();
		var players = info.run();
		
	}
}
