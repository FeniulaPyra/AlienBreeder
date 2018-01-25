package items;
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
}
