package gamedemo;

import processing.core.PApplet;

public class GraphicsMain extends PApplet {

	private static final int SIZE = 600;
	private static final double HEIGHT = 1000;
	private static final double G = 10.0;
	private static final int UPS = 30;
	private static final long RATE = Math.round(1000000000.0 / UPS);

	private boolean simulate = false;

	private Stone s = new Stone(HEIGHT, -G / UPS / UPS);

	private long nextUpdate;

	@Override
	public void settings() {
		size(SIZE, SIZE);
	}

	public void setup() {
		background(0);

	}

	public void draw() {
		background(0);
		update();

		stroke(255);

		this.ellipse(SIZE / 2, SIZE - (float) (s.getHeight() * 0.9 * SIZE / HEIGHT + 0.05 * SIZE), SIZE * 0.1f, SIZE * 0.1f);
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
