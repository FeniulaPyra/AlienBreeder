package general;
import java.sql.*;
import java.util.ArrayList;

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
	
	//~~DATABASE STATUS~~\\
	public static final int getAlienSize() {
		try {
			ResultSet size = mainCon.createStatement().executeQuery("SELECT * FROM aliens WHERE id = (SELECT MAX(id) FROM aliens);");
			return (size).getInt("id") + 1;
		}
		catch(SQLException s) {
			System.out.println("Couldn't get largest alien id\n" + s);
		}
		return 0;
	}
	public static final int getArtifactSize() {
		try {
			return (mainCon.createStatement().executeQuery("SELECT * FROM artifacts WHERE id = (SELECT MAX(id) FROM artifacts);")).getInt("id") + 1;
		} 
		catch(SQLException s) {
			System.out.println("Couldn't get largest artifact id\n" + s);
		}
		return 0;
	}
	
	//~~UPDATE DATABASE~~\\ 
	public static final void updateBreedTable() {
		//TODO reupdate the table, because the basevals of all the breeds is wrong.
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
		
		try {
			mainCon.createStatement().executeUpdate("UPDATE aliens SET has = 0;");
		}
		catch(SQLException s) {
			System.out.println("Can't reset achievements: ");
			s.printStackTrace();
		}
		
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
							mainCon.createStatement().executeUpdate("INSERT INTO aliens (pattern, pattern_color, color, breed, has) VALUES (\"" + B_PATTERNS[bp] + "\", \"" + B_PAT_COLORS[bpc] + "\", \"" + B_COLORS[bc] + "\", " + b + ", 0);");
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
						mainCon.createStatement().executeUpdate("INSERT INTO artifacts (material, type, breed, has) VALUES (\"" + ARTIFACT_MATERIALS[m] + "\", \"" + ARTIFACT_TYPES[t] + "\", " + b + ", 0);");
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
	
	//~~GET STUFF~~\\ 
	//TODO FIXME
	public static Alien getAlien(int ID) {
		try {
			//ResultSet dbAlien = mainCon.createStatement().executeQuery("SELECT * FROM aliens WHERE id = " + ID + ";");
			Breed breed = BREEDS[mainCon.createStatement().executeQuery("SELECT * FROM aliens WHERE id = " + ID + ";").getInt("breed")];
			String color = mainCon.createStatement().executeQuery("SELECT * FROM aliens WHERE id = " + ID + ";").getString("color");
			String pattern = mainCon.createStatement().executeQuery("SELECT * FROM aliens WHERE id = " + ID + ";").getString("pattern");
			String patCol = mainCon.createStatement().executeQuery("SELECT * FROM aliens WHERE id = " + ID + ";").getString("pattern_color");
			
			return new Alien(breed, color, pattern, patCol);//BREEDS[dbAlien.getInt("breed")], dbAlien.getString("color"), dbAlien.getString("pattern"), dbAlien.getString("pattern_color"));
		}
		catch(SQLException s) {
			System.out.println("sqlexception when getting an alien from an id\n");
			s.printStackTrace();
		}
		return null;
	}
	public static Artifact getArt(int ID) {
		try {
			ResultSet dbArtifact = mainCon.createStatement().executeQuery("SELECT * FROM artifacts WHERE id = " + ID + ";");
			return new Artifact(BREEDS[dbArtifact.getInt("breed")], dbArtifact.getString("material"), dbArtifact.getString("type"));
		}
		catch(SQLException s) {
			System.out.println("sqlexception when getting an artfact from its id\n" + s);
		}
		return null;
	}
	public static String getAlienColor(int ID) {
		try {
			return mainCon.createStatement().executeQuery("SELECT * FROM aliens WHERE id = " + ID + ";").getString("color");
		}
		catch(SQLException s) {
			System.out.println("Can't get alien color: ");
			s.printStackTrace();
		}
		return null;
	}
	public static String getAlienPattern(int ID) {
		try {
			return mainCon.createStatement().executeQuery("SELECT * FROM aliens WHERE id = " + ID + ";").getString("pattern");
		}
		catch(SQLException s) {
			System.out.println("Can't get alien pattern: ");
			s.printStackTrace();
		}
		return null;
	}
	public static String getAlienPatternColor(int ID) {
		try {
			return mainCon.createStatement().executeQuery("SELECT * FROM aliens WHERE id = " + ID + ";").getString("pattern_color");
		}
		catch(SQLException s) {
			System.out.println("Cant' get alien pattern color: ");
			s.printStackTrace();
		}
		return null;
	}
	public static Breed getAlienBreed(int ID) {
		try {
			return BREEDS[mainCon.createStatement().executeQuery("SELECT * FROM aliens WHERE id = " + ID + ";").getInt("breed")];
		}
		catch(SQLException s) {
			System.out.println("Can't get alien breed: ");
			s.printStackTrace();
		}
		return null;
	}
	
	//~~RANDOM STUFF~~\\
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

	//~~ACHIEVEMENT STUFF~~\\
	
	public static void achieve(Alien achieved) {
		String name = achieved.getName();
		String color;
		String pattern;
		String patternColor;
		int breedID;
		
		color = name.substring(0, name.indexOf(' '));
		name = name.substring(name.indexOf(' ') + 1,  name.lastIndexOf(' '));
		
		patternColor = name.substring(0, name.indexOf(' '));
		name = name.substring(name.indexOf(' ') + 1, name.length());
		
		pattern = name.substring(name.indexOf(' ') + 1, name.length());
		
		try {
			ResultSet breed = mainCon.createStatement().executeQuery("SELECT * FROM breeds WHERE name = \"" + achieved.getBreed().getName() + "\";");
			breedID = breed.getInt("id");
		}
		catch(SQLException s) {
			System.out.println("error in finding an alien's breed!\n" + s);
			return;
		}
		
		try {
			mainCon.createStatement().executeUpdate("UPDATE aliens SET has = 1 WHERE breed = " + breedID + " AND color = \"" + color + "\" AND pattern = \"" + pattern + "\" AND pattern_color = \"" + patternColor + "\";");
		}
		catch(SQLException s) {
			System.out.println("error when achieving alien!\n" + s);
			return;
		}
		
	}
	public static void achieve(Artifact achieved) {
		String name = achieved.getName();
		String material;
		String object;
		int breedID;
		
		material = name.substring(name.indexOf(' ') + 1, name.lastIndexOf(' '));
		object = name.substring(name.lastIndexOf(' ') + 1);
		
		try {
			ResultSet breed = mainCon.createStatement().executeQuery("SELECT * FROM breeds WHERE name = \"" + achieved.getBreed().getName() + "\";");
			breedID = breed.getInt("id");
		}
		catch(SQLException s) {
			System.out.println("error in finding an artifact breed!\n" + s);
			return;
		}
		
		try {
			mainCon.createStatement().executeUpdate("UPDATE artifacts SET has  = 1 WHERE breed = " + breedID + " AND material = \"" + material + "\" AND type = \"" + object + "\";");
		}
		catch(SQLException s) {
			System.out.println("error when achieving artifact!\n" + s);
		}
	}
	public static void resetArtifacts() {
		try {
			mainCon.createStatement().executeUpdate("UPDATE artifacts SET has = 0;");
		}
		catch(SQLException s) {
			System.out.println("Error when resetting artifacts achievements!\n" + s);
		}
	}
	public static void resetAliens() {
		try {
			mainCon.createStatement().executeUpdate("UPDATE aliens SET has = 0;");
		}
		catch(SQLException s) {
			System.out.println("Error when resetting alien achievements!\n" + s);
		}
	}
	public static void resetAll() {
		resetArtifacts();
		resetAliens();
	}
	public static boolean haveAlien(Alien inAlien) {
		try {
			ResultSet dbAlien = mainCon.createStatement().executeQuery("SELECT aliens.has FROM aliens LEFT OUTER JOIN breeds ON breeds.id = aliens.breed WHERE pattern = " + inAlien.getBreedPattern() + " AND pattern_color = " + inAlien.getBreedPatternColor() + " AND color = " + inAlien.getBreedColor() + " AND breed.name = " + inAlien.getBreed().getName() + ";");
			return dbAlien.getInt("has") == 1;
		}
		catch(SQLException e) {
			System.out.println("Sql exception when checking if an alien has been achieved.\n" + e);
		}
		return false;
	}
	public static boolean haveAlien(int id) {
		return haveAlien(getAlien(id));
	}
	public static boolean haveArtifact(Artifact inArt) {
		try {
			ResultSet dbArt = mainCon.createStatement().executeQuery("SELECT artifacts.has FROM aliens LEFT OUTER JOIN breeds ON breeds.id = artifacts.breed WHERE material = " + inArt.getMaterial() + " AND type = " + inArt.getObject() + " AND breed.name = " + inArt.getBreed().getName() + ";");
			return dbArt.getInt("has") == 1;
		}
		catch(SQLException e) {
			System.out.println("Sql exception when checking if an alien has been achieved.\n" + e);
		}
		return false;
	}
	public static boolean haveArtifact(int id) {
		return haveArtifact(getArt(id));
	}
	public static ArrayList<Alien> getAllAliens(boolean achieved) {
		ArrayList<Alien> gotten = new ArrayList<Alien>();
		try {
			ResultSet aliens = mainCon.createStatement().executeQuery("SELECT * FROM aliens;");
			do {
				if((achieved && aliens.getInt("has") == 1) || !achieved) {
					gotten.add(new Alien(BREEDS[aliens.getInt("breed")], aliens.getString("color"), aliens.getString("pattern"), aliens.getString("pattern_color")));
				}
			}while(aliens.next());
		}
		catch(SQLException s) {
			System.out.println("Can't get gotten aliens: ");
			s.printStackTrace();
		}
		return gotten;
	}
	
	
	//~~OTHER~~\\
	public static int countChar(String str, char toFind) {
		int num = 0;
		for(int i = 0; i < str.length(); i++) {
			if(str.charAt(i) == toFind) num++;
		}
		return num;
	}
}
