package etf.dotsandboxes.bm160243d;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileReaderr {
	File file;
	Game game;
	public FileReaderr(Game g) {
		file = new File("2. adv_vs_beg.txt");
		game=g;
	}

	public ArrayList<Line> readLines() {
		ArrayList<Line> lista = new ArrayList<>();
		int vrsta, kolona;
		Dot d1, d2;
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line=br.readLine();
			int M = line.charAt(0)-48;
			int N= line.charAt(2)-48;
			
			Board board= new Board(M, N);
			game.setBoard(board);
			Platno platno= new Platno(M, N, game);
			game.setPlatno(platno);
			while ((line = br.readLine()) != null) {
				if (isNumeric(line.substring(0, 1))) {
					vrsta = Integer.parseInt(line.substring(0, 1));
					kolona = line.charAt(1) - 65;
					d1 = new Dot(vrsta, kolona);
					d2 = new Dot(vrsta, kolona + 1);
					
				} else {
					vrsta = line.charAt(0) - 65;
					kolona = Integer.parseInt(line.substring(1, 2));
					d1 = new Dot(vrsta, kolona);
					d2 = new Dot(vrsta+1, kolona);
				}
				
				
				Line l = new Line(d1, d2);
				lista.add(l);

			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
	}

	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			int d = Integer.parseInt(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
