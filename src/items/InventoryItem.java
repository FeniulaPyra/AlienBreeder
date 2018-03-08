package items;

public abstract class InventoryItem {
	String name;
	char itemType;
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
	public char getItemType() {
		return itemType;
	}
	public int getValue() {
		return value;
	}
	public String toString() {
		return name;
	}
}
