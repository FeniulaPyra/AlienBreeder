//TODO base values
public class Breed {
	private String name;
	private int level;
	private int baseVal;
	
	public Breed() {
		name = "";
		level = 0;
	}
	public Breed(String inName, int inLvl) {
		name = inName;
		level = inLvl;
		baseVal = (int)((2.5 * Math.pow(level, 2)) + (12.5 * level) + 15);
	}

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
	public boolean equals(Breed otherBreed) {
		return name.equals(otherBreed.getName());
	}
}
