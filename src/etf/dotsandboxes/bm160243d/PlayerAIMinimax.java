package etf.dotsandboxes.bm160243d;

import java.util.ArrayList;
import java.util.Collections;

import javafx.scene.paint.Paint;

public class PlayerAIMinimax extends Player {
	int depth;

	public PlayerAIMinimax(Paint c, Game g, int d, long time) {
		super(c, g,d);
		depth = d;
		delay = time;
	}

	@Override
	public void makeMove() {
		

		if (!game.gameOver()) {
			MinimaxReturn r = minimax(game.getBoard(), depth, color == javafx.scene.paint.Color.BLUE, Integer.MIN_VALUE,
					Integer.MAX_VALUE);
			Moves moves = r.getLine();
			//System.out.println("Najbolja procena mi je " + r.getValue() );
			if (moves == null) {
				connectRandomDots();
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*int i = checkForPossibleBoxes();
				if (i > 0) {
					connectRandomDots();
				}*/
				//System.out.println("Povezao sam Random");
			} else {
				drawLine(moves.getFirst());
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				checkForPossibleBoxes();
				if (moves.getSecond() != null) {
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					drawLine(moves.getSecond());
				}
				//System.out.println("Povezao sam po Minimaxu");
			}
		}
	}

	public MinimaxReturn minimax(Board b, int dubina, boolean maximazingPlayer, int alpha, int beta) {
		int bestScore, currentScore;
		Moves bestMove = null;
		MinimaxReturn pom = null;
		ArrayList<Moves> moves;
		moves = b.getMoves();
		if (b.full() || dubina == 0 || moves.size() == 0) {
			return new MinimaxReturn(null, b.evaluateBoard());
		}
		if (maximazingPlayer) {
			bestScore = Integer.MIN_VALUE;
		} else {
			bestScore = Integer.MAX_VALUE;
		}
		Collections.shuffle(moves);
		for (Moves l : moves) {
			Board newBoard = b.copyBoard();
			newBoard.addLine(l);
			if (maximazingPlayer) {
				pom = minimax(newBoard, dubina - 1, false, alpha, beta);
				currentScore = pom.getValue();
				if (currentScore > bestScore) {
					bestScore = currentScore;
					bestMove = l;
					if (bestScore >= beta) {// odsecanje, ako smo izasli iz prozora
						return new MinimaxReturn(bestMove, bestScore);
					}
					alpha = Math.max(alpha, bestScore);
				}
			} else {
				pom = minimax(newBoard, dubina - 1, true, alpha, beta);
				currentScore = pom.getValue();
				if (currentScore < bestScore) {
					bestScore = currentScore;
					bestMove = l;
					if (bestScore <= alpha) {// odsecanje, ako smo izasli iz prozora
						return new MinimaxReturn(bestMove, bestScore);
					}
					beta = Math.min(beta, bestScore);
				}
			}
		}
		return new MinimaxReturn(bestMove, bestScore);

	}

}
