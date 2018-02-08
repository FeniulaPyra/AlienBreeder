package general;
import java.sql.*;
import org.sqlite.*;
import items.*;

//TODO maybe you should make this file more readable...


//TODO artifacts table screwed up
//TODO while the breeds start at 0 in the alien table (where Inockei breed = 0), the breeds table starts at 1 (where Inockei = 1). This needs to be fixed
//IDK if i fixed those yet ^^

/*
 * basic storyline: you are a worker on a research/exploration spaceship. Despite the scientific advances, the spaceship can not sort things it finds (dirt, rubble, rare things, aliens)
 * because all of the programmers went on strike before programming the sorting algorithm for an unknown amount of time. So, your job as a worker is to sort through all of the random 
 * stuff the spaceship finds. However, you also collect and breed the tiny aliens that the space crew would otherwise discard(ToT) or put through horrible science experiments. However,
 * some of the other workers have also started doing the same thing and will occasionally request certain aliens from you.
 */
	//Base Value Equation = 2.5L^2 + 12.5L + 15
	//Strength and Intel = 10L + ((rand)L - 2L)
	//When breeding, Strength/Intel will be the lowest value + 0, 1, or 2

public abstract class General {
	public static Connection mainCon;
	//~~TYPES~~\\
	
	//Alien Types
	public static final Breed[] BREEDS = { //15
			new Breed("Inockei", 1), 
			new Breed("Lezynii", 2), new Breed("Uhuquas", 2),
			new Breed("Viishika", 3), new Breed("Chourov", 3), new Breed("Geivol", 3),
			new Breed("Dugosy", 4), new Breed("Xaesyt", 4), new Breed("Eixox", 4),
			new Breed("Weazo", 5), new Breed("Jichayrei", 5), new Breed("Leith", 5),
			new Breed("Pheis", 6), new Breed("Tatoul", 6), new Breed("Ghokeyli", 6),
			new Breed("Eaquozo", 7)};
	public static final String[] B_COLORS = {"Black", "White", "Red", "Brown", "Orange", "Yellow", "Green", "Blue", "Violet", "Rose"}; //10
	public static final String[] B_PATTERNS = {"Tayn", "Zaye", "Yetathi", "Gehour", "Eyloyle", "Ireimo", "Hyiju", "Kaeque", "Aetachi"}; //9
	public static final String[] B_PAT_COLORS = {"Slate", "Silver", "Salmon", "Chestnut", "Mango", "Gold", "Oceana", "Fuschia", "Magenta"}; //9
	
	//Artifact Types:
	public static final String[] ARTIFACT_TYPES = {"", "Totem", "Bowl", "Urn", "Picture", "Tools", "Toy", "Instrument", "Headdress", "Object"}; //10
	public static final String[] ARTIFACT_MATERIALS = {"Cardboard", "Glass", "Bronze", "Silver", "Gold", "Crystal"}; //6
	
	//Junks
	public static final String[] JUNK_TYPES = {"JFrame", "Dirt", "Dead Flower", "Styrafoam cup", "Magical Spoon", "Old Bread", "Unidentifyable Glob", "Rusty Metal"};
	
