import java.io.*;
import java.util.*;

public class MainGame {
	public static void main(String[] args) {
		/*TODO TODO TODO TODO TODO TODO
		 * Shop
		 * Set
		 * Random Breeder
		 * Saving/Loading
		 * Adding aliens at the beginning of a game
		 * Artifacts
		 */
		int  choice = -1;
//		int coins = 0;
//		int exp = 0;
//		int level = 1;
		Scanner input = new Scanner(System.in);
		ArrayList<Alien> userInventory = new ArrayList<Alien>();
		ArrayList<Alien> set = new ArrayList<Alien>();
		ArrayList<Artifact> shop = new ArrayList<Artifact>();
		Alien questAlien = new Alien(1);
		Alien randomBreeder = new Alien(1); //TODO
		Profile user = new Profile(userInventory, 0, 1, 100);
		
		while(choice != 0) {
			//prompts the user with the menu of options
			System.out.println("~~~~~Menu~~~~~"
					+ "\n1. View Alien List"
					+ "\n2. Breed an Alien"
					+ "\n3. Sell an Alien"
					+ "\n4. Rename an Alien"
		/*TODO*/	+ "\n5. (WIP)~ Compete with Alien ~(WIP)"
		/*TODO*/	+ "\n6. (WIP)~ Sets ~(WIP)" /*This is just a quest, but you have to breed all of the children of a random set of parents*/
					+ "\n7. Quest"
					+ "\n8. Shop"
					+ "\n9. Save"
					+ "\n10. Load"
					+ "\n0. Quit");
			choice = input.nextInt();
			
			//checks what the user chose
			switch(choice) {
				case 1:
					//View Aliens
					showList(userInventory);
					break;
				case 2:
					//Breed Aliens
					while(choice != 0) {
						//the aliens to be bred
						Alien alienA = null;
						Alien alienB = null;
						//the array of possible offspring of the parents 
						ArrayList<Alien> possibleOffspring = new ArrayList<Alien>();
						
						//shows the aliens in the user's inventory
						showList(userInventory);
						System.out.println("0. Exit");
						choice = input.nextInt();
		/*TODO*/		if(choice != 0) {
							//prompts the user for the first alien parent
							System.out.println("Type the number next to the first alien parent");
							alienA = userInventory.get(choice);
							//prompts the user for the second alien parent
							System.out.println("Type the number next to the second alien parent");
							alienB = userInventory.get(input.nextInt());
							
							//shows the user the possible children of the determined parents
							System.out.println("These are the possible children:");
							possibleOffspring = alienA.generatePotentialOffspring(alienB);
							showList(possibleOffspring);
							//shows the range of strength and intelligence of the child
							System.out.println("Intelligence will be between " + ((alienA.getInt() >= alienB.getInt()) ? alienB.getInt() + " and " + (alienB.getInt() + 2): alienA.getInt() + " and " + (alienA.getInt() + 2)));
							System.out.println("Strength will be between " + ((alienA.getStrength() >= alienB.getStrength()) ? alienB.getStrength() + " and " + (alienB.getStrength() + 2): alienA.getStrength() + " and " + alienA.getStrength()));
							
							//prompts the user to confirm
							System.out.println("Is this OK?\n\t0. Exit\t1. OK\t2. Choose new parents");
							
							//adds the new alien to the user's inventory
							if(input.nextInt() == 1) {
								Alien child = possibleOffspring.get((int)(Math.random() * possibleOffspring.size()));
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
					while(choice != 0) {
						//shows the inventory to the user
						showList(userInventory);
						System.out.println("0. Exit");
						//prompts the user for the alien to be sold
						System.out.println("Type the number next to the alien you want to sell.");
						choice = input.nextInt();
						try {
							if(choice != 0) {
								Alien alienToSell = userInventory.get(choice - 1);
								System.out.println(alienToSell);
								System.out.println("Do you want to sell this Alien?\n0. Exit\n1. Yes, Sell the Alien\n2. Choose another Alien");
								if(choice == 1) {
									userInventory.remove(alienToSell);
									user.addCoins(alienToSell.getValue());
								}
							}
						}
						catch(IndexOutOfBoundsException e) {
							System.out.println("That alien doesn't exist.");
						}
					}
					break;
				case 4:
					break;
				case 5:
					//TODO
					break;
				case 6:
					//TODO
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
					break;
				case 8:
					//Shop
					break;
				case 9:
					//Save
					break;
				case 10:
					//Load
					break;
				case 0:
					//exit
					System.out.println("Are you sure? Don't forget to save first!\n0. Yes, I'm Leaving!\n1. Nevermind!");
					choice = input.nextInt();
				default:
					System.out.println("badInput");
			}
			choice = -1;
		}
	}
	public static void showList(ArrayList<Alien> inAliens) {
		for(int i = 0; i < inAliens.size(); i++) {
			Alien current = inAliens.get(i);
			System.out.println((i + 1) + ". " + current);
		}
	}
	//TODO WIP
	public static void save(Profile inUser, String name) {
		try {
			File save = new File("..\\Saves\\");
			System.out.println(0/0);
		}
		catch(Exception e) {
			System.out.println("There was a problem! " + e);
		}
	}
	public static void checkLevelUp(Profile inUser) {
		if(inUser.getExp() >= Math.pow((inUser.getLevel() * 10), 2)) {
			inUser.setExp(-(int)(Math.pow((inUser.getLevel() * 10), 2)));
			inUser.setLevel(inUser.getLevel() + 1);
			System.out.println("You leveled up!");
		}
	}
}
