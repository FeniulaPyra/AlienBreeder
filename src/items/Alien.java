package items;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
	
	public Alien(Alien copyAlien) {
		this(copyAlien.getBreed(), copyAlien.getBreedColor(), copyAlien.getBreedPattern(), copyAlien.getBreedPatternColor());
	}
	
	public Alien(int inLvl) {
		this(General.getRandAlien(inLvl));
	}
	
	/*public Alien(int inLvl) {
		//TODO
		try {
			ResultSet aliens = General.mainCon.createStatement().executeQuery("SELECT * FROM aliens, breeds where breeds.level = " + inLvl + " AND aliens.breed = breeds.id ORDER BY RANDOM();");
			for(int i = 0; i < General.BREEDS.length; i++) {
				if(General.BREEDS[i].getName().equals(aliens.getString("breed"))) {
					mainBreed = General.BREEDS[i];
				}
			}
			bColor = aliens.getString("base_color");
			bPattern = aliens.getString("pattern");
			bPatColor = aliens.getString("pattern_color");
		}
		catch(SQLException s) {
			System.out.println("SQL Exception when creating random alien!");
		}
		strength = (mainBreed.getLevel() * 10) + ((int)(Math.random() * mainBreed.getLevel() - (mainBreed.getLevel() * 2)));
		intelligence = (mainBreed.getLevel() * 10) + ((int)(Math.random() * mainBreed.getLevel() - (mainBreed.getLevel() * 2)));
	}*/
	/*
	 * Generates a random Alien based off of the player's level
	 * @param inLvl the player's level
	 */
	/*public Alien(int inLvl) {
		//TODO pull the random alien from the database rather than doing this painfulness
		//the indexes bookending the breeds with the same level as the player
		int startSect;
		int endSect;
		
		//finds the start sections
		for(startSect = -1; startSect < General.BREEDS.length - 1 && General.BREEDS[startSect + 1].getLevel() != inLvl; startSect++) {}
		
		//finds the end section
		for(endSect = General.BREEDS.length; endSect > startSect && General.BREEDS[endSect - 1].getLevel() != inLvl; endSect--) {}
		
		//gets the random alien
		int randomAlienID = (int)(Math.random() * (endSect - startSect) + startSect);
		
		//sets all of the breed/etc
		mainBreed = General.BREEDS[randomAlienID];
		bColor =  General.getRandomColor();
		bPattern = General.getRandom;
		bPatColor = General.B_PAT_COLORS[(int)(Math.random() * General.B_PAT_COLORS.length)];
		
		//randomizes numeric values
		strength = (mainBreed.getLevel() * 10) + ((int)(Math.random() * mainBreed.getLevel() - (mainBreed.getLevel() * 2)));
		intelligence = (mainBreed.getLevel() * 10) + ((int)(Math.random() * mainBreed.getLevel() - (mainBreed.getLevel() * 2)));
		value = strength + intelligence + mainBreed.getBaseVal();
		
		//sets name to breed/etc
		name = bColor + " " + bPatColor + " " + bPattern + " " + mainBreed.getName();
	}
	*/
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
	
	public ArrayList<Alien> generatePotentialOffspring(Alien mate) {
		
		boolean hasAlien = false;
		ArrayList<Alien> offspring = new ArrayList<Alien>();
		
		for(int i = 0; i < 16; i++) {
			//this for loop uses a binary number to count up to 15 from zero. Each place in the four-digit binary number is essentially a boolean representing
			//one of the traits of an alien. A 0 signifies that the child will have the kind of trait of the first parent, and a 1 signifies that the child will
			//have the trait of the second parent
			String traits = Integer.toBinaryString(i) + "000";
			
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
			
			name = bColor + " " + bPatColor + " " + bPattern + " " + mainBreed.getName();
			
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
	public boolean equals(Alien otherAlien) {
		return (otherAlien.getBreed().equals(mainBreed) && otherAlien.getBreedColor().equals(bColor) && otherAlien.getBreedPattern().equals(bPattern) && otherAlien.getBreedPatternColor().equals(bPatColor));
	}
	public String output() {
		return (name
				+ "\n\t Type: " + bColor + " " + bPatColor + " " + bPattern + " " + mainBreed.getName()
				+ "\n\t Value: " + value
				+ "\n\t Intelligence: " + intelligence
				+ "\n\t Strength: " + strength);
	}
	public String toString() {
		return bColor + " " + bPatColor + " " + bPattern + " " + mainBreed.getName();
	}
	public void recalculateVal() {
		value = strength + intelligence + mainBreed.getBaseVal();
	}
	
}
