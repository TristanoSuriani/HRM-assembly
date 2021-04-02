package nl.suriani.hrmasm.lib.cpu.chips;

public class ALU {
	private final Register16Bit a;
	private final Register16Bit b;
	private final Register16Bit sum;
	private final Register16Bit sub;
	private final Register16Bit mul;
	private final Register16Bit div;
	private final Register16Bit mod;

	public ALU() {
		a = new Register16Bit();
		b = new Register16Bit();
		sum = new Register16Bit();
		sub = new Register16Bit();
		mul = new Register16Bit();
		div = new Register16Bit();
		mod = new Register16Bit();
	}

	public void store(short a, short b) {
		this.a.store(a);
		this.b.store(b);
		this.sum.store((short) (a + b));
		this.sub.store((short) (a - b));
		this.mul.store((short) (a * b));
		if (b != 0) {
			this.div.store((short) (a / b));
			this.mod.store((short) (a % b));
		}
	}

	public short loadSum() {
		return sum.load();
	}

	public short loadSub() {
		return sub.load();
	}

	public short loadMul() {
		return mul.load();
	}

	public short loadDiv() {
		return div.load();
	}

	public short loadMod() {
		return mod.load();
	}
}
