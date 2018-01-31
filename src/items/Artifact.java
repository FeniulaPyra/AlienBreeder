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
	}
	public Artifact(int inLvl) {
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
	}
}
