package nl.suriani.hrmasm.lib.cpu.chips;

public class Register2Bit {
	private boolean bit1;
	private boolean bit2;
	
	public void store0b00() {
		bit1 = false;
		bit2 = false;
	}

	public void store0b01() {
		bit1 = false;
		bit2 = true;
	}

	public void store0b10() {
		bit1 = true;
		bit2 = false;
	}
	
	public void store0b11() {
		bit1 = true;
		bit2 = true;
	}
	
	public short load() {
		return (short) ( 2 * booleanToShort(bit1) + booleanToShort(bit2));
	}

	private short booleanToShort(boolean value) {
		return (short) (value ? 1 : 0);
	}
}
