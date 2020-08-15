package etf.dotsandboxes.bm160243d;

public class Coordinate {
	int vrsta,kolona;
	
	public Coordinate(int x, int y) {
		vrsta=x;
		kolona=y;
	}
	

	
	public int getVrsta() {
		return vrsta;
	}



	public void setVrsta(int vrsta) {
		this.vrsta = vrsta;
	}



	public int getKolona() {
		return kolona;
	}



	public void setKolona(int kolona) {
		this.kolona = kolona;
	}
	
	public boolean isValidCoordinate() {
		if (kolona < 0 || vrsta <0)
			return false;
		else return true;

	}



	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "X: " + vrsta + " Y: " + kolona + "\n";
	}
	 

}
