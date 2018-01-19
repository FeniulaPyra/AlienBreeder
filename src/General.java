import java.sql.*;
//import org.sqlite.*;

//TODO handwrite through the damn loop stop being lazy
//TODO but theres like 8 loops
//TODO well you are stupid too bad
//TODO but plzno
//TODO howbout plsyes.
//TODO but no.
//TODO or suffer through 8 hours of trying to figure out your stupid loops through trial and error
//TODO but please.
//TODO but no.
//TODO DO THE LOOP
//TODO WAAAHH T^T


//TODO artifacts table screwed up
//TODO while the breeds start at 0 in the alien table (where Inockei breed = 0), the breeds table starts at 1 (where Inockei = 1). This needs to be fixed


/*
 * basic storyline: you are a worker on a research/exploration spaceship. Despite the scientific advances, the spaceship can not sort things it finds (dirt, rubble, rare things, aliens)
 * because all of the programmers went on strike before programming the sorting algorithm for an unknown amount of time. So, your job as a worker is to sort through all of the random 
 * stuff the spaceship finds. However, you also collect and breed the tiny aliens that the space crew would otherwise discard(ToT) or put through horrible science experiments. However,
 * some of the other workers have also started doing the same thing and will occasionally request certain aliens from you.
 */


public abstract class General {
	public static final Breed[] BREEDS = { //15
			new Breed("Inockei", 1), 
			new Breed("Lezynii", 2), new Breed("Uhuquas", 2),
			new Breed("Viishika", 3), new Breed("Chourov", 3), new Breed("Geivol", 3),
			new Breed("Dugosy", 4), new Breed("Xaesyt", 4), new Breed("Eixox", 4),
			new Breed("Weazo", 5), new Breed("Jichayrei", 5), new Breed("Leith", 5),
			new Breed("Pheis", 6), new Breed("Tatoul", 6), new Breed("Ghokeyli", 6),
			new Breed("Eaquozo", 7)};
	//Value Equation = 2.5L^2 + 12.5L + 15
	public static final String[] B_COLORS = {"Black", "White", "Red", "Brown", "Orange", "Yellow", "Green", "Blue", "Violet", "Rose"}; //10
	public static final String[] B_PATTERNS = {"Tayn", "Zaye", "Yetathi", "Gehour", "Eyloyle", "Ireimo", "Hyiju", "Kaeque", "Aetachi"}; //9
	public static final String[] B_PAT_COLORS = {"Slate", "Silver", "Salmon", "Chestnut", "Mango", "Gold", "Oceana", "Fuschia", "Magenta"}; //9
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
	
	
	public static final String[] ARTIFACT_TYPES = {"", "Totem", "Bowl", "Urn", "Picture", "Tools", "Toy", "Instrument", "Headdress", "Object"}; //10
	public static final String[] ARTIFACT_MATERIALS = {"Cardboard", "Glass", "Bronze", "Silver", "Gold", "Crystal"}; //6
	/* Artifacts:
	 * These are collectables that can be bought in the shop. The name of an artifact will be in the following form: [Material] [Alien Type] [Artifact Type]
	 * For example, you may have a Glass Weazo Picture, or a Cardboard Chourov Toy.
	 */
	
	public static final String[] JUNK_TYPES = {"JFrame", "Dirt", "Dead Flower", "Styrafoam cup", "Magical Spoon", "Old Bread", "Unidentifyable Glob", "Rusty Metal"};
	
	//SHOULD loop through the breeds array and add all of the breeds to the breeds table
	public static final void updateBreedTable(Connection inCon) {
		int lastB = 0;
		try {
			//Clears the table so there shouldn't be any duplicates
			ResultSet lastBreed = inCon.createStatement().executeQuery("SELECT * FROM breeds where id = (SELECT MAX(id) FROM breeds);");
			lastB = lastBreed.getInt("id") + 1;
		}
		catch (SQLException e) {
			//outputs possible errors
			System.out.println("You have an SQL error at getting the last breed: \n" + e);
		}
		
		//loops through the breeds array
		for(int b = lastB; b < BREEDS.length; b++) {
			try {
				//adds the current breed
				inCon.createStatement().executeUpdate("INSERT INTO breeds VALUES (" + b + ", \"" + BREEDS[b].getName() + "\", " + BREEDS[b].getLevel() + ", " + BREEDS[b].getBaseVal() + ");");
			}
			catch(SQLException e) {
				//outputs possible errors
				System.out.println("You have an SQL error at inserting the " + b + "th breed\n" + e);
			}
		}
		System.out.println("Breed Update Complete.");
	}
	
