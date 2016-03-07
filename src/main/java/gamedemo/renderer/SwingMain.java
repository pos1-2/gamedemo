package gamedemo.renderer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import javax.swing.JWindow;

import gamedemo.GameLoop;
import gamedemo.model.Stone;

public class SwingMain {
	private static final int UPS = 30;
	private static final double HEIGHT = 1000.; // in meters
	private static final double G = 10.; // free fall acceleration

	private static Stone s;

	public static void main(String[] args) {
		JWindow f = new JWindow();
		f.setIgnoreRepaint(true);

		f.setLocation(100, 100);
		f.setPreferredSize(new Dimension(640, 480));
		f.pack();

		f.setVisible(true);

		f.createBufferStrategy(2);

		BufferStrategy b = f.getBufferStrategy();

		s = new Stone(HEIGHT, -G / UPS / UPS);

		System.out.println("dropping the stone from " + HEIGHT + " meters...");

		// capping at 60 fps seems to run more smoothly on my computer
		GameLoop loop = new GameLoop(UPS, 60, new GameLoop.Callbacks() {
			@Override
			public void update() {
				s.update();
			}

			public void render() {
				do {
					do {
						Graphics g = b.getDrawGraphics();
						SwingMain.render(g);
						g.dispose();
					} while (b.contentsRestored());
					b.show();
				} while (b.contentsLost());
				// this is needed to propagate update to window manager?
				// animation seems laggy otherwise
				Toolkit.getDefaultToolkit().sync();
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

		System.exit(0);
	}

	private static void render(Graphics g) {
		Rectangle bounds = new Rectangle(0, 0, 640, 480);

		double x = bounds.getCenterX();
		double y = bounds.getHeight() - (s.getHeight() / HEIGHT * bounds.getHeight() * 0.9) + bounds.getMinY()
				- bounds.getHeight() * 0.05;

		g.clearRect(bounds.x, bounds.y, bounds.width, bounds.height);

		g.fillOval((int) x - 10, (int) y - 10, 20, 20);
	}
}
