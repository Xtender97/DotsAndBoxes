package etf.dotsandboxes.bm160243d;

public class Test {

	public static void main(String[] args) {
		Board b = new Board(2, 3);
		Dot d1 = new Dot(1,1);
		Dot d2 = new Dot(1,2);
		Line l = b.connectDots(d1, d2);
		System.out.println(b.checkForBox(l)[0].toString());
		System.out.println(b.checkForBox(l)[1].toString()+"\n");
		
		d1 = new Dot(1,1);
		d2 = new Dot(2,1);
		l = b.connectDots(d1, d2);
		System.out.println(b.checkForBox(l)[0].toString());
		System.out.println(b.checkForBox(l)[1].toString()+"\n");
		
		d1 = new Dot(1,2);
		d2 = new Dot(2,2);
		l = b.connectDots(d1, d2);
		System.out.println(b.checkForBox(l)[0].toString());
		System.out.println(b.checkForBox(l)[1].toString()+"\n");
		
		d1 = new Dot(2,1);
		d2 = new Dot(2,2);
		l = b.connectDots(d1, d2);
		System.out.println(b.checkForBox(l)[0].toString());
		System.out.println(b.checkForBox(l)[1].toString()+"\n");
		
		d1 = new Dot(0,1);
		d2 = new Dot(0,2);
		l = b.connectDots(d1, d2);
		System.out.println(b.checkForBox(l)[0].toString());
		System.out.println(b.checkForBox(l)[1].toString()+"\n");
		
		d1 = new Dot(0,1);
		d2 = new Dot(1,1);
		l = b.connectDots(d1, d2);
		System.out.println(b.checkForBox(l)[0].toString());
		System.out.println(b.checkForBox(l)[1].toString()+"\n");
		
		d1 = new Dot(0,2);
		d2 = new Dot(1,2);
		l = b.connectDots(d1, d2);
		System.out.println(b.checkForBox(l)[0].toString());
		System.out.println(b.checkForBox(l)[1].toString()+"\n");
	}

}
