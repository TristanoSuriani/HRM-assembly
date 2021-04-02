package nl.suriani.hrmasm.lib.cpu.chips;

public class BoxController {
	final StackUnit stackUnit;
	final Register16Bit pointer;

	public BoxController(short size, short offset) {
		stackUnit = new StackUnit(size);
		pointer = new Register16Bit();
		pointer.store(offset);
	}

	public short read(RAM ram) {
		short data = stackUnit.pop();
		ram.store(pointer.load(), data);
		pointer.store((short) (pointer.load() + 1));
		return data;
	}

	public void write(short[] data) {
		for (short i = 0; i < data.length; i += 1) {
			stackUnit.tail(data[i]);
		}
	}
}
