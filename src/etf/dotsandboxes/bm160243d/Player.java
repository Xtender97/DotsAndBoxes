package etf.dotsandboxes.bm160243d;

import java.util.Random;

import javafx.scene.paint.Paint;

public abstract class Player {
	int Score = 0;
	Paint color;
	Game game;
	long delay;

	public Player(Paint c, Game g, long d) {
		color = c;
		game = g;
		delay=d;
	}

	public void increaceScore() {
		Score++;
	}

	public abstract void makeMove();

	public int checkForPossibleBoxes() {
		int nmbOfBoxes = 0;
		for (int i = 0; i < game.getBoard().getM(); i++) {
			for (int j = 0; j < game.getBoard().getN(); j++) {
				// System.out.println("Proveravam " + i + " " + j);
				if (game.getBox(i, j).getNumber_of_edges() == 3) {
					nmbOfBoxes++;
					Line linija = game.getBox(i, j).addMissingEdge(i, j);
					drawLine(linija);
					/*System.out.println(linija.getDot1().getX() + ", " + linija.getDot1().getY() + " =>>> "
							+ linija.getDot2().getX() + ", " + linija.getDot2().getY());*/
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					nmbOfBoxes += checkForPossibleBoxes();
					return nmbOfBoxes;
				}
			}
		}
		return nmbOfBoxes;
	}

	public void drawLine(Line l) {
		if (l.isHorizontal()) {
			//System.out.println("Horizontalna Linija !!!!!!!!!!");
			game.getPlatno().createHorizontalLine(new Coordinate(l.minX(), l.minY()));

		}

		if (l.isVerical()) {
			//System.out.println("Vericalna Linijaa!!!!!!!!");
			game.getPlatno().createVerticalLine(new Coordinate(l.minX(), l.minY()));
		}
	}

	public void connectRandomDots() {
		Random r = new Random();
		int i = r.ints(0, (game.getBoard().getM() + 1)).limit(1).findFirst().getAsInt();
		int j = r.ints(0, (game.getBoard().getN() + 1)).limit(1).findFirst().getAsInt();
		int HorV = r.ints(0, 2).limit(1).findFirst().getAsInt();// 0 - horizontal 1- vertical
		Dot d1, d2;
		Line l;
		if (HorV == 0) {// horizontal
			d1 = new Dot(i, j);
			if (j != game.getBoard().getN()) {
				d2 = new Dot(i, j + 1);
			} else {
				d2 = new Dot(i, j - 1);
			}
			l = new Line(d1, d2);
		} else {
			d1 = new Dot(i, j);
			if (i != game.getBoard().getM()) {
				d2 = new Dot(i + 1, j);
			} else {
				d2 = new Dot(i - 1, j);
			}
			l = new Line(d1, d2);
		}
		while (!game.getBoard().checkLine(l)) {
			i = r.ints(0, (game.getBoard().getM() + 1)).limit(1).findFirst().getAsInt();
			j = r.ints(0, (game.getBoard().getN() + 1)).limit(1).findFirst().getAsInt();
			HorV = r.ints(0, 2).limit(1).findFirst().getAsInt();// 0 - horizontal 1- vertical
			if (HorV == 0) {// horizontal
				d1 = new Dot(i, j);
				if (j != game.getBoard().getN()) {
					d2 = new Dot(i, j + 1);
				} else {
					d2 = new Dot(i, j - 1);
				}
				l = new Line(d1, d2);
			} else {
				d1 = new Dot(i, j);
				if (i != game.getBoard().getM()) {
					d2 = new Dot(i + 1, j);
				} else {
					d2 = new Dot(i - 1, j);
				}
				l = new Line(d1, d2);
			}

		}
		drawLine(l);
		/*System.out.println(l.getDot1().getX() + ", " + l.getDot1().getY() + " =>>> " + l.getDot2().getX() + ", "
				+ l.getDot2().getY());*/
	}

	public int getScore() {
		return Score;
	}

	public void setScore(int score) {
		Score = score;
	}

	public Paint getColor() {
		return color;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	

}
