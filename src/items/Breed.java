package items;

import java.io.Serializable;

//TODO switch to a InventoryItem?
public class Breed implements Serializable {
	private String name;
	private int level;
	private int baseVal;
	
	//*~~~CONSTRUCTORS~~~*\\
	
	//Creates an empty breed. Should never be used.
	public Breed() {
		name = "";
		level = 0;
	}
	//creates a breed based on name and level.
	public Breed(String inName, int inLvl) {
		name = inName;
		level = inLvl;
		baseVal = (int)((2.5 * Math.pow(level, 2)) + (12.5 * level) + 15);
	}

	//*~~~GETTERS~~~*\\
	public String getName() {
		return name;
	}
	public int getBaseVal() {
		return baseVal;
	}
	public int getLevel() {
		return level;
	}
	
	//*~~~OTHER~~~*\\
	//overrides equal to check name
	public boolean equals(Breed otherBreed) {
		return name.equals(otherBreed.getName());
	}
	//overrides tostring
	public String toString() {
		return name;
	}
}
