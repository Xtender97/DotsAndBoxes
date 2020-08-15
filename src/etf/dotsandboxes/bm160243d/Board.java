package etf.dotsandboxes.bm160243d;

import java.util.ArrayList;

public class Board {
	private int M, N;
	private Line[][] horizontal;
	private Line[][] vertical;
	private Box[][] boxes;
	private int turn = 0; // 0 - player 1 (blue) maximazing 1 - player 2 (red)minimazing
	int player1 = 0, player2 = 0;

	public Board(int M, int N) {
		this.M = M;
		this.N = N;
		horizontal = new Line[M + 1][N];
		vertical = new Line[M][N + 1];
		boxes = new Box[M][N];
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				boxes[i][j] = new Box(this);
			}
		}
	}

	public int evaluateBoard() {
		int br = player1 - player2;
		//if (br != 0)
			//System.out.println(br + " p1: " + player1 + " p2: " + player2);
		return br;
	}

	public Board copyBoard() {
		Board newBoard = new Board(M, N);
		for (int i = 0; i < M + 1; i++) {
			for (int j = 0; j < N; j++) {
				newBoard.horizontal[i][j] = horizontal[i][j];
			}
		}
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N + 1; j++) {
				newBoard.vertical[i][j] = vertical[i][j];
			}
		}
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				newBoard.boxes[i][j] = new Box(boxes[i][j], newBoard);
			}
		}
		newBoard.player1 = player1;
		newBoard.player2 = player2;
		newBoard.turn = turn;

		return newBoard;
	}

	public void addLine(Moves m) {

		Line l = m.getFirst();
		if (checkLine(l)) {

			if (checkForBox(l)[0]!= Position.NONE || checkForBox(l)[1]!= Position.NONE) {
				connectDots(l.getDot1(), l.getDot2());
				connectBoxes();
				if (m.getSecond() != null) {
					connectDots(m.getSecond().getDot1(), m.getSecond().getDot2());
				}
			}

			else {
				connectDots(l.getDot1(), l.getDot2());
			}

			swapTurn();

		} else {
			System.out.println("Ne moze se dodati linija");
		}
	}

	public ArrayList<Moves> getMoves() {
		ArrayList<Moves> lines = new ArrayList<Moves>();
		for (int i = 0; i < M + 1; i++) {
			for (int j = 0; j < N; j++) {
				if (horizontal[i][j] == null && !isThirdEdgeHorizontal(i, j)) {
					Dot d1 = new Dot(i, j);
					Dot d2 = new Dot(i, j + 1);
					Line l = new Line(d1, d2);
					if (isFourthEdgeHorizontal(i, j)) {
						//System.out.println("********************cetvrta linija");
						ArrayList<Moves> secondMoves = addAllMovesAfterBox(lines, l);
						for (Moves m : secondMoves) {
							//System.out.println("stavrnno sam dodao************");
							Moves move = new Moves(l, m.getFirst());
							lines.add(move);
						}
					} else {
						Moves move = new Moves(l, null);
						lines.add(move);
					}

				}
			}
		}
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N + 1; j++) {
				if (vertical[i][j] == null && !isThirdEdgeVertical(i, j)) {
					Dot d1 = new Dot(i, j);
					Dot d2 = new Dot(i + 1, j);
					Line l = new Line(d1, d2);

					if (isFourthEdgeVertical(i, j)) {
						//System.out.println("********************cetvrta linija");
						ArrayList<Moves> secondMoves = addAllMovesAfterBox(lines, l);
						for (Moves m : secondMoves) {
							//System.out.println("stavrnno sam dodao************");

							Moves move = new Moves(l, m.getFirst());
							lines.add(move);
						}
					} else {
						Moves move = new Moves(l, null);
						lines.add(move);
					}
				}
			}
		}
		//System.out.println("Nasao sam " + lines.size() + "potreza!!!!");
		return lines;
	}

	private ArrayList<Moves> addAllMovesAfterBox(ArrayList<Moves> lines, Line l) {
		Board tabla = this.copyBoard();

		tabla.addLine(new Moves(l, null));
		ArrayList<Moves> mov = tabla.getMoves();
		if (mov.size() == 0 && !tabla.full()) {
			
			mov = tabla.getAllMoves();
		}
		if (tabla.full()) {
			mov.add(new Moves(null, null));
		}
		return mov;
	}

	public boolean connectBoxes() {
		boolean connected = false;
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				if (boxes[i][j].getNumber_of_edges() == 3) {
					Line linija = boxes[i][j].addMissingEdge(i, j);
					connectDots(linija.getDot1(), linija.getDot2());
					/*
					 * System.out.println(linija.getDot1().getX() + ", " + linija.getDot1().getY() +
					 * " =>>> " + linija.getDot2().getX() + ", " + linija.getDot2().getY());
					 */
					connected = true;
					connectBoxes();

				}

			}
		}
		return connected;
	}

	public ArrayList<Moves> getAllMoves() {
		ArrayList<Moves> lines = new ArrayList<Moves>();
		for (int i = 0; i < M + 1; i++) {
			for (int j = 0; j < N; j++) {
				if (horizontal[i][j] == null) {
					Dot d1 = new Dot(i, j);
					Dot d2 = new Dot(i, j + 1);
					Line l = new Line(d1, d2);
					Moves move = new Moves(l, null);
					lines.add(move);
				}
			}
		}
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N + 1; j++) {
				if (vertical[i][j] == null) {
					Dot d1 = new Dot(i, j);
					Dot d2 = new Dot(i + 1, j);
					Line l = new Line(d1, d2);
					Moves move = new Moves(l, null);
					lines.add(move);
				}
			}
		}
		return lines;
	}

	private boolean isThirdEdgeHorizontal(int i, int j) {
		boolean treca = false;
		if (i < M) {
			if (boxes[i][j].getNumber_of_edges() == 2) {
				treca = true;
			}
		}
		if (i > 0) {
			if (boxes[i - 1][j].getNumber_of_edges() == 2) {
				treca = true;
			}
		}
		if (i < M) {
			if (boxes[i][j].getNumber_of_edges() == 3) {
				treca = false;
			}
		}
		if (i > 0) {
			if (boxes[i - 1][j].getNumber_of_edges() == 3) {
				treca = false;
			}
		}
		return treca;
	}

	private boolean isFourthEdgeHorizontal(int i, int j) {
		if (i < M) {
			if (boxes[i][j].getNumber_of_edges() == 3) {
				return true;
			}
		}
		if (i > 0) {
			if (boxes[i - 1][j].getNumber_of_edges() == 3) {
				return true;
			}
		}
		return false;
	}

	private boolean isThirdEdgeVertical(int i, int j) {
		boolean treca = false;
		if (j < N) {
			if (boxes[i][j].getNumber_of_edges() == 2) {
				treca = true;
			}
		}
		if (j > 0) {
			if (boxes[i][j - 1].getNumber_of_edges() == 2) {
				treca = true;
			}
		}
		if (j < N) {
			if (boxes[i][j].getNumber_of_edges() == 3) {
				treca = false;
			}
		}
		if (j > 0) {
			if (boxes[i][j - 1].getNumber_of_edges() == 3) {
				treca = false;
			}
		}
		return treca;
	}

	private boolean isFourthEdgeVertical(int i, int j) {
		if (j < N) {
			if (boxes[i][j].getNumber_of_edges() == 3) {
				return true;
			}
		}
		if (j > 0) {
			if (boxes[i][j - 1].getNumber_of_edges() == 3) {
				return true;
			}
		}
		return false;
	}

	public Line connectDots(Dot x, Dot y) {// pazi da ne povezes vec povezane
		Line l = new Line(x, y);
		if (l.isHorizontal()) {
			horizontal[x.getX()][l.minY()] = l;
			if (x.getX() < M) {
				boxes[x.getX()][l.minY()].addEdge(Box.UP);
			}
			if (x.getX() > 0) {
				boxes[x.getX() - 1][l.minY()].addEdge(Box.DOWN);
			}

		} else if (l.isVerical()) {

			vertical[l.minX()][x.getY()] = l;
			if (x.getY() < N) {
				boxes[l.minX()][x.getY()].addEdge(Box.LEFT);
			}
			if (x.getY() > 0) {
				boxes[l.minX()][x.getY() - 1].addEdge(Box.RIGHT);
			}

		}
		return l;

	}

	public Position[] checkForBox(Line x) {
		Position[] rez = new Position[2];
		int i = 0;
		if (x.isHorizontal()) {
			if (x.getDot1().getX() == 0) {
				if (horizontal[x.getDot1().getX() + 1][x.minY()] != null
						&& vertical[x.getDot1().getX()][x.getDot1().getY()] != null
						&& vertical[x.getDot1().getX()][x.getDot2().getY()] != null) {
					rez[i++] = Position.DOWN;
				}
			} else if (x.getDot1().getX() == M) {
				if (horizontal[x.getDot1().getX() - 1][x.minY()] != null
						&& vertical[x.getDot1().getX() - 1][x.getDot1().getY()] != null
						&& vertical[x.getDot1().getX() - 1][x.getDot2().getY()] != null) {
					rez[i++] = Position.UP;
				}
			} else {

				if (horizontal[x.getDot1().getX() + 1][x.minY()] != null
						&& vertical[x.getDot1().getX()][x.getDot1().getY()] != null
						&& vertical[x.getDot1().getX()][x.getDot2().getY()] != null) {
					rez[i++] = Position.DOWN;
				}
				if (horizontal[x.getDot1().getX() - 1][x.minY()] != null
						&& vertical[x.getDot1().getX() - 1][x.getDot1().getY()] != null
						&& vertical[x.getDot1().getX() - 1][x.getDot2().getY()] != null) {
					rez[i++] = Position.UP;
				}
			}

		} else if (x.isVerical()) {
			if (x.getDot1().getY() == 0) {
				if (vertical[x.minX()][x.getDot1().getY() + 1] != null
						&& horizontal[x.getDot1().getX()][x.getDot1().getY()] != null
						&& horizontal[x.getDot2().getX()][x.getDot1().getY()] != null) {
					rez[i++] = Position.RIGHT;
				}
			} else if (x.getDot1().getY() == N) {
				if (vertical[x.minX()][x.getDot1().getY() - 1] != null
						&& horizontal[x.getDot1().getX()][x.getDot1().getY() - 1] != null
						&& horizontal[x.getDot2().getX()][x.getDot1().getY() - 1] != null) {
					rez[i++] = Position.LEFT;
				}
			} else {

				if (vertical[x.minX()][x.getDot1().getY() + 1] != null
						&& horizontal[x.getDot1().getX()][x.getDot1().getY()] != null
						&& horizontal[x.getDot2().getX()][x.getDot1().getY()] != null) {
					rez[i++] = Position.RIGHT;
				}
				if (vertical[x.minX()][x.getDot1().getY() - 1] != null
						&& horizontal[x.getDot1().getX()][x.getDot1().getY() - 1] != null
						&& horizontal[x.getDot2().getX()][x.getDot1().getY() - 1] != null) {
					rez[i++] = Position.LEFT;
				}
			}
		}
		while (i < 2) {
			rez[i++] = Position.NONE;
		}
		return rez;

	}

	public int getTurn() {
		return turn;
	}

	public void swapTurn() {
		if (turn == 0) {
			turn = 1;
		} else
			turn = 0;
	}

	public boolean checkLine(Line line) {
		if (line.isHorizontal()) {
			if (horizontal[line.getDot1().getX()][line.minY()] == null) {
				return true;
			} else
				return false;
		} else {
			if (vertical[line.minX()][line.getDot1().getY()] == null) {
				return true;
			} else
				return false;
		}
	}

	public boolean full() {
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				// System.out.println("full "+ i + " " + j + " "
				// +boxes[i][j].getNumber_of_edges());
				if (boxes[i][j].getNumber_of_edges() < 4) {
					return false;
				}
			}
		}
		return true;
	}

	public int getM() {
		return M;
	}

	public int getN() {
		return N;
	}

	public Line[][] getHorizontal() {
		return horizontal;
	}

	public void setHorizontal(Line[][] horizontal) {
		this.horizontal = horizontal;
	}

	public Line[][] getVertical() {
		return vertical;
	}

	public void setVertical(Line[][] vertical) {
		this.vertical = vertical;
	}

	public Box[][] getBoxes() {
		return boxes;
	}

	public void setBoxes(Box[][] boxes) {
		this.boxes = boxes;
	}

}
