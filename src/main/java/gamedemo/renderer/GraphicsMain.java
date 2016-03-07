package gamedemo.renderer;

import gamedemo.GameLoop;
import gamedemo.model.Stone;
import processing.core.PApplet;

public class GraphicsMain extends PApplet {

	private static final int SIZE = 600;
	private static final double HEIGHT = 1000;
	private static final double G = 10.0;
	private static final int UPS = 30;

	private boolean simulate = false;

	private Stone s = new Stone(HEIGHT, -G / UPS / UPS);

	private final GameLoop loop = new GameLoop(UPS, null, new GameLoop.Callbacks() {
		public void update() {
			GraphicsMain.this.update();
		};

		public void render() {
			background(0);

			stroke(255);

			ellipse(SIZE / 2, SIZE - (float) (s.getHeight() * 0.9 * SIZE / HEIGHT + 0.05 * SIZE), SIZE * 0.1f,
					SIZE * 0.1f);
		}
	});

	@Override
	public void settings() {
		size(SIZE, SIZE);
	}

	public void setup() {
		background(0);
	}

	public void draw() {
		loop.throttleLoop();
	}

	public void update() {
		if (mousePressed && simulate == false && !s.isBottom()) {
			simulate = true;
		}

		if (!simulate) {
			return;
		}

		s.update();

		if (s.isBottom()) {
			simulate = false;
		}
	}

	public static void main(String[] args) {
		PApplet.main(new String[] { "--present", "gamedemo.renderer.GraphicsMain" });
	}
}
