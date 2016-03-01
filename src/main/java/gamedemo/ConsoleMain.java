package gamedemo;

public class ConsoleMain {

	private static final double HEIGHT = 1000.; // in meters
	private static final double G = 10.; // free fall acceleration
	private static final int UPS = 30; // updates per second
	private static final long RATE = Math.round(1000000000.0/UPS);
	
	public static void main(String[] args) {
		Stone s = new Stone(HEIGHT, -G/UPS/UPS);

		System.out.println("dropping the stone from " + HEIGHT + " meters...");
		
		long nextUpdate =  System.nanoTime() + RATE;
		
		long updateCount = 0;
		
		while(true) {
			long t = System.nanoTime();
			
			if(nextUpdate < t) {
				updateCount++;
				s.update();
				nextUpdate += RATE;
			} else {
				Thread.yield();
			}
			
			if(s.isBottom()) {
				break;
			}
		}
		
		System.out.println("simulation took " + (updateCount * (RATE / 1000000000.)) + " seconds");
		System.out.println("exact calculation is " + Math.sqrt(2. * HEIGHT / G) + " seconds");
	}

}
