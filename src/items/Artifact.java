package items;
import java.sql.ResultSet;
import java.sql.SQLException;

import general.General;

public class Artifact extends InventoryItem {
	private Breed alienType;
	private int level;
	
	//Creates an Artifact given the alien type, material, and kind of object
	public Artifact(Breed inAlien, String material, String object) {
		int i;
		for(i = 0; i < General.ARTIFACT_TYPES.length && !General.ARTIFACT_TYPES[i].equalsIgnoreCase(object); i++) {}
		level = inAlien.getLevel();
		value = inAlien.getBaseVal() + ((i - 1) * 10);
		name = inAlien.getName() + " " + material + " " + object;
		alienType = inAlien;
		itemType = 'r';
	}
	public Artifact(Artifact copyArtifact) {
		alienType = copyArtifact.getBreed();
		level = alienType.getLevel();
		value = copyArtifact.getValue();
		name = copyArtifact.getName();
		itemType = 'r';
	}
	public Artifact(int inLvl) {
		this(General.getRandArtifact(inLvl));
	}
	
	//*~~~GETTERS~~~*\\
	public int getLevel() {
		return level;
	}
	public Breed getBreed() {
		return alienType;
	}
	public String getMaterial() {
		return name.substring(name.indexOf(' ') - 1, name.lastIndexOf(' '));
	}
	public String getObject() {
		return name.substring(name.lastIndexOf(' ') - 1);
	}
}