	//~~UPDATE DATABASE~~\\
	public static final void updateBreedTable() {
		int lastB = 0;
		try {
			//Clears the table so there shouldn't be any duplicates
			ResultSet lastBreed = mainCon.createStatement().executeQuery("SELECT * FROM breeds where id = (SELECT MAX(id) FROM breeds);");
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
				mainCon.createStatement().executeUpdate("INSERT INTO breeds VALUES (" + b + ", \"" + BREEDS[b].getName() + "\", " + BREEDS[b].getLevel() + ", " + BREEDS[b].getBaseVal() + ");");
			}
			catch(SQLException e) {
				//outputs possible errors
				System.out.println("You have an SQL error at inserting the " + b + "th breed\n" + e);
			}
		}
		System.out.println("Breed Update Complete.");
	}
	public static final void updateAlienTable() {
		//From here to the breeds loop will start the loop at the last alien entry
		//TODO cleanup
		
		int b = 0, bc = 0, bp = 0, bpc = 0, lastB = 0;
		String lastBC = "", lastBP = "", lastBPC = "";
		int tempB = 0, tempBC = 0, tempBP = 0, tempBPC = 0;
		
		//gets the last alien entry from the database and separates the values into indiv vars
		try {
			ResultSet lastAlien = mainCon.createStatement().executeQuery("SELECT * FROM aliens WHERE id = (SELECT MAX(id) FROM aliens);");
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
		System.out.println((((b+bc+bp+bpc)/(double)(BREEDS.length * B_COLORS.length * B_PATTERNS.length * B_PAT_COLORS.length)) * 100) + "%");
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
							mainCon.createStatement().executeUpdate("INSERT INTO aliens (pattern, pattern_color, color, breed) VALUES (\"" + B_PATTERNS[bp] + "\", \"" + B_PAT_COLORS[bpc] + "\", \"" + B_COLORS[bc] + "\", " + b + ");");
							//if((b+bc+bp+bpc) % 100 == 0) {
								//System.out.println((((b*bc*bp*bpc) +"/" + (double)(BREEDS.length * B_COLORS.length * B_PATTERNS.length * B_PAT_COLORS.length)) /* 100*/) + "%");
						//	}
							if(mainCon.createStatement().executeQuery("SELECT * FROM aliens WHERE id = (SELECT MAX(id) FROM aliens);").getInt("id") % 100 == 0) {
							System.out.println(mainCon.createStatement().executeQuery("SELECT * FROM aliens WHERE id = (SELECT MAX(id) FROM aliens);").getInt("id"));
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
	public static final void updateArtifactTable() {
		int lastB = 0;
		String lastT = "", lastM = "";
		int t = 0, b = 0, m = 0;
		
		
		try {
			ResultSet lastArt = mainCon.createStatement().executeQuery("SELECT * FROM artifacts where id = (SELECT MAX(id) FROM artifacts);");
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
						mainCon.createStatement().executeUpdate("INSERT INTO artifacts (material, type, breed) VALUES (\"" + ARTIFACT_MATERIALS[m] + "\", \"" + ARTIFACT_TYPES[t] + "\", " + b + ");");
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
	
	public static final Alien getRandAlien(int lvl) {
		return new Alien(getRandomBreed(lvl), getRandomColor(), getRandomPattern(), getRandomPatternColor());
	}
	public static final Artifact getRandArtifact(int lvl) {
		return new Artifact(getRandomBreed(lvl), getRandomMaterial(), getRandomObject());
	}
	
	public static Connection sqlSetup() {
		String host = "jdbc:sqlite:C:\\Users\\165760\\eclipse-workspace\\AlienBreeder\\assets\\alienDB.db";
		
		try {
			mainCon = DriverManager.getConnection(host);
			System.out.println("Connection successful.");
			return mainCon;
		}
		catch (SQLException s) {
			System.out.println("SQL Error! " + s);
		}
		catch (Exception e) { //<< Lazy
			System.out.println("Error! "  + e);
		}
		return null;
	}
	public static void sqlUpdate() {
		System.out.println("");
		try {
			System.out.println("Beginning breeds update...");
			updateBreedTable();
		}
		catch(Exception e) {
			System.out.println("Error at breeds update: " + e);
		}
		try {
			System.out.println("Beginning aliens update...");
			updateAlienTable();
		}
		catch(Exception e) {
			System.out.println("Error at aliens update: " + e);
		}
		try {
			System.out.println("Beginning artifacts update...");
			updateArtifactTable();
		}
		catch(Exception e) {
			System.out.println("Error at artifacts update: " + e);
		}
	}
	
	//Random Alien Stuff
	public static Breed getRandomBreed(int lvl) {
		try {
			ResultSet breeds = mainCon.createStatement().executeQuery("SELECT * FROM breeds WHERE level = " + lvl + " ORDER BY RANDOM() LIMIT 1;");
			return BREEDS[breeds.getInt("id")];
		}
		catch(SQLException s) {
			System.out.println("SQL Error when getting random breed from database!" + s);
		}
		catch(Exception e) {
			System.out.println("");
		}
		return BREEDS[0];
		
	}
	public static String getRandomColor() {
		return B_COLORS[(int)(Math.random() * B_COLORS.length)];
	}
	public static String getRandomPattern() {
		return B_PATTERNS[(int)(Math.random() * B_PATTERNS.length)];
	}
	public static String getRandomPatternColor() {
		return B_PAT_COLORS[(int)(Math.random() * B_PATTERNS.length)];
	}
	
	//Random Artifact Stuff
	public static String getRandomObject() {
		return ARTIFACT_TYPES[(int)(Math.random() * ARTIFACT_TYPES.length)];
	}
	public static String getRandomMaterial() {
		return ARTIFACT_MATERIALS[(int)(Math.random() * ARTIFACT_MATERIALS.length)];
	}
	
	//random junk
	public static String getRandomJunk() {
		return JUNK_TYPES[(int)(Math.random() * JUNK_TYPES.length)];
	}
}
