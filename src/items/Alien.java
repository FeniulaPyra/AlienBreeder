package items;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import general.*;

public class Alien extends InventoryItem {
	private Breed mainBreed; //Head shape
	private String bColor; //head Color;
	private String bPattern; //head pattern;
	private String bPatColor; //head pattern color
	private int strength;
	private int intelligence;
	
	//*~~~CONSTRUCTORS~~~*\\
	/**
	 * Generates an empty Alien
	 */
	public Alien() {
		this(General.BREEDS[0], "", "", "");
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
		strength = (mainBreed.getLevel() * 10) + ((int)(Math.random() * (mainBreed.getLevel() - (mainBreed.getLevel() * 2))));
		intelligence = (mainBreed.getLevel() * 10) + ((int)(Math.random() * (mainBreed.getLevel() - (mainBreed.getLevel() * 2))));
		value = strength + intelligence + mainBreed.getBaseVal();
		itemType = 'a';
	}
	//copy constructor for aliens
	public Alien(Alien copyAlien) {
		this(copyAlien.getBreed(), copyAlien.getBreedColor(), copyAlien.getBreedPattern(), copyAlien.getBreedPatternColor());
	}
	//creates an alien based on lvl
	public Alien(int inLvl) {
		this(General.getRandAlien(inLvl));
	}
	
	//*~~~SETTERS~~~*\\
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
	public void setStrength(int inStrength) {
		strength = inStrength;
	}
	public void setIntel(int inIntel) {
		intelligence = inIntel;
	}
	
	//*~~~GETTERS~~~*\\
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
	public int getStrength() {
		return strength;
	}
	public int getIntel() {
		return intelligence;
	}
	
	//*~~~OTHER~~~*\\
	
	//generates possible offspring between this and another alien
	public ArrayList<Alien> generatePotentialOffspring(Alien mate) {
		
		boolean hasAlien = false;
		ArrayList<Alien> offspring = new ArrayList<Alien>();
		
		Breed newBreed;
		String breedColor;
		String pattern;
		String patternColor;
		
		for(int i = 0; i < 16; i++) {
			//this for loop uses a binary number to count up to 15 from zero. Each place in the four-digit binary number is essentially a boolean representing
			//one of the traits of an alien. A 0 signifies that the child will have the kind of trait of the first parent, and a 1 signifies that the child will
			//have the trait of the second parent
			//adds 000 at end in case of integer requiring less than 4 characters to form.
			String traits = Integer.toBinaryString(i) + "000";
			
			//checks the traits of the possible alien child
			//breed
			if(traits.charAt(0) == '0') {
				newBreed = mainBreed;
			}
			else {
				newBreed = mate.getBreed();
			}
			
			//main color
			if(traits.charAt(1) == '0') {
				breedColor = bColor;
			}
			else {
				breedColor = mate.getBreedColor();
			}
			
			//pattern
			if(traits.charAt(2) == '0') {
				pattern = bPattern;
			}
			else {
				pattern = mate.getBreedPattern();
			}
			
			//pattern color
			if(traits.charAt(3) == '0') {
				patternColor = bPatColor;
			}
			else {
				patternColor = mate.getBreedPatternColor();
			}
			
			Alien toAdd = new Alien(newBreed, breedColor, pattern, patternColor);
			//checks to make sure the program doesn't show the same possible child twice.
			for(int q = 0; q < offspring.size(); q++) {
				if(toAdd.equals(offspring.get(q))) {
					hasAlien = true;
				}
			}
			if(!hasAlien) {
				offspring.add(toAdd);
			}
			hasAlien = false;
		}
		
		return offspring;
	}
	
	//overrides equals to check name
	public boolean equals(Alien otherAlien) {
		return (this.toString().equals(otherAlien.toString()));
	}
	//overrides haschcode to return the id of the alien. 
	public int hashCode() {
		int cID = Arrays.asList(General.B_COLORS).indexOf(bColor);
		int pID = Arrays.asList(General.B_PATTERNS).indexOf(bPattern);
		int pcID = Arrays.asList(General.B_PAT_COLORS).indexOf(bPatColor);
		int bID = Arrays.asList(General.BREEDS).indexOf(mainBreed);
		
		return (bID * 810) + (cID * 81) + (pID * 9) + (pcID * 1) + 1;
	}
	//debugging stuff. outputs all info for alien.
	public String output() {
		return (name
				+ "\n\t Type: " + bColor + " " + bPatColor + " " + bPattern + " " + mainBreed.getName()
				+ "\n\t Value: " + value
				+ "\n\t Intelligence: " + intelligence
				+ "\n\t Strength: " + strength);
	}
	//overrides tostring to output name only
	public String toString() {
		return bColor + " " + bPatColor + " " + bPattern + " " + mainBreed.getName();
	}
	//calculates the alien's value
	public void recalculateVal() {
		value = strength + intelligence + mainBreed.getBaseVal();
	}
	
}
