package splendor.controller;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

import javax.swing.ImageIcon;

import com.github.forax.zen.Application;
import com.github.forax.zen.PointerEvent;

import splendor.model.Board;
import splendor.model.Card;
import splendor.model.Game;
import splendor.model.Parser;
import splendor.model.Player;
import splendor.view.DisplayCards;
import splendor.view.DisplayChoice;
import splendor.view.GraphicView;
import splendor.action.*;

public class MainTest {
	public static void main(String[] args) {

		Application.run(Color.black, ctx -> {
			List<Player> players = new GetPlayerInfo().run();

			var gamePhase = new GetPhase().run();
			var background = new ImageIcon("assets/ressources/background.png",null).getImage();
			var game = new Game(gamePhase, players);
			var view = new GraphicView(players, game.getBoard(), ctx);
			ctx.renderFrame(g2d->{
				g2d.drawImage(background,0,0,ctx.getScreenInfo().width(),ctx.getScreenInfo().height(),null);
			});
			view.drawBoard();
			for(;;) {
				 var event = ctx.pollEvent();
				 if(event == null) continue;
				 switch (event) {
				 	case PointerEvent p: {
				 		if(p.action().equals(PointerEvent.Action.POINTER_DOWN)) {
				 			var card = view.getCardFromView(p, game.getBoard().getCards());
					 		System.out.println(card);
				 		}
				 	}
				 	default : break;	
				 }
				 try {
					Thread.sleep(16);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			

		});

	}
}
