package gamedemo.renderer;

import gamedemo.GameLoop;
import gamedemo.model.Stone;

public class ConsoleMain {

	private static final double HEIGHT = 1000.; // in meters
	private static final double G = 10.; // free fall acceleration
	private static final int UPS = 30; // updates per second

	public static void main(String[] args) {
		Stone s = new Stone(HEIGHT, -G, UPS);

		System.out.println("dropping the stone from " + HEIGHT + " meters...");

		GameLoop loop = new GameLoop(UPS, null, new GameLoop.Callbacks() {
			@Override
			public void update() {
				s.update();
			}

			@Override
			public boolean running() {
				return !s.isBottom();
			}
		});

		long startTime = System.nanoTime();
		loop.loop();

		System.out.println(
				"simulation took approximately " + ((System.nanoTime() - startTime) / 1000000000.) + " seconds");
		System.out.println("exact calculation is " + Math.sqrt(2. * HEIGHT / G) + " seconds");
	}

}
