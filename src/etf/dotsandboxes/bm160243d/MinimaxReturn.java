package etf.dotsandboxes.bm160243d;

public class MinimaxReturn {
	Moves line;
	int value;
	public MinimaxReturn(Moves l, int v) {
		line=l;
		value=v;
		
	}
	public Moves getLine() {
		return line;
	}
	public void setLine(Moves line) {
		this.line = line;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
}
