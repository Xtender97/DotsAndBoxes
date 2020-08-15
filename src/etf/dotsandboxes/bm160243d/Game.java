package etf.dotsandboxes.bm160243d;

import java.util.ArrayList;

public class Game implements Runnable {
	protected Player player1, player2;
	Board board;
	Platno platno;
	Player turn = player1;
	boolean HumanPlayed = false;
	boolean work, fromFile;
	FilePrinter printer;
	long delay;

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public Platno getPlatno() {
		return platno;
	}

	public void setPlatno(Platno platno) {
		this.platno = platno;
	}

	public void setTurn(Player turn) {
		this.turn = turn;
	}

	public Game(Board b) {
		board = b;
	}
	
	public Game(Board b, int M, int N, Player p1, Player p2, boolean fromFile, long time) {
		board=b;
		player1 = p1;
		player2 = p2;
		platno = new Platno(M, N, this);
		turn=player1;
		printer = new FilePrinter(this, M, N);
		this.fromFile=fromFile;
		delay=time;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Player getTurn() {
		return turn;
	}

	public void swapTurn() {
		if (turn == player1) {
			board.swapTurn();
			turn = player2;
			
		} else {
			turn = player1;
			board.swapTurn();
		}
		HumanPlayed = false;

	}
	
	public void makeMove() {
		while (!gameOver() && work) {
			turn.makeMove();
			swapTurn();
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//System.out.println("Game Over Score: B: " + player1.getScore()+" R: "+ player2.getScore());
		
		printer.closeFile();
		stop();
	}

	@Override
	public void run() {
		work=true;
		if (fromFile) {
			paintBoard();
		}
		while(work) {
			makeMove();
		}
		
		/*if(gameOver()) {
			Thread.interrupted();
		}*/
	}

	private void paintBoard() {
		FileReaderr reader = new FileReaderr(this);
		ArrayList<Line> lines = reader.readLines();
		
		for (Line l : lines) {
			
			if (board.checkForBox(l)[0]!= Position.NONE || board.checkForBox(l)[1]!= Position.NONE) {
				turn.drawLine(l);
				
			}
			else {
				turn.drawLine(l);
				swapTurn();
			}
			
		}
	}

	public Box getBox(int vrsta, int kolona) {
		return board.getBoxes()[vrsta][kolona];
	}


	public boolean gameOver() {
		return board.full();
	}

	public boolean getHumanPlayed() {
		return HumanPlayed;
	}
	
	public synchronized void setHumanPlayed() {
		HumanPlayed =  true;
	}

	public synchronized void stop() {
		work = false;
	}

	public FilePrinter getPrinter() {
		return printer;
	}

	public void setPrinter(FilePrinter printer) {
		this.printer = printer;
	}
	
}
