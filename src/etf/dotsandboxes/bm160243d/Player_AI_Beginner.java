package etf.dotsandboxes.bm160243d;

import java.util.Random;

import javafx.scene.paint.Paint;

public class Player_AI_Beginner extends Player {

	public Player_AI_Beginner(Game g, Paint c, long d) {
		super(c, g,d);
	}

	public void makeMove() {
		checkForPossibleBoxes();

		if (!game.gameOver()) {
			connectRandomDots();
			//System.out.println("AI odigrao RANDOM POTEZ JEBENI");
		}
		//System.out.println("AI odigrao");
	}

}
