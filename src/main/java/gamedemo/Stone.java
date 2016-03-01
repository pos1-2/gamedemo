package gamedemo;

public class Stone {
	private double height;
	private double vel;
	private double accel;

	public Stone(double height, double accel) {
		this.height = height;
		this.accel = accel;
		this.vel = 0;
	}

	public boolean isBottom() {
		return this.height == 0;
	}

	public void update() {
		vel += accel;
		height += vel;
		if (height < 0) {
			height = 0;
		}
	}

	public double getHeight() {
		return height;
	}
}
