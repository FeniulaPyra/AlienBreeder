package items;

public abstract class InventoryItem {
	String name;
	int value;
	
	public void setName(String inName) {
		name = inName;
	}
	public void setValue(int inValue) {
		value = inValue;
	}
	public String getName() {
		return name;
	}
	public int getValue() {
		return value;
	}
	public String toString() {
		return name;
	}
}