	//SHOULD loop through the breed value arrays and add all possible combinations of an alien into the aliens table in the databases. Hopefully this doesn't take long.
	public static final void updateAlienTable(Connection inCon) {
		//From here to the breeds loop will start the loop at the last alien entry
		//TODO cleanup
		
		int b = 0, bc = 0, bp = 0, bpc = 0, lastB = 0;
		String lastBC = "", lastBP = "", lastBPC = "";
		int tempB = 0, tempBC = 0, tempBP = 0, tempBPC = 0;
		
		//gets the last alien entry from the database and separates the values into indiv vars
		try {
			ResultSet lastAlien = inCon.createStatement().executeQuery("SELECT * FROM aliens WHERE id = (SELECT MAX(id) FROM aliens);");
			lastB = lastAlien.getInt("breed");
			lastBC = lastAlien.getString("color");
			lastBP = lastAlien.getString("pattern");
			lastBPC = lastAlien.getString("pattern_color");
		}
		catch(SQLException s) {
			System.out.println("SQL Error at getting the last alien: " + s);
		}
		catch(Exception e) {
			System.out.println("Error at getting the last alien: " + e);
		}
		
		//sets the breed loop start
		b = lastB;
		
		//sets the color loop start
		for(int i = 0; i < B_COLORS.length; i++) {
			if(B_COLORS[i].equalsIgnoreCase(lastBC)) {
				bc = i;
				i += B_COLORS.length;
			}
		}
		
		//sets the pattern loop start
		for(int i = 0; i < B_PATTERNS.length; i++) {
			if(B_PATTERNS[i].equalsIgnoreCase(lastBP)) {
				bp = i;
				i += B_PATTERNS.length;
			}
		}
		
		//sets the pattern color loop start
		for(int i = 0; i < B_PAT_COLORS.length; i++) {
			if(B_PAT_COLORS[i].equalsIgnoreCase(lastBPC)) {
				bpc = i + 1;
				i += B_PAT_COLORS.length;
			}
		}
		
		//loops through breeds
		for(; b < BREEDS.length; b++) {
			
			//loops through the colors
			for(; bc < B_COLORS.length; bc++) {
				
				//loops through the patterns
				for(; bp < B_PATTERNS.length; bp++) {
					
					//loops through the pattern colors
					for(; bpc < B_PAT_COLORS.length; bpc++) {
						
						//System.out.println("b: " + b + "bc: " + bc + "bp: " + bp + "bpc: " + bpc);
						try {
							//adds the current breed combination to the table
							inCon.createStatement().executeUpdate("INSERT INTO aliens (pattern, pattern_color, color, breed) VALUES (\"" + B_PATTERNS[bp] + "\", \"" + B_PAT_COLORS[bpc] + "\", \"" + B_COLORS[bc] + "\", " + b + ");");
							if((b+bc+bp+bpc) % 100 == 0) {
								System.out.println((((b+bc+bp+bpc)/(double)(BREEDS.length * B_COLORS.length * B_PATTERNS.length * B_PAT_COLORS.length)) * 100) + "%");
							}
						}
						catch(SQLException e) {
							//ouputs possible errors
							System.out.println("You have a SQL error at inserting the alien: \nb = " + b + "\nbc = " + bc + "bp = " + bp + "\nbpc = " + bpc);
							System.out.println(e);
							return;
						}
					}
					bpc = 0;
					
				}
				bp = 0;
			}
			bc = 0;
		}
		b = 0;
		System.out.println("Alien Update Complete.");
	}
	
	//SHOULD loop through the array of artifact values and add artifacts to the artifact table
	public static final void updateArtifactTable(Connection inCon) {
		int lastB = 0;
		String lastT = "", lastM = "";
		int t = 0, b = 0, m = 0;
		
		
		try {
			ResultSet lastArt = inCon.createStatement().executeQuery("SELECT * FROM artifacts where id = (SELECT MAX(id) FROM artifacts);");
			lastB = lastArt.getInt("breed");
			lastT = lastArt.getString("type");
			lastM = lastArt.getString("material");
		}
		catch(SQLException e) {
			//ouputs errors
			System.out.println("You have an SQL error at getting the last artifact:\n" + e);
		}
		
		b = lastB;
		
		//loops through and finds the index of the last type
		for(int i = 0; i < ARTIFACT_TYPES.length; i++) {
			if(ARTIFACT_TYPES[i].equalsIgnoreCase(lastT)) {
				t = i;
				i += ARTIFACT_TYPES.length;
			}
		}
		
		//loops through and finds the index of the last material
		for(int i = 0; i < ARTIFACT_MATERIALS.length; i++) {
			if(ARTIFACT_MATERIALS[i].equalsIgnoreCase(lastM)) {
				m = i + 1;
				i += ARTIFACT_MATERIALS.length;
			}
		}
				
		//loops through breeds
		for(; b < BREEDS.length; b++) {
			//loops through artifact types
			for(; t < ARTIFACT_TYPES.length; t++) {
				//loops through materials
				for(; m < ARTIFACT_MATERIALS.length; m++) {
					try {
						//inserts thing
						inCon.createStatement().executeUpdate("INSERT INTO artifacts (material, type, breed) VALUES (\"" + ARTIFACT_MATERIALS[m] + "\", \"" + ARTIFACT_TYPES[t] + "\", " + b + ");");
					}
					catch(SQLException e) {
						//outputs errors
						System.out.println("You have an SQL error at inserting the " + BREEDS[b] + " " + ARTIFACT_TYPES[t] + " " + ARTIFACT_MATERIALS[m]);
						System.out.println(e);
						return;
					}
				}
				m = 0;
			}
			t = 0;
		}
		System.out.println("Artifact Update Complete.");
	}
	public Alien getRandAlien() {
		return null;
	}
}
