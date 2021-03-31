package nl.suriani.hrmasm.lib.cpu;

public class StackUnit {
	private final Register16Bit[] units;
	private final Register16Bit pointer;

	public StackUnit(short size) {
		units = new Register16Bit[size];
		for (short i = 0; i < size; i += 1) {
			units[i] = new Register16Bit();
		}
		pointer = new Register16Bit();
		pointer.store((short) -1);
	}

	public void tail(short value) {
		pointer.store((short) (pointer.load() + 1));
		units[pointer.load()].store(value);
	}

	public void push(short value) {
		pointer.store((short) (pointer.load() + 1));
		shitfRight();
		units[0].store(value);
	}

	private void shitfRight() {
		for (int i = units.length - 1; i > 0; i -= 1) {
			units[i].store(units[i - 1].load());
		}
	}

	private void shiftLeft() {
		for (int i = 0; i < units.length - 1; i += 1) {
			units[i].store(units[i + 1].load());
		}
	}

	public short pop() {
		short value = units[0].load();
		pointer.store((short) (pointer.load() - 1));
		shiftLeft();
		return value;
	}
}
