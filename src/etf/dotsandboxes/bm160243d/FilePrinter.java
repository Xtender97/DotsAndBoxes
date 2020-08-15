package etf.dotsandboxes.bm160243d;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class FilePrinter {
	PrintWriter writer;
	Game game;

	public FilePrinter(Game g, int M, int N) {
		try {
			game = g;
			writer = new PrintWriter("izlazni.txt", "UTF-8");
			writer.println(M + " " + N);
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void writeCoordinate(Coordinate c, boolean horizontal) {
		String boja ;
		if(game.getTurn().getColor()==javafx.scene.paint.Color.BLUE) {
			boja= "BLUE";
		}
		else {
			boja = "RED";
		}
		if (horizontal) {
			char slovo=(char) (c.getKolona()+65);
			writer.print(c.getVrsta());
			writer.println(slovo + " " + boja);
		}
		else {
			char slovo=(char) (c.getVrsta()+65);
			writer.print(slovo);
			writer.println(c.getKolona()+ " " + boja);
		}
	}

	public void closeFile() {
		writer.close();
	}
}
