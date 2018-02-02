package general;

import java.io.Serializable;
import java.util.*;
import items.*;

public class Profile implements Serializable{
	public ArrayList<Alien> aliens; //probably not sorted, maybe TODO?
	public ArrayList<Artifact> artifacts;
	public ArrayList<Junk> other;
	public int pocket; //also not sorted, but that's ok
	private int exp;
	private int level;
	private int coins;
	private int id;
	
	public Profile() {
		this(new ArrayList<Alien>(), 0, 1, 100);
	}
	
	public Profile(ArrayList<Alien> inAliens, int inExp, int inLvl, int inCoins) {
		aliens = inAliens;
		exp = inExp;
		level = inLvl;
		coins = inCoins;
		pocket = 10;
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
	
	//NOTE these methods might seem redundant, because the arrays are public, but they aren't redundant, because they sort stuff.
	//NOTE there's probably too many anyways but meh
	
	//Adds individual things
	public void add(Alien toAdd) {
		if(!aliens.isEmpty()) {
			int i;
			for(i = aliens.size() - 1; i >= 0 && aliens.get(i).getName().compareTo(toAdd.getName()) > 0; i--) {}
			aliens.add(i + 1, toAdd);
		} else {
			aliens.add(toAdd);
		}
		System.out.println(toAdd);
	}
	public void add(Artifact toAdd) {
		if(!artifacts.isEmpty()) {
			int i;
			for(i = artifacts.size() - 1; i >= 0 && artifacts.get(i).getName().compareTo(toAdd.getName()) > 0; i--) {}
			artifacts.add(i, toAdd);
		} else {
			artifacts.add(toAdd);
		}
	}
	public void add(Junk toAdd) {
		if(!other.isEmpty()) {
			int i;
			for(i = other.size() - 1; i >= 0 && other.get(i).getName().compareTo(toAdd.getName()) > 0; i--) {}
			other.add(i, toAdd);
		} else {
			other.add(toAdd);
		}
	}
	
	//Adds many things
	public void addAliens(ArrayList<Alien> toAdd) {
		for(int i = 0; i < toAdd.size(); i++) {
			aliens.add(toAdd.get(i));
		}
	}
	public void addArtifacts(ArrayList<Artifact> toAdd) {
		for(int i = artifacts.size(); i < toAdd.size(); i--) {
			artifacts.add(toAdd.get(i));
		}
	}
	public void addJunks(ArrayList<Junk> toAdd) {
		for(int i = 0; i < toAdd.size(); i++) {
			other.add(toAdd.get(i));
		}
	}
	public void addMany(ArrayList<InventoryItem> toAdd) {
		for(int i = 0; i < toAdd.size(); i++) {
			switch(toAdd.get(i).getClass().getName().charAt(1)) {
				case 'l':
					add((Alien)toAdd.get(i));
					break;
				case 'r':
					add((Artifact)toAdd.get(i));
					break;
				case 'u':
					add((Junk)toAdd.get(i));
					break;
				default:
					add((Junk)toAdd.get(i));
					break;
			}
		}
	}
	
	public void checkLevelUp() {
		if(exp >= Math.pow(level * 10, 2)) {
			exp = -(int)(Math.pow(level * 10, 2));
			level++;
		}
	}
	public int getMaxExp() {
		return (int)Math.pow(level * 10, 2);
	}
}
