package etf.dotsandboxes.bm160243d;

public class Moves {
	
	Line first = null, second= null;
	
	public Moves(Line f, Line s) {
		first = f;
		second = s;
	}

	public Line getFirst() {
		return first;
	}

	public void setFirst(Line first) {
		this.first = first;
	}

	public Line getSecond() {
		return second;
	}

	public void setSecond(Line second) {
		this.second = second;
	}
	
	
}
