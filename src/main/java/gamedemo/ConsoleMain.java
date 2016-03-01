package gamedemo;

public class ConsoleMain {

	private static final int UPS = 30;
	private static final long RATE = Math.round(1000000000.0/UPS);
	public static void main(String[] args) {
		Stone s = new Stone(1000, -10.0/UPS/UPS);

		long nextUpdate =  System.nanoTime() + RATE;
		
		while(true) {
			long t = System.nanoTime();
			
			if(nextUpdate < t) {
				s.update();
				nextUpdate += RATE;
			} else {
				Thread.yield();
			}
			
			if(s.isBottom()) {
				return;
			}
		}
	}

}
