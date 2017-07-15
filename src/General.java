
public abstract class General {
	public static final Breed[] BREEDS = {
			new Breed("Inockei", 1), 
			new Breed("Lezynii", 2), new Breed("Uhuquas", 2),
			new Breed("Viishika", 3), new Breed("Chourov", 3), new Breed("Geivol", 3),
			new Breed("Dugosy", 4), new Breed("Xaesyt", 4), new Breed("Eixox", 4),
			new Breed("Weazo", 5), new Breed("Jichayrei", 5), new Breed("Leith", 5),
			new Breed("Pheis", 6), new Breed("Tatoul", 6), new Breed("Ghokeyli", 6)};
	//Value Equation = 2.5L^2 + 12.5L + 15
	public static final String[] B_COLORS = {"Black", "White", "Red", "Brown", "Orange", "Yellow", "Green", "Blue", "Violet", "Rose"};
	public static final String[] B_PATTERNS = {"Tayn", "Zaye", "Yetathi", "Gehour", "Eyloyle", "Ireimo", "Hyiju", "Kaeque", "Aetachi"};
	public static final String[] B_PAT_COLORS = {"Slate", "Silver", "Salmon", "Chestnut", "Mango", "Gold", "Oceana", "Fuschia", "Magenta"};
	//public final String[] T_COLORS = {};
	//public final String[] H_MATERIAL = {};
	
	//for breeding aliens with better strength/intel, breeding two aliens with strength/intel that is within 2 units of eachother's strength/intel can result with a child
	//that has a strength/intel that is 0-2 higher than the alien parent with the lowest strength/intel.
	//EX) if Alien A has 37 intel, and Alien B has 36 intel, the child could have 36, 37, or 38 intel. if Alien B had 35 intel, the child could have 35, 36, or 37 intel.
	//The same works for strength
	
	//Value = s + i + breed base value
	//Strength = t +-/*^%!level || random per alien instance (smallest is 10)
	//intelligence = h +-/*^%!level || random per alien instance (smallest is 10)
	//exp will be increased by 
	
	
	public static final String[] ARTIFACT_TYPES = {"Totem", "Bowl", "Urn", "Picture", "Tools", "Toy", "Instrument", "Headdress", "Object", ""};
	public static final String[] ARTIFACT_MATERIALS = {"Cardboard", "Glass", "Bronze", "Silver", "Gold", "Crystal"};
	/* Artifacts:
	 * These are collectables that can be bought in the shop. The name of an artifact will be in the following form: [Material] [Alien Type] [Artifact Type]
	 * For example, you may have a Glass Weazo Picture, or a Cardboard Chourov Toy.
	 */
}
