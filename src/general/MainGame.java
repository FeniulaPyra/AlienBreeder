package general;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import items.*;
import panels.*;

//TODO link to sql, move everything from GENERAL into a database. << sorta done
//TODO for some reason, when breeding, the name of the child is not set to the breeds and colors...
//TODO do the jframes

//TODO CLEAN EVERYTHINGGGGGG!!!
//TODO COMMENT EVERYTHINGGGG!!!
//TODO FIX EVERYTHINGGGGGGGG!!!

public class MainGame extends JFrame {
	
	public static JPanel mainPanel = new JPanel();
	public static JTabbedPane jTPane = new JTabbedPane();
	private static AlienPanel aliens;
	private static WorkPanel work;
	private static QuestPanel quests;
	public static ShopPanel shop;
	public static CollectionPanel collection;
	
	public static JButton saveButton = new JButton("Save"); //o joy
	public static JButton loadButton = new JButton("Load");
	
	private Profile user;

	private JLabel profName;
	private JLabel exp;
	private JLabel coins;
	private JLabel level;
	
	
	public static void main(String args[]) {
		new MainGame();
	}
	
	public MainGame() {
		
		setSize(300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		this.setContentPane(jTPane);
		
		mainPanel.setLayout(new GridLayout(6, 1));
		
		General.sqlSetup();
		General.sqlUpdate();
		
		
		user = new Profile(); //TODO save/load sequence
		
		
		for(int i = 0; i < 4; i++) {
			user.add(new Alien(user.getLevel()));
		}
				
		coins = new JLabel("$");
		level = new JLabel("LVL");
		exp = new JLabel("EXP");
		
		updateValuesUI();
		
		mainPanel.add(coins);
		mainPanel.add(level);
		mainPanel.add(exp);
		mainPanel.add(saveButton);
		mainPanel.add(loadButton);

		aliens = new AlienPanel(user);
		work = new WorkPanel(user);
		quests = new QuestPanel(user);
		shop = new ShopPanel(user);
		collection = new CollectionPanel(user);
		
		jTPane.addTab("Main", mainPanel);
		jTPane.addTab("Aliens", aliens);
		jTPane.addTab("Work", work);
		jTPane.addTab("Quests", quests);
		jTPane.addTab("Shop", shop);
		jTPane.addTab("Collection", collection);
		buttonWatcherMainScreen();
	}
	
	
	/*
	public static void main(String[] args) {
		/*TODO TODO TODO TODO TODO TODO
		 * Shop
		 * Set
		 * Random Breeder
		 * Saving/Loading
		 * Adding aliens at the beginning of a game
		 * Artifacts
		 * saving
		 * going to work (searching through random stuff the spaceship picks up, i.e. dirt, ores, artifacts, aliens/etc)
		 * perhaps convert everything to a joptionpane/etc. Make everything a popup. Maybe have one pane that is always just a list of all the aliens
		 * and then other panes for other things. or just make it a jframe T.T
		 * screw jframes im freaking done
		 *
		int  choice = -1;
		Scanner input = new Scanner(System.in);
		ArrayList<Alien> userInventory = new ArrayList<Alien>();
		ArrayList<Alien> set = new ArrayList<Alien>();
		ArrayList<Artifact> shop = new ArrayList<Artifact>();
		Alien questAlien = new Alien(1);
		Alien randomBreeder = new Alien(1); //TODO
		Profile user = new Profile(userInventory, 0, 1, 100);
		joptionFrame.setAlwaysOnTop(true);
		
		String SAVE_DIRECTORY = "c:\\programmings\\Alien Breeder\\Saves\\";
		
		
		userInventory.add(new Alien(1));
		userInventory.add(new Alien(1));
		
		while(choice != 0) {
			//prompts the user with the menu of options
			System.out.println("~~~~~Menu~~~~~ C: " + user.getCoins() + " Exp: " + user.getExp() + " Level: " + user.getLevel()
					+ "\n1. View Alien List"						//tested
					+ "\n2. Breed an Alien"							//tested
					+ "\n3. Sell an Alien"							//tested
					+ "\n4. Rename an Alien"						//
		/*TODO* 	+ "\n5. (WIP)~ Compete with Alien ~(WIP)"		//
		/*TODO* 	+ "\n6. (WIP)~ Sets ~(WIP)" //This is just a quest, but you have to breed all of the children of a random set of parents (or generate a random array of 4 aliens or something) sets will give you all of the values of the aliens * 3
		/*TODO* 	+ "\n7. Quest"									//
		/*TODO* 	+ "\n8. Shop"									//
		/*TODO* 	+ "\n9. Go to Work"								//
		/*TEST* 	+ "\n10. Save"									//
		/*TEST* 	+ "\n11. Load"									//
					+ "\n0. Quit");									//tested
			choice = input.nextInt();
			
			//checks what the user chose
			switch(choice) {
				case 1:
					//View Aliens
					showList(userInventory);
					break;
				case 2:
					//Breed Aliens
					//the aliens to be bred
					Alien alienA = null;
					Alien alienB = null;
					//the array of possible offspring of the parents 
					ArrayList<Alien> possibleOffspring = new ArrayList<Alien>();

					//prompts the user for the first alien parent
					alienA = selectAlien(userInventory);
					//prompts the user for the second alien parent
					alienB = selectAlien(userInventory);
					
					if(alienA != null && alienB != null) {
						if(alienA == alienB) {
							System.out.println("You can't breed aliens with themselves!!");
						}
						else {
						
							//shows the user the possible children of the determined parents
							System.out.println("These are the possible children:");
							possibleOffspring = alienA.generatePotentialOffspring(alienB);
							showListBreedsOnly(possibleOffspring);
							//shows the range of strength and intelligence of the child
							System.out.println("Intelligence will be between " + ((alienA.getIntel() >= alienB.getIntel()) ? alienB.getIntel() + " and " + (alienB.getIntel() + 2): alienA.getIntel() + " and " + (alienA.getIntel() + 2)));
							System.out.println("Strength will be between " + ((alienA.getStrength() >= alienB.getStrength()) ? alienB.getStrength() + " and " + (alienB.getStrength() + 2): alienA.getStrength() + " and " + alienA.getStrength()));
							
							//prompts the user to confirm
							System.out.println("Is this OK?\n\t0. Exit\t1. OK\t2. Choose new parents");
							
							//adds the new alien to the user's inventory
							if(input.nextInt() == 1) {
								Alien child = possibleOffspring.get((int)(Math.random() * possibleOffspring.size()));
								child.setStrength((alienA.getStrength() > alienB.getStrength() ? alienB.getStrength(): alienA.getStrength()) + (int)(Math.random() * 3));
								child.setIntel((alienA.getIntel() > alienB.getIntel() ? alienB.getIntel(): alienA.getIntel()) + (int)(Math.random() * 3));
								child.recalculateVal();
								
								userInventory.add(child);
								
								//gives the user exp and levels up user if applicable
								user.addExp(child.getValue());
								checkLevelUp(user);
								
								//outputs the new child to the user
								System.out.println("Your new alien is \n" + child);
							}
						}
					}
					break;
				case 3:
					//Sell an Alien
					//prompts the user for the alien to be sold
					Alien alienToSell = selectAlien(userInventory);
					System.out.println(alienToSell);
					userInventory.remove(alienToSell);
					user.addCoins(alienToSell.getValue());
					choice = -1;
					break;
				case 4:
					//Rename
					//prompts the user for the alien to be renamed
					while(choice != 0) {
						Alien alienToRename = selectAlien(userInventory);
						System.out.println(alienToRename);
						System.out.println("Do you want to rename this Alien?\n0. Exit\n1. Yes, Sell the Alien\n2. Choose another Alien");
						if(choice == 1) {
							System.out.println("Please type the new name:");
							alienToRename.setName(input.nextLine());
							choice = -1;
						}
					}
					break;
				case 5:
					//Compete
					//TODO
					WIP();
					choice = -1;
					break;
				case 6:
					//Sets
					//TODO
					WIP();
					choice = -1;
					break;
				case 7:
					//Quest
					
					//shows the quest alien
					System.out.println("Your current Quest Alien is:\n" + questAlien);
					
					//prompts asking if user wants to turn in alien to complete quest
					System.out.println("Would you like to complete the quest?\n0. Exit\n1. Yes");
					choice = input.nextInt();
					
					//If the user wants to turn in an alien...
					if(choice == 1) {
						//...and the user HAS the alien
						if(userInventory.contains(questAlien)) {
							//turns alien in
							userInventory.remove(questAlien);
							
							//rewards the user with coins and exp twice the alien's usual value
							user.addCoins(questAlien.getValue() * 2);
							user.addExp(questAlien.getValue() * 2);
							
							//checks if the user leveled up
							checkLevelUp(user);
							
							//gets a new quest alien
							questAlien = new Alien(user.getLevel());
							//shows the user the quest alien
							System.out.println("Quest Complete! Here is your new Quest Alien: " + questAlien);
						}
						else {
							System.out.println("You don't have the Alien! Come back when you have the Alien!");
						}
					}
					choice = -1;
					break;
				case 8:
					//Shop
					choice = -1;
					break;
				case 9:
					//going to work
				case 10:
					//Save
					System.out.println("Do you want to save over a previous file or save a new file?\n0. Exit\n1. Save over an old file\n2. Save a new file");
					choice = input.nextInt();
					if(choice == 1) {
						save(user, (String)JOptionPane.showInputDialog(joptionFrame, "SAVE", "Choose a save file: ", JOptionPane.QUESTION_MESSAGE, null, new File(SAVE_DIRECTORY).listFiles(), ""));
					}
					else if(choice == 2) {
						System.out.println("Please type the name of your profile:");
						save(user, input.nextLine());
					}
					choice = -1;
					break;
				case 11:
					//Load
					user = load((File)JOptionPane.showInputDialog(joptionFrame, "LOAD", "Choose a save file: ", JOptionPane.QUESTION_MESSAGE, null, new File(SAVE_DIRECTORY).listFiles(), ""));
					break;
				case 12:
					//test aliens
					for(int i = 0; i < 15; i++) {
						System.out.println((new Alien(user.getLevel())).getName());
					}
					break;
				case 0:
					//exit
					System.out.println("Are you sure? Don't forget to save first!\n0. Yes, I'm Leaving!\n1. Nevermind!");
					choice = input.nextInt();
					break;
				default:
					System.out.println("badInput");
			}
		}
	}
	public static void showList(ArrayList<Alien> inAliens) {
		for(int i = 0; i < inAliens.size(); i++) {
			Alien current = inAliens.get(i);
			System.out.println((i + 1) + ". " + current);
		}
	}
	public static void showListBreedsOnly(ArrayList<Alien> inAliens) {
		for(int i = 0; i < inAliens.size(); i++) {
			Alien current = inAliens.get(i);
			System.out.println((i + 1) + ". " + current.getBreedColor() + " " + current.getBreedPatternColor() + " " + current.getBreedPattern() + " " + current.getBreed());
		}
	}
	//TODO WIP needs to check if file already exists/prompt user for preexisting file when saving
	public static void save(Profile inUser, String name) {
		try {
			File save = new File(name + ".ser");
			FileOutputStream fos= new FileOutputStream(save);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(inUser);
			System.out.println("Saved at " + save);
			oos.close();
			fos.close();
		}
		catch(Exception e) {
			System.out.println("There was a problem while saving! " + e);
		}
	}
	public static Profile load(File inUser) {
		Profile tempUser = null;
		try {
			FileInputStream fis = new FileInputStream(inUser);
			ObjectInputStream ois = new ObjectInputStream(fis);
			tempUser = (Profile)ois.readObject();
			ois.close();
			fis.close();
		}
		catch(FileNotFoundException f) {
			System.out.println("That file was not found!!" + f);
		}
		catch(IOException i) {
			System.out.println("There was a problem!" + i);
		}
		catch(ClassNotFoundException c) {
			System.out.println("That file is not a profile!" + c);
		}
		return tempUser;
	}
	public static void WIP() {
		System.out.println("Coming Soon!");
	}
	public static Alien selectAlien(ArrayList<Alien> inAliens) {
		return (Alien)JOptionPane.showInputDialog(joptionFrame, "SELECT ALIEN", "Choose an alien: ", JOptionPane.QUESTION_MESSAGE, null, inAliens.toArray(), "");
	}*/
	public void buttonWatcherMainScreen() {
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				General.achieve(user.aliens.get(0));
				General.achieve(General.getRandArtifact(user.getLevel()));
				//WIP();
			}
		});
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WIP();
			}
		});
		jTPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				shop.shopUpdate();
				shop.updateSellables();
				//aliens.updateLabels();
				updateValuesUI();
			}
		});
	}
	
	//TODO probs don't need this anymore
	public Alien selectAlien(String function, ArrayList<Alien> alienChoices) {
		return (Alien) JOptionPane.showInputDialog(null, "Select an alien: ", function, JOptionPane.QUESTION_MESSAGE, null, alienChoices.toArray(), "Select");
	}
	
	
	//TODO probs don't need this either
	public void  work() {
	}
	
	public void WIP() {
		JOptionPane.showConfirmDialog(this, "WIP", "Coming Soon!", JOptionPane.OK_CANCEL_OPTION);
	}
	public void updateValuesUI() {
		user.checkLevelUp();
		coins.setText("$" + user.getCoins());
		level.setText("LVL " + user.getLevel());
		exp.setText("EXP " + user.getExp() + "/" + user.getMaxExp());
	}
}
