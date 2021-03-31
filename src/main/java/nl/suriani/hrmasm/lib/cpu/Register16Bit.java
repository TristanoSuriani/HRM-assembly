package nl.suriani.hrmasm.lib.cpu;

public class Register16Bit {
	private short value;

	public short load() {
		return value;
	}

	public void store(short value) {
		this.value = value;
	}

	public void reset() {
		value = 0;
	}
}
