package nl.suriani.hrmasm.lib.cpu;

public class ALU {
	private final Register16Bit a;
	private final Register16Bit b;
	private final Register16Bit sum;
	private final Register16Bit sub;

	public ALU() {
		a = new Register16Bit();
		b = new Register16Bit();
		sum = new Register16Bit();
		sub = new Register16Bit();
	}

	public void store(short a, short b) {
		this.a.store(a);
		this.b.store(b);
		this.sum.store((short) (a + b));
		this.sub.store((short) (a - b));
	}

	public short loadSum() {
		return sum.load();
	}

	public short loadSub() {
		return sub.load();
	}
}
