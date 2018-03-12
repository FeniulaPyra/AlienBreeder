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
		for(i = 0; i < General.ARTIFACT_MATERIALS.length && !General.ARTIFACT_MATERIALS[i].equalsIgnoreCase(material); i++) {}
		material = General.ARTIFACT_MATERIALS[i];
		for(i = 0; i < General.ARTIFACT_TYPES.length && !General.ARTIFACT_TYPES[i].equalsIgnoreCase(object); i++) {}
		object = General.ARTIFACT_TYPES[i - 1];
		level = inAlien.getLevel();
		value = inAlien.getBaseVal() + ((i - 1) * 10);
		name = inAlien.getName() + " " + material + " " + object;
		itemType = 'r';
	}
	/*public Artifact(int inLvl) {
		try {
			ResultSet arts = General.mainCon.createStatement().executeQuery("SELECT * FROM artifacts, breeds where breeds.level = " + inLvl + " AND artifacts.breed = breeds.id ORDER BY RANDOM();");
			for(int i = 0; i < General.BREEDS.length; i++) {
				if(General.BREEDS[i].getName().equals(arts.getString("breed"))) {
					alienType = General.BREEDS[i];
				}
			}
			name = alienType.getName() + " " + arts.getString("material") + " " + arts.getString("object");
		} catch (SQLException e) {
			System.out.println("SQL Exception when getting random artifact!");
		}
	}*/
	//TODO make this a this();
	public Artifact(int inLvl) {
		int startSect;
		int endSect;
		
		//finds the start sections
		for(startSect = -1; startSect < General.BREEDS.length - 1 && General.BREEDS[startSect + 1].getLevel() != inLvl; startSect++) {}
		
		//finds the end section
		for(endSect = General.BREEDS.length; endSect > startSect && General.BREEDS[endSect - 1].getLevel() != inLvl; endSect--) {}

		//gets the random alien
		alienType = General.BREEDS[(int)(Math.random() * (endSect - startSect) + startSect)];
		name = alienType.getName() + " " + General.ARTIFACT_MATERIALS[(int)(Math.random() * General.ARTIFACT_MATERIALS.length)] + " " + General.ARTIFACT_TYPES[(int)(Math.random() * General.ARTIFACT_MATERIALS.length)];
		itemType = 'r';
		level = inLvl;
		value = alienType.getBaseVal() + (( - 1) * 10);
	}
	
	//*~~~GETTERS~~~*\\
	public int getLevel() {
		return level;
	}
}
