package nl.suriani.hrmasm.lib.cpu.chips;

public class ROM {
	private final Register16Bit[] units;

	public ROM(short size) {
		units = new Register16Bit[size];
		reset();
	}

	public void flash(short[] instructions) {
		reset();
		for (short i = 0; i < units.length && i < instructions.length; i += 1) {
			units[i].store(instructions[i]);
		}
	}

	public short load(short address) {
		return units[address].load();
	}

	private void reset() {
		for (short i = 0; i < units.length; i += 1) {
			units[i] = new Register16Bit();
		}
	}
}
