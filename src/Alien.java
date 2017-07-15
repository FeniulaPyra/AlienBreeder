import java.util.ArrayList;

public class Alien {
	private String name;
	private Breed mainBreed; //Head shape
	private String bColor; //head Color;
	private String bPattern; //head pattern;
	private String bPatColor; //head pattern color
	private int value;
	private int strength;
	private int intelligence;
	
	//*~~~CONSTRUCTORS~~~*\\
	/**
	 * Generates an empty Alien
	 */
	public Alien() {
		name = bColor = bPattern = bPatColor =  "";
		value = strength = intelligence = 0;
		mainBreed = null;
	}
	/**
	 * Generates an Alien given specifications
	 * @param inB the Breed or head shape of the Alien
	 * @param inBC the main Color of the head
	 * @param inBP the Pattern of the Alien's head
	 * @param inBPC the Pattern's Color of the Alien's head
	 */
	public Alien(Breed inB, String inBC, String inBP, String inBPC) {
		mainBreed = inB;
		bColor = inBC;
		bPattern = inBP;
		bPatColor = inBPC;
		name = bColor + " " + bPatColor + " " + bPattern + " " + mainBreed.getName();
		strength = (mainBreed.getLevel() * 10) + ((int)(Math.random() * mainBreed.getLevel() - (mainBreed.getLevel() * 2)));
		intelligence = (mainBreed.getLevel() * 10) + ((int)(Math.random() * mainBreed.getLevel() - (mainBreed.getLevel() * 2)));
		value = strength + intelligence + mainBreed.getBaseVal();
	}
	/**
	 * Generates a random Alien based off of the player's level
	 * @param inLvl the player's level
	 */
	public Alien(int inLvl) {
		int startSect;
		int endSect;
		for(startSect = -1; startSect < General.BREEDS.length - 1 && General.BREEDS[startSect + 1].getLevel() != inLvl; startSect++) {
			System.out.println(General.BREEDS[startSect + 1].getLevel());
		}
		for(endSect = General.BREEDS.length; endSect > startSect && General.BREEDS[endSect - 1].getLevel() != inLvl; endSect--) {}
		int randomAlienID = (int)(Math.random() * (endSect - startSect + 1) + startSect);
		mainBreed = General.BREEDS[randomAlienID];
		bColor =  General.B_COLORS[(int)(Math.random() * General.B_COLORS.length)];
		bPattern = General.B_PATTERNS[(int)(Math.random() * General.B_PATTERNS.length)];
		bPatColor = General.B_PAT_COLORS[(int)(Math.random() * General.B_PAT_COLORS.length)];
		strength = (mainBreed.getLevel() * 10) + ((int)(Math.random() * mainBreed.getLevel() - (mainBreed.getLevel() * 2)));
		intelligence = (mainBreed.getLevel() * 10) + ((int)(Math.random() * mainBreed.getLevel() - (mainBreed.getLevel() * 2)));
		value = strength + intelligence + mainBreed.getBaseVal();
		name = bColor + " " + bPatColor + " " + bPattern + " " + mainBreed.getName();
	}
	
	//*~~~SETTERS~~~*\\
	public void setName(String inName) {
		name = inName;
	}
	public void setBreed(Breed inB) {
		mainBreed = inB;
	}
	public void setBreedColor(String inBC) {
		bColor = inBC;
	}
	public void setBreedPattern(String inBP) {
		bPattern = inBP;
	}
	public void setBreedPatternColor(String inBPC) {
		bPatColor = inBPC;
	}
	
	//*~~~GETTERS~~~*\\
	public String getName() {
		return name;
	}
	public Breed getBreed() {
		return mainBreed;
	}
	public String getBreedColor() {
		return bColor;
	}
	public String getBreedPattern() {
		return bPattern;
	}
	public String getBreedPatternColor() {
		return bPatColor;
	}
	public int getValue() {
		return value;
	}
	public int getStrength() {
		return strength;
	}
	public int getInt() {
		return intelligence;
	}
	
	//*~~~OTHER~~~*\\
	
	public ArrayList<Alien> generatePotentialOffspring(Alien mate) {
		
		
		ArrayList<Alien> offspring = new ArrayList<Alien>();
		
		for(int i = 0; i < 16; i++) {
			//this for loop uses a binary number to count up to 15 from zero. Each place in the four-digit binary number is essentially a boolean representing
			//one of the traits of an alien. A 0 signifies that the child will have the kind of trait of the first parent, and a 1 signifies that the child will
			//have the trait of the second parent
			String traits = Integer.toBinaryString(i);
			
			//the potential child alien
			Alien toAdd = new Alien();
			
			//checks the traits of the possible alien child
			//breed
			if(traits.charAt(0) == '0') {
				toAdd.setBreed(mainBreed);
			}
			else {
				toAdd.setBreed(mate.getBreed());
			}
			
			//main color
			if(traits.charAt(1) == '0') {
				toAdd.setBreedColor(bColor);
			}
			else {
				toAdd.setBreedColor(mate.getBreedColor());
			}
			
			//pattern
			if(traits.charAt(2) == '0') {
				toAdd.setBreedPattern(bPattern);
			}
			else {
				toAdd.setBreedPattern(mate.getBreedPattern());
			}
			
			//pattern color
			if(traits.charAt(3) == '0') {
				toAdd.setBreedPatternColor(bPatColor);
			}
			else {
				toAdd.setBreedPatternColor(mate.getBreedPatternColor());
			}
			
			//checks to make sure the program doesn't show the same possible child twice.
			if(!offspring.contains(toAdd)) {
				offspring.add(toAdd);
			}
		}
		
		return offspring;
		
		
		//in case the above thing doesn't actually work T.T Please God spare us
		/*offspring.add(new Alien(mainBreed, bColor, bPattern, bPatColor));
		offspring.add(new Alien(mainBreed, bColor, bPattern, mate.getBreedPatternColor()));
		offspring.add(new Alien(mainBreed, bColor, mate.getBreedPattern(), bPatColor));
		offspring.add(new Alien(mainBreed, mate.getBreedColor(), bPattern, bPatColor));
		offspring.add(new Alien(mate.getBreed(), bColor, bPattern, bPatColor));
		offspring.add(new Alien(mate.getBreed(), bColor, bPattern, mate.getBreedPatternColor()));
		offspring.add(new Alien(mate.getBreed(), bColor, mate.getBreedPattern(), bPatColor));
		offspring.add(new Alien(mate.getBreed(), mate.getBreedColor(), bPattern, bPatColor));
		offspring.add(new Alien(mate.getBreed(), mate.getBreedColor(), bPattern, mate.getBreedPatternColor()));
		offspring.add(new Alien(mate.getBreed(), mate.getBreedColor(), mate.getBreedPattern(), bPatColor));
		offspring.add(new Alien(mate.getBreed(), mate.getBreedColor(), mate.getBreedPattern(), mate.getBreedPatternColor()));
		offspring.addAll(c)*/
		
	}
	public boolean equals(Alien otherAlien) {
		return (otherAlien.getBreed() == mainBreed && otherAlien.getBreedColor() == bColor && otherAlien.getBreedPattern() == bPattern && otherAlien.getBreedPatternColor() == bPatColor);
	}
	public String toString() {
		return (name
				+ "\n\t Type: " + bColor + " " + bPatColor + " " + bPattern + " " + mainBreed.getName()
				+ "\n\t Value: " + value
				+ "\n\t Intelligence: " + intelligence
				+ "\n\t Strength: " + strength);
	}
	
}
