package etf.dotsandboxes.bm160243d;

public class Line {
	private Dot dot1, dot2;

	public Line(Dot d1, Dot d2) {
		this.dot1 = d1;
		this.dot2 = d2;
	}

	public Dot getDot1() {
		return dot1;
	}

	public Dot getDot2() {
		return dot2;
	}
	
	public boolean isHorizontal() {
		if(dot1.getX()==dot2.getX()) return true;
		else return false;
	}
	public boolean isVerical() {
		if(dot1.getY()==dot2.getY()) return true;
		else return false;
	}
	
	public int minX() {
		return dot1.getX()<dot2.getX()?dot1.getX():dot2.getX();
	}
	
	public int minY() {
		return dot1.getY()<dot2.getY()?dot1.getY():dot2.getY();
	}
	
	

}
