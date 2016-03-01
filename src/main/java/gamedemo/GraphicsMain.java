package gamedemo;

import processing.core.PApplet;

public class GraphicsMain extends PApplet {

	private static final int UPS = 30;
	private static final long RATE = Math.round(1000000000.0 / UPS);

	private boolean simulate = false;

	private Stone s = new Stone(1000, -10.0 / UPS / UPS);

	private long nextUpdate;

	@Override
	public void settings() {
		size(600, 600);

	}

	public void setup() {
		background(0);

	}

	public void draw() {
		background(0);
		update();

		stroke(255);

		this.ellipse(300, 600 - (float) (s.getHeight() / 2 + 50), 10, 10);
	}

	public void update() {
		if (mousePressed && simulate == false && !s.isBottom()) {
			simulate = true;
			nextUpdate = System.nanoTime() + RATE;
		}

		if (!simulate) {
			return;
		}

		long t = System.nanoTime();

		if (nextUpdate < t) {
			s.update();
			nextUpdate += RATE;
		}

		if (s.isBottom()) {
			simulate = false;
		}

	}

	public static void main(String[] args) {
		PApplet.main(new String[] { "--present", "gamedemo.GraphicsMain" });
	}
}
