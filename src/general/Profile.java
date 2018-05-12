package general;

import java.io.Serializable;
import java.util.*;
import items.*;

public class Profile implements Serializable{
	//contains all of the aliens the user has
	public ArrayList<Alien> aliens;
	//contains all of the artifacts the user has
	public ArrayList<Artifact> artifacts;
	//contains all the junk the user has. Most of this should be sold by the user.
	public ArrayList<Junk> other;
	//the size of the pocket of the user.
	public int pocket;
	//experience points
	private int exp;
	//level. based on exp and controlls what aliens show up in the workplace/shop
	private int level;
	//currency
	private int coins;
	
	//creates an empty profile
	public Profile() {
		this(new ArrayList<Alien>(), new ArrayList<Artifact>(), new ArrayList<Junk>(), 0, 1, 100);
	}
	
	//creates a profile given all of the values. used when loading a profile from the file system.
	public Profile(ArrayList<Alien> inAliens, ArrayList<Artifact> inArts, ArrayList<Junk> inJunk, int inExp, int inLvl, int inCoins) {
		aliens = new ArrayList<Alien>();
		//this is for making sure the achievements are updated
		for(int i = 0; i < inAliens.size(); i++)
			add(inAliens.get(i));
		artifacts = new ArrayList<Artifact>();
		//this is for making sure the achievements are updated
		for(int i = 0; i < inArts.size(); i++)
			add(inArts.get(i));
		other = inJunk;
		exp = inExp;
		level = inLvl;
		coins = inCoins;
		pocket = 10;
	}
	
	//copy constructor. for loading a user
	public Profile(Profile inUser) {
		this(inUser.aliens, inUser.artifacts, inUser.other, inUser.exp, inUser.level, inUser.coins);
	}
	
	//*~~~SETTERS~~~*\\
	public void setExp(int inExp) {
		exp = inExp;
	}
	public void setLevel(int inLvl) {
		level = inLvl;
	}
	public void setCoins(int inCoins) {
		coins = inCoins;
	}
	
	//*~~~GETTERS*~~~\\
	public int getExp() {
		return exp;
	}
	public int getLevel() {
		return level;
	}
	public int getCoins() {
		return coins;
	}
	public int getPocket() {
		return pocket;
	}
	
	//*~~~ADDERS~~~*\\
	public void addExp(int toAdd) {
		exp += toAdd;
	}
	public void addLevel(int toAdd) {
		level += toAdd;
	}
	public void addCoins(int toAdd) {
		coins += toAdd;
	}
	
	//*~~~OTHER~~~*\\
	
	//sorts the various inventories of the user
	public void sort(ArrayList<InventoryItem> inArray) {
		int toCheck, index;

		//checks each element against everything else
		for(toCheck = 1; toCheck < inArray.size(); toCheck++) {
			for(index = toCheck - 1; index >= 0 && inArray.get(index).getName().compareTo(inArray.get(toCheck).getName()) > 0; index--) {
				inArray.add(index + 1, inArray.remove(index));
				index--;
			}
			toCheck = index + 1;
		}
	}
	
	//Adds individual things
	
	//adds an alien to the inventory
	public void add(Alien toAdd) {
		//this if just avoids index out of bounds (IOOB) exceptions.
		if(!aliens.isEmpty()) {
			int i;
			//finds the spot to put the alien (makes sure the inventory stays sorted!)
			for(i = aliens.size() - 1; i >= 0 && aliens.get(i).getName().compareTo(toAdd.getName()) > 0; i--) {}
			aliens.add(i + 1, toAdd);
		} else {
			aliens.add(toAdd);
		}
		
		//this tells the database that the user has gotten/bred this alien. For achievement purposes only
		General.achieve(toAdd);
	}
	
	//adds an artifact to the inventory
	public void add(Artifact toAdd) {
		//avoids IOOB exceptions
		if(!artifacts.isEmpty()) {
			int i;
			//keeps the art. inventory sorted
			for(i = artifacts.size() - 1; i >= 0 && artifacts.get(i).getName().compareTo(toAdd.getName()) > 0; i--) {}
			
			//avoids IOOB
			if(i < 0) 
				artifacts.add(toAdd);
			else 
				artifacts.add(i, toAdd);
		} else {
			artifacts.add(toAdd);
		}
		//achievement stuff
		General.achieve(toAdd);
	}
	//adds junk stuff (
	public void add(Junk toAdd) {
		//avoids ioob
		if(other.size() != 0) {
			int i;
			//for some reason, I decided that we should also sort junk.
			for(i = other.size() - 1; i >= 0 && other.get(i).getName().compareTo(toAdd.getName()) > 0; i--) {}
			
			//avoids IOOB
			if(i >= 0) {
				other.add(i, toAdd);
			}
			else {
				other.add(toAdd);
			}
		} else {
			other.add(toAdd);
		}
	}
	
	//Adds many things
	
	//for adding many aliens at once. Not generally used.
	public void addAliens(ArrayList<Alien> toAdd) {
		for(int i = 0; i < toAdd.size(); i++) {
			aliens.add(toAdd.get(i));
		}
	}
	//for adding many arts at once. Not generally used.
	public void addArtifacts(ArrayList<Artifact> toAdd) {
		for(int i = artifacts.size(); i < toAdd.size(); i--) {
			artifacts.add(toAdd.get(i));
		}
	}
	//for adding many junks at once. Not Generally used.
	public void addJunks(ArrayList<Junk> toAdd) {
		for(int i = 0; i < toAdd.size(); i++) {
			other.add(toAdd.get(i));
		}
	}
	//for adding a mish-mash of items at once. Used when leaving work tab.
	public void addMany(ArrayList<InventoryItem> toAdd) {
		for(int i = 0; i < toAdd.size(); i++) {
			//checks what kind of Inventory Item it is.
			switch(toAdd.get(i).getItemType()) {
			
				//if alien...
				case 'a':
					//...add to aliens
					add((Alien)toAdd.get(i));
					break;
				//if artifact...
				case 'r':
					//...add to arts
					add((Artifact)toAdd.get(i));
					break;
				//if junk...
				case 'j':
					//...add to junks
					add((Junk)toAdd.get(i));
					break;
				default:
					//shouldn't happen
					add((Junk)toAdd.get(i));
					break;
			}
		}
	}
	
	//checks if the user can level up from current exp
	public void checkLevelUp() {
		
		//checks if user has enough exp
		if(exp >= getMaxExp()) {
			//subtracts that amount of exp
			exp -= getMaxExp();
			//adds a level.
			level++;
			//this is in case the user got enough exp to level up multiple times in one go.
			checkLevelUp();
		}
	}
	//So I don't have to keep rewriting this.
	public int getMaxExp() {
		return (int)Math.pow(level * 10, 2);
	}
}
