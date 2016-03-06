package gamedemo;

public class GameLoop {
	private long nextRender;
	private long nextUpdate;

	private final int ups; // updates per second
	private final Integer fps; // frame per second
	private final long updateRate;
	private final long renderRate;

	public static class Callbacks {
		public void update() {
		}

		public void render() {
		}

		public boolean running() {
			return true;
		}
	}

	private final Callbacks callbacks;

	public GameLoop(int ups, Integer fps, Callbacks callbacks) {
		this.ups = ups;
		this.fps = fps;
		updateRate = Math.round(1000000000.0 / ups);
		renderRate = this.fps == null ? 0 : Math.round(1000000000.0 / fps);
		nextRender = System.nanoTime();
		nextUpdate = nextRender + updateRate;
		this.callbacks = callbacks;
	}

	public GameLoop(Callbacks callbacks) {
		this(30, null, callbacks);
	}

	private long t;

	private void throttleUpdate() {
		if (nextUpdate < t) {
			callbacks.update();
			nextUpdate += updateRate;
		}
	}

	private void throttleRender() {
		if (fps == null) {
			callbacks.render();
		} else if (nextRender < t) {
			nextRender += renderRate;

			callbacks.render();

			Thread.yield();
		}
	}

	public void loop() {
		while (callbacks.running()) {
			throttleLoop();
		}
	}

	public void throttleLoop() {
		t = System.nanoTime();

		throttleUpdate();
		throttleRender();
	}

	public Integer getFps() {
		return fps;
	}

	public int getUps() {
		return ups;
	}
}
