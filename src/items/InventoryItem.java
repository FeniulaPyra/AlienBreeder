package items;

import java.io.Serializable;

public abstract class InventoryItem implements Serializable {
	String name;
	//tells whether the item is an alien, artifact, or junk.
	char itemType;
	int value;
	
	//*~~~SETTERS~~~*\\
	public void setName(String inName) {
		name = inName;
	}
	public void setValue(int inValue) {
		value = inValue;
	}
	
	//*~~~GETTERS~~~*\\
	public String getName() {
		return name;
	}
	public char getItemType() {
		return itemType;
	}
	public int getValue() {
		return value;
	}
	
	//overides tostring
	public String toString() {
		return name;
	}
}
