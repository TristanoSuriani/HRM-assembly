package nl.suriani.hrmasm.lib.cpu.chips;

public class RAM {
	private final Register16Bit[] units;

	public RAM(short size) {
		units = new Register16Bit[size];
		for (short i = 0; i < size; i += 1) {
			units[i] = new Register16Bit();
		}
	}

	public short load(short address) {
		return units[address].load();
	}

	public void store(short address, short value) {
		units[address].store(value);
	}
}
