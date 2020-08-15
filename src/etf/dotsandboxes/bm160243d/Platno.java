package etf.dotsandboxes.bm160243d;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Platno extends Canvas implements EventHandler<MouseEvent> {
	final int HEIGHT = 600;
	final int WIDTH = 800;
	int M , N ;
	final int DOT_RADIUS = 30;
	final int MARGIN = 20;
	final int LINE_WIDTH = 30;
	Game game;
	boolean oneMoreMove = false;
	public Platno(int M, int N, Game game) {
		super();
		this.M=M;
		this.N=N;
		this.game= game;
		this.setHeight(HEIGHT);
		this.setWidth(WIDTH);
		GraphicsContext g = this.getGraphicsContext2D();
		g.setFill(Color.GREY);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		this.paintDots(g);
		this.setOnMouseClicked(this);
		
	}
	public void paintDots(GraphicsContext g) {
		double x = MARGIN;
		double y = MARGIN;
		g.setFill(Color.BLACK);
		for (int i = 0; i < M + 1; i++) {
			for (int j = 0; j < N + 1; j++) {
				g.fillOval(x, y, DOT_RADIUS, DOT_RADIUS);
				x += (WIDTH - ((N + 1) * DOT_RADIUS) - 2 * MARGIN) / N + DOT_RADIUS;
			}
			y += (HEIGHT - ((M + 1) * DOT_RADIUS) - 2 * MARGIN) / M + DOT_RADIUS;
			x = MARGIN;
		}
	}
	@Override
	public void handle(MouseEvent event) {
		
		if (event.getSource() == this && game.turn instanceof HumanPlayer) {
			double x1 = event.getSceneX();
			double y1 = event.getSceneY();
			oneMoreMove = false;
			Coordinate k1 = detectHorizintalLine(x1, y1);
			createHorizontalLine(k1);
			k1 = detectVerticalLine(x1, y1);
			createVerticalLine(k1);

			/*System.out.println(detectHorizintalLine(x1, y1));
			System.out.println(detectVerticalLine(x1, y1));
			System.out.println("x1: " + x1 + " y1: " + y1);*/
			if((!oneMoreMove && 
					(detectHorizintalLine(x1, y1).isValidCoordinate()|| detectVerticalLine(x1, y1).isValidCoordinate()))
					|| game.gameOver())
				game.setHumanPlayed();
			
		}

	}
	
	public void createHorizontalLine(Coordinate k1) {
		int top_x, top_y,line_height;
		GraphicsContext g = this.getGraphicsContext2D();
		if (k1.isValidCoordinate() && lineFreeHorizontal(k1)) {
			top_x = MARGIN + k1.getKolona() * ((WIDTH - ((N + 1) * DOT_RADIUS) - 2 * MARGIN) / N + DOT_RADIUS)
					+ DOT_RADIUS / 2;
			top_y = MARGIN + k1.getVrsta() * ((HEIGHT - (M + 1) * LINE_WIDTH - 2 * MARGIN) / M)
					+ k1.getVrsta() * LINE_WIDTH;
			line_height = (WIDTH - ((N + 1) * DOT_RADIUS) - 2 * MARGIN) / N + DOT_RADIUS;
			g.setFill(game.getTurn().getColor());
			g.fillRoundRect(top_x, top_y, line_height, LINE_WIDTH, 0, 0);
			paintDots(g);
			connectDotsHorizontal(k1);

		}
		else {
			if (k1.isValidCoordinate()) {
				oneMoreMove=true;
			}
		}
		
	}
	public void createVerticalLine(Coordinate k1) {
		int top_x, top_y,line_height;
		GraphicsContext g = this.getGraphicsContext2D();
		if (k1.isValidCoordinate() && lineFreeVertical(k1)) {
			top_x = MARGIN + k1.getKolona() * ((WIDTH - ((N + 1) * DOT_RADIUS) - 2 * MARGIN) / N + DOT_RADIUS);
			top_y = MARGIN + k1.getVrsta() * ((HEIGHT - (M + 1) * LINE_WIDTH - 2 * MARGIN) / M)
					+ k1.getVrsta() * LINE_WIDTH + DOT_RADIUS / 2;
			line_height = (HEIGHT - ((M + 1) * DOT_RADIUS) - 2 * MARGIN) / M + DOT_RADIUS;
			g.setFill(game.getTurn().getColor());
			g.fillRoundRect(top_x, top_y, LINE_WIDTH, line_height, 0, 0);
			paintDots(g);
			connectDotsVertical(k1);
			//System.out.println("crtam vertikalnu liniju");
		}
		else {
			if (k1.isValidCoordinate()) {
				oneMoreMove=true;
			}
		}

		
	}
	
	private Coordinate detectHorizintalLine(double x, double y) {// return one of the coordinates -1 if line is bad
		int a = -1, b = -1;
		for (int i = 0; i < M + 1; i++) {
			if (y > MARGIN + i * ((HEIGHT - (M + 1) * LINE_WIDTH - 2 * MARGIN) / M) + i * LINE_WIDTH
					&& y <= MARGIN + i * ((HEIGHT - (M + 1) * LINE_WIDTH - 2 * MARGIN) / M) + (i + 1) * LINE_WIDTH) {
				a = i;

			}
		}
		for (int i = 0; i < N; i++) {
			if (x > MARGIN + i * (WIDTH - 2 * MARGIN) / N && x <= MARGIN + (i + 1) * (WIDTH - 2 * MARGIN) / N) {
				b = i;
				//System.out.println(MARGIN + i * (WIDTH - 2 * MARGIN) / N + " pa ide do "
				//		+ (MARGIN + (i + 1) * ((WIDTH - 2 * MARGIN) / N)));
			}
		}

		return new Coordinate(a, b);
	}

	private Coordinate detectVerticalLine(double x, double y) {
		int a = -1, b = -1;
		for (int i = 0; i < N + 1; i++) {
			if (x > MARGIN + i * (WIDTH - (N + 1) * LINE_WIDTH - 2 * MARGIN) / N + i * LINE_WIDTH
					&& x <= MARGIN + i * (WIDTH - (N + 1) * LINE_WIDTH - 2 * MARGIN) / N + (i + 1) * LINE_WIDTH) {
				b = i;
			}
		}
		for (int i = 0; i < M; i++) {
			if (y > MARGIN + i * (HEIGHT - 2 * MARGIN) / M && y <= MARGIN + (i + 1) * (HEIGHT - 2 * MARGIN) / M) {
				a = i;
				//System.out.println(MARGIN + i * (WIDTH - 2 * MARGIN) / N + " pa ide do "
				//		+ (MARGIN + (i + 1) * ((WIDTH - 2 * MARGIN) / N)));
			}
		}

		return new Coordinate(a, b);
	}
	
	private boolean lineFreeHorizontal(Coordinate coordinate) {
		Dot d1 = new Dot(coordinate.getVrsta(), coordinate.getKolona());
		Dot d2 = new Dot(coordinate.getVrsta(), coordinate.getKolona()+1);
		Line l = new Line(d1,d2);
		return game.getBoard().checkLine(l);
	}
	
	private boolean lineFreeVertical(Coordinate coordinate) {
		Dot d1 = new Dot(coordinate.getVrsta(), coordinate.getKolona());
		Dot d2 = new Dot(coordinate.getVrsta()+1, coordinate.getKolona() );
		Line l = new Line(d1,d2);
		return game.getBoard().checkLine(l);
	}
	
	public void connectDotsHorizontal(Coordinate coordinate) {
		Dot d1 = new Dot(coordinate.getVrsta(), coordinate.getKolona());

		Dot d2 = new Dot(coordinate.getVrsta(), coordinate.getKolona() + 1);
		Line l = game.getBoard().connectDots(d1, d2);
		game.getPrinter().writeCoordinate(coordinate, true);
		Position[] p = game.getBoard().checkForBox(l);
		for (int i = 0; i < p.length; i++) {
			if (p[i] != Position.NONE) {
				paintBox(coordinate, p[i]);
				oneMoreMove = true;
				game.getTurn().increaceScore();
			}
		}
	}

	public void connectDotsVertical(Coordinate coordinate) {
		Dot d1 = new Dot(coordinate.getVrsta(), coordinate.getKolona());

		Dot d2 = new Dot(coordinate.getVrsta() + 1, coordinate.getKolona());
		Line l = game.getBoard().connectDots(d1, d2);
		game.getPrinter().writeCoordinate(coordinate, false);
		Position[] p = game.getBoard().checkForBox(l);
		for (int i = 0; i < p.length; i++) {
			if (p[i] != Position.NONE) {
				paintBox(coordinate, p[i]);
				oneMoreMove = true;
				game.getTurn().increaceScore();
			}
		}
	}

	public void paintBox(Coordinate coordinate, Position position) {
		GraphicsContext g = this.getGraphicsContext2D();
		int box_width = (WIDTH - ((N + 1) * DOT_RADIUS) - 2 * MARGIN) / N + 2 * DOT_RADIUS;
		int box_height = (HEIGHT - ((M + 1) * DOT_RADIUS) - 2 * MARGIN) / M + 2 * DOT_RADIUS;
		int top_x;
		int top_y;
		g.setFill(game.getTurn().getColor());
		switch (position) {
		case LEFT: {
			top_x = MARGIN
					+ (coordinate.getKolona() - 1) * ((WIDTH - ((N + 1) * DOT_RADIUS) - 2 * MARGIN) / N + DOT_RADIUS);
			top_y = MARGIN + coordinate.getVrsta() * ((HEIGHT - (M + 1) * LINE_WIDTH - 2 * MARGIN) / M)
					+ coordinate.getVrsta() * LINE_WIDTH;
			g.fillRoundRect(top_x, top_y, box_width, box_height, 30, 30);
			paintDots(g);
			break;
		}
		case RIGHT: {
			top_x = MARGIN
					+ (coordinate.getKolona() ) * ((WIDTH - ((N + 1) * DOT_RADIUS) - 2 * MARGIN) / N + DOT_RADIUS);
			top_y = MARGIN + coordinate.getVrsta() * ((HEIGHT - (M + 1) * LINE_WIDTH - 2 * MARGIN) / M)
					+ coordinate.getVrsta() * LINE_WIDTH;
			g.fillRoundRect(top_x, top_y, box_width, box_height, 30, 30);
			paintDots(g);
			break;
		}
		case UP: {
			top_x = MARGIN
					+ (coordinate.getKolona() ) * ((WIDTH - ((N + 1) * DOT_RADIUS) - 2 * MARGIN) / N + DOT_RADIUS);
			top_y = MARGIN + (coordinate.getVrsta()-1) * ((HEIGHT - (M + 1) * LINE_WIDTH - 2 * MARGIN) / M)
					+ (coordinate.getVrsta()-1) * LINE_WIDTH;
			g.fillRoundRect(top_x, top_y, box_width, box_height, 30, 30);
			paintDots(g);
			break;
		}
		case DOWN: {
			top_x = MARGIN
					+ (coordinate.getKolona() ) * ((WIDTH - ((N + 1) * DOT_RADIUS) - 2 * MARGIN) / N + DOT_RADIUS);
			top_y = MARGIN + coordinate.getVrsta() * ((HEIGHT - (M + 1) * LINE_WIDTH - 2 * MARGIN) / M)
					+ coordinate.getVrsta() * LINE_WIDTH;
			g.fillRoundRect(top_x, top_y, box_width, box_height, 30, 30);
			paintDots(g);
			break;
		}

		default:
			break;
		}
	}

}
