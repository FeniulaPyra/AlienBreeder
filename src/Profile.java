import java.io.Serializable;
import java.util.*;

public class Profile implements Serializable{
	ArrayList<Alien> aliens; //probably not sorted, maybe TODO?
	Object[] pocket; //also not sorted, but that's ok
	int exp;
	int level;
	int coins;
	int id;
	
	public Profile() {
		this(new ArrayList<Alien>(), 0, 1, 100);
	}
	
	public Profile(ArrayList<Alien> inAliens, int inExp, int inLvl, int inCoins) {
		aliens = inAliens;
		exp = inExp;
		level = inLvl;
		coins = inCoins;
		pocket = new Object[10];
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
	public void sortAliens() {
		//TODO do the thing
	}
}
