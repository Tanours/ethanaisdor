package splendor.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

import com.github.forax.zen.Application;

import splendor.action.GetPhase;
import splendor.action.GetPlayerInfo;
import splendor.model.Card;
import splendor.model.Game;
import splendor.model.Parser;
import splendor.model.Player;

public class Main {
    public static void main(String[] args) {
			List<Player> players = new GetPlayerInfo().run();

        	var gamePhase = new GetPhase().run();
           
            var game = new Game(gamePhase,players);
            game.run();
 
    }
}
