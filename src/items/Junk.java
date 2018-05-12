package items;

import general.General;

public class Junk extends InventoryItem {
	
	//creates a junk given name and value;
	public Junk(String inName, int inValue) {
		name = inName;
		value = inValue;
		itemType = 'j';
	}
	//creates a random junk
	public Junk() {
		this(General.JUNK_TYPES[(int)(Math.random() * General.JUNK_TYPES.length)], (int)(Math.random() * General.JUNK_TYPES.length));
	}
}
