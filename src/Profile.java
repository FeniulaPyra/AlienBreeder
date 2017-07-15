import java.io.Serializable;
import java.util.*;

public class Profile implements Serializable{
	ArrayList<Alien> aliens;
	int exp;
	int level;
	int coins;
	
	public Profile(ArrayList<Alien> inAliens, int inExp, int inLvl, int inCoins) {
		aliens = inAliens;
		exp = inExp;
		level = inLvl;
		coins = inCoins;
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
}
