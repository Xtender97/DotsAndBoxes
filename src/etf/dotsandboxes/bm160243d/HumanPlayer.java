package etf.dotsandboxes.bm160243d;

import javafx.scene.paint.Paint;

public class HumanPlayer extends Player {
	
	public HumanPlayer(Game g, Paint c, long d) {
		super(c,g,d);
		
	}

	@Override
	public void makeMove() {
		
			//System.out.println("Cekam");
			while(!game.getHumanPlayed() && game.work) {
				//System.out.println("U petlji sam i glup sam");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
			//System.out.println("Budim se");

	}


	
	
	


	
}
