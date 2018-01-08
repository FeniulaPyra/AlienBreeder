
public class Junk {
	String name;
	int value;
	
	public Junk(String inName, int inValue) {
		name = inName;
		value = inValue;
	}
	public Junk() {
		this(General.JUNK_TYPES[(int)(Math.random() * General.JUNK_TYPES.length)], 0);
	}
	
}
