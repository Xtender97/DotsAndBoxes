package etf.dotsandboxes.bm160243d;

public class Box {
	public final static int LEFT = 0;
	public final static int RIGHT = 1;
	public final static int UP = 2;
	public final static int DOWN = 3;
	int number_of_edges = 0;
	Color color = Color.NONE;
	Board board;
	int Edges[] = { 0, 0, 0, 0 };

	public Box(Board b) {
		board = b;

	}
	public Box(Box b, Board brd) {
		number_of_edges = b.number_of_edges;
		board = brd;
		for(int i = 0 ; i < 4; i++) {
			Edges[i]= b.Edges[i];
		}
	}

	public void addEdge(int edge) {
		setEdge(edge);
		if (number_of_edges == 4) {
			if (board.getTurn() == 0) {
				color = Color.BLUE;
				board.player1++;
			} else {
				color = Color.RED;
				board.player2++;
			}
		}
	}

	public void changeColor(Color c) {
		color = c;
	}

	private void setEdge(int edge) {
		Edges[edge] = 1;
		number_of_edges++;

	}

	public int getNumber_of_edges() {
		return number_of_edges;
	}

	public Color getColor() {
		return color;
	}

	public int[] getEdges() {
		return Edges;
	}

	public Line addMissingEdge(int vrsta, int kolona) {
		int i = 0;
		while (Edges[i] == 1 && i < 4) {
			i++;
		}
		Dot d1, d2;
		Line linija;
		switch (i) {
		case LEFT: {
			d1 = new Dot(vrsta, kolona);
			d2 = new Dot(vrsta + 1, kolona);
			linija = new Line(d1, d2);
			return linija;

		}
		case RIGHT: {
			d1 = new Dot(vrsta, kolona + 1);
			d2 = new Dot(vrsta + 1, kolona + 1);
			linija = new Line(d1, d2);
			return linija;
		}
		case UP: {
			d1 = new Dot(vrsta, kolona);
			d2 = new Dot(vrsta, kolona + 1);
			linija = new Line(d1, d2);
			return linija;
		}
		case DOWN: {
			d1 = new Dot(vrsta + 1, kolona);
			d2 = new Dot(vrsta + 1, kolona + 1);
			linija = new Line(d1, d2);
			return linija;
		}
		default:

			return null;

		}
	}

}
