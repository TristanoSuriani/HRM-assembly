package nl.suriani.hrmasm.lib.cpu;

public class OutboxController {
	private StackUnit stackUnit;
	private Register16Bit pointer;

	public OutboxController(short size, short offset) {
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
}
