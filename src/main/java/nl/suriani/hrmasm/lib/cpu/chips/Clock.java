package nl.suriani.hrmasm.lib.cpu.chips;

public class Clock {
	private short frequency;

	public Clock(short frequency) {
		this.frequency = frequency;
	}

	public void tick() {
		try {
			Thread.sleep(1 / frequency);
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
