package general;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

import org.sqlite.*;
import items.*;
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
	//all of the possible breeds. Can add breeds here if wanted.
	public static final Breed[] BREEDS = { //15
			new Breed("Inockei", 1), 
			new Breed("Lezynii", 2), new Breed("Uhuquas", 2),
			new Breed("Viishika", 3), new Breed("Chourov", 3), new Breed("Geivol", 3),
			new Breed("Dugosy", 4), new Breed("Xaesyt", 4), new Breed("Eixox", 4),
			new Breed("Weazo", 5), new Breed("Jichayrei", 5), new Breed("Leith", 5),
			new Breed("Pheis", 6), new Breed("Tatoul", 6), new Breed("Ghokeyli", 6),
			new Breed("Eaquozo", 7)};
	//all of the possible base colors.
	public static final String[] B_COLORS = {"Black", "White", "Red", "Brown", "Orange", "Yellow", "Green", "Blue", "Violet", "Rose"}; //10
	//all of the possible patterns
	public static final String[] B_PATTERNS = {"Tayn", "Zaye", "Yetathi", "Gehour", "Eyloyle", "Ireimo", "Hyiju", "Kaeque", "Aetachi"}; //9
	//all of the possible pattern colors
	public static final String[] B_PAT_COLORS = {"Slate", "Silver", "Salmon", "Chestnut", "Mango", "Gold", "Oceana", "Fuschia", "Magenta"}; //9
	
	//Artifact Types:
	//different kinds of objects
	public static final String[] ARTIFACT_TYPES = {"", "Totem", "Bowl", "Urn", "Picture", "Tools", "Toy", "Instrument", "Headdress", "Object"}; //10
	//different materials the objects could be made out of. Yes, you can have some cardboard tools.
	public static final String[] ARTIFACT_MATERIALS = {"Cardboard", "Glass", "Bronze", "Silver", "Gold", "Crystal"}; //6
	
	//Junks
	public static final String[] JUNK_TYPES = {"Dust Bunny", "Dirt", "Dead Flower", "Styrafoam cup", "Magical Spoon", "Old Bread", "Unidentifyable Glob", "Rusty Metal"};
	
	//~~DATABASE STATUS~~\\
	//gets the number of aliens in the database
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
	//gets the number of artifacts in the database
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
	//updates the breed table to match breeds array here
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
	//updates the aliens to match the breeds array here. This method was a pain in the rear end. Testing it required clearing and resetting the database multiple times. that took like a half hour each time.
	public static final void updateAlienTable() {
		//From here to the breeds loop will start the loop at the last alien entry
		
		try {
			mainCon.createStatement().executeUpdate("UPDATE aliens SET has = 0;");
		}
		catch(SQLException s) {
			System.out.println("Can't reset achievements: ");
			s.printStackTrace();
		}
		
		int b = 0, bc = 0, bp = 0, bpc = 0, lastB = 0;
		String lastBC = "", lastBP = "", lastBPC = "";
		
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
	//updates the artifacts to match the breeds array here
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
	
	//returns a random alien. For work/shop area.
	public static final Alien getRandAlien(int lvl) {
		return new Alien(getRandomBreed(lvl), getRandomColor(), getRandomPattern(), getRandomPatternColor());
	}
	//returns a random artifact. For work/shop area.
	public static final Artifact getRandArtifact(int lvl) {
		return new Artifact(getRandomBreed(lvl), getRandomMaterial(), getRandomObject());
	}
	
	//sets up the sql connection! Very important!!!
	public static Connection sqlSetup() {
		String host = "jdbc:sqlite:D:\\Code\\alienbreeder\\assets\\alienDB.db";
		
		try {
			mainCon = DriverManager.getConnection(host);
			System.out.println("Connection successful.");
			return mainCon;
		}
		catch (SQLException s) {
			System.out.println("SQL Error! " + s);
		}
		catch (Exception e) {
			System.out.println("Error! "  + e);
		}
		return null;
	}
	
	//updates the various tables. Done at the beginning of the program.
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
	//gets an alien from the database based on its unique id
	public static Alien getAlien(int ID) {
		try {
			ResultSet dbAlien = mainCon.createStatement().executeQuery("SELECT * FROM aliens WHERE id = " + ID + ";");
			
			return new Alien(BREEDS[dbAlien.getInt("breed")], dbAlien.getString("color"), dbAlien.getString("pattern"), dbAlien.getString("pattern_color"));
		}
		catch(SQLException s) {
			System.out.println("sqlexception when getting an alien from an id\n");
			s.printStackTrace();
		}
		return null;
	}
	//gets an art from the database based on its unique id
	public static Artifact getArt(int ID) {
		try {
			//ResultSet dbArtifact = mainCon.createStatement().executeQuery("SELECT * FROM artifacts WHERE id = " + ID + ";");
			Breed breed = BREEDS[mainCon.createStatement().executeQuery("SELECT * FROM artifacts WHERE id = " + ID + ";").getInt("breed")];
			String material = mainCon.createStatement().executeQuery("SELECT * FROM artifacts WHERE id = " + ID + ";").getString("material");
			String object = mainCon.createStatement().executeQuery("SELECT * FROM artifacts WHERE id = " + ID + ";").getString("type");
			return new Artifact(breed, material, object);//BREEDS[dbArtifact.getInt("breed")], dbArtifact.getString("material"), dbArtifact.getString("type"));
		}
		catch(SQLException s) {
			System.out.println("sqlexception when getting an artfact from its id\n" + s);
		}
		return null;
	}
	//returns the color of the alien with the specified id.
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
	//returns the pattern of the alien given its specific id
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
	//returns the pattern color given aliens specific id
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
	//gets the breed of the alien with the specified id
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
	
	//gets a random breed of level less than the given level.
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
	//gets a random color
	public static String getRandomColor() {
		return B_COLORS[(int)(Math.random() * B_COLORS.length)];
	}
	//gets a random pattern
	public static String getRandomPattern() {
		return B_PATTERNS[(int)(Math.random() * B_PATTERNS.length)];
	}
	//gets a random color of pattern
	public static String getRandomPatternColor() {
		return B_PAT_COLORS[(int)(Math.random() * B_PATTERNS.length)];
	}
	
	//Random Artifact Stuff
	//gets a random object of an artifact (Totem, tools, headdress, etc)
	public static String getRandomObject() {
		return ARTIFACT_TYPES[(int)(Math.random() * ARTIFACT_TYPES.length)];
	}
	//gets a random material for an artifact
	public static String getRandomMaterial() {
		return ARTIFACT_MATERIALS[(int)(Math.random() * ARTIFACT_MATERIALS.length)];
	}
	
	//gets a random junk
	public static String getRandomJunk() {
		return JUNK_TYPES[(int)(Math.random() * JUNK_TYPES.length)];
	}

	//~~ACHIEVEMENT STUFF~~\\
	
	//changes the "has" field in the alien table to show as true(1) for the specified alien. for achievement only
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
	//changes the "has" field in the art table to show as true(1) for the specified artifact. for achievement only.
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
	
	//resets the achieved arts at the beginning of the game.
	public static void resetArtifacts() {
		try {
			mainCon.createStatement().executeUpdate("UPDATE artifacts SET has = 0;");
		}
		catch(SQLException s) {
			System.out.println("Error when resetting artifacts achievements!\n" + s);
		}
	}
	//resets the achieved aliens at the beginning of the game.
	public static void resetAliens() {
		try {
			mainCon.createStatement().executeUpdate("UPDATE aliens SET has = 0;");
		}
		catch(SQLException s) {
			System.out.println("Error when resetting alien achievements!\n" + s);
		}
	}
	//resets both arts and aliens because I am lazy.
	public static void resetAll() {
		resetArtifacts();
		resetAliens();
	}
	
	//checks if the user has achieved a certain alien object. for collection screen only
	public static boolean haveAlien(Alien inAlien) {
		try {
			ResultSet dbAlien = mainCon.createStatement().executeQuery("SELECT aliens.has FROM aliens LEFT OUTER JOIN breeds ON breeds.id = aliens.breed WHERE pattern = \"" + inAlien.getBreedPattern() + "\" AND pattern_color = \"" + inAlien.getBreedPatternColor() + "\" AND color = \"" + inAlien.getBreedColor() + "\" AND breeds.name = \"" + inAlien.getBreed().getName() + "\";");
			return dbAlien.getInt("has") == 1;
		}
		catch(SQLException e) {
			System.out.println("Sql exception when checking if an alien has been achieved.\n" + e);
		}
		return false;
	}
	//checks if user has achieved certain alien based on its id. for collection only
	public static boolean haveAlien(int id) {
		return haveAlien(getAlien(id));
	}
	//checks if the user has achieved an artifact object. for collection
	public static boolean haveArtifact(Artifact inArt) {
		try {
			mainCon.endRequest();
			ResultSet dbArt = mainCon.createStatement().executeQuery("SELECT artifacts.has FROM artifacts LEFT OUTER JOIN breeds ON breeds.id = artifacts.breed WHERE material = \"" + inArt.getMaterial() + "\" AND type = \"" + inArt.getObject() + "\" AND breeds.name = \"" + inArt.getBreed().getName() + "\";");
			return dbArt.getInt("has") == 1;
		}
		catch(SQLException e) {
			System.out.println("Sql exception when checking if an artifact has been achieved.\n" + e);
			e.printStackTrace();
		}
		return false;
	}
	//checks if user has achieved art based on its id. for collection
	public static boolean haveArtifact(int id) {
		return haveArtifact(getArt(id));
	}
	
	//gets all of the aliens from the database. Will either include all or just the achieved ones. for collection panel only 
	public static ArrayList<Alien> getAllAliens(boolean achieved) {
		ArrayList<Alien> gotten = new ArrayList<Alien>();
		try {
			ResultSet aliens = mainCon.createStatement().executeQuery("SELECT * FROM aliens;");
			do {
				if((achieved && aliens.getInt("has") == 1) || !achieved) {
					gotten.add(new Alien(BREEDS[aliens.getInt("breed")], aliens.getString("color"), aliens.getString("pattern"), aliens.getString("pattern_color")));
				}
			}while(aliens.next());
			aliens.close();
		}
		catch(SQLException s) {
			System.out.println("Can't get gotten aliens: ");
			s.printStackTrace();
		}
		return gotten;
	}
	//gets all of the arts from the database. Will have either just achieved or all of them. for collection only
	public static ArrayList<Artifact> getAllArts(boolean achieved) {
		ArrayList<Artifact> gotten = new ArrayList<Artifact>();
		try {
			ResultSet arts = mainCon.createStatement().executeQuery("SELECT * FROM artifacts;");
			do {
				if((achieved && arts.getInt("has") == 1) || !achieved) {
					gotten.add(new Artifact(BREEDS[arts.getInt("breed")], arts.getString("material"), arts.getString("type")));
				}
			}while(arts.next());
			arts.close();
		}catch(SQLException s) {
			System.out.println("Can't get gotten arts: ");
			s.printStackTrace();
		}
		return gotten;
	}
	//Gets all of the aliens of a certain breed and color, patter, or pattern color. For multi quest.
	public static ArrayList<Alien> getAliensOfType(Breed inB, String inType) {
		ArrayList<Alien> toSend = new ArrayList<Alien>();
		String typeValue = "";
		if((Arrays.asList(B_COLORS)).contains(inType)) {
			typeValue = "color";
		}
		if((Arrays.asList(B_PATTERNS)).contains(inType)) {
			typeValue = "pattern";
		}
		if((Arrays.asList(B_PAT_COLORS)).contains(inType)) {
			typeValue = "pattern_color";
		}
		
		ArrayList<Alien> temp = getAllAliens(false);
		for(int i = 0; i < temp.size(); i++) {
			if(temp.get(i).getBreed() == inB && temp.get(i).getName().contains(inType)) {
				toSend.add(temp.get(i));
			}
		}
		
		return toSend;
	}
	
	//~~OTHER~~\\
	//a thing to count the number of a certain character in a string. for getting aliens for multiquest.
	public static int countChar(String str, char toFind) {
		int num = 0;
		for(int i = 0; i < str.length(); i++) {
			if(str.charAt(i) == toFind) num++;
		}
		return num;
	}
}
