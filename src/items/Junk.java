package items;

import general.General;

public class Junk extends InventoryItem {
	
	public Junk(String inName, int inValue) {
		name = inName;
		value = inValue;
		itemType = 'j';
	}
	public Junk() {
		this(General.JUNK_TYPES[(int)(Math.random() * General.JUNK_TYPES.length)], (int)(Math.random() * General.JUNK_TYPES.length));
	}
}
