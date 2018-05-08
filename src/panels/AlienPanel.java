package panels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import items.*;
import general.*;

//TODO use a boxlayout nested in a 2-column, 1-row gridlayout
//Gah ^
public class AlienPanel extends JPanel {
	
	public final String[] COMPETITIONS = {"Intelligence", "Strength", "Cancel"};
	public final String[] DICE = {"No", "2-sided ($20)", "4-sided ($40)", "6-sided ($60)"};
	
	public JButton sell; //opens confirmation box, yes sells, no cancels
	public JButton rename; //opens dlg box with input, takes new name, has cancel button
	public JButton breed; //opens dlg box with list of other aliens. upon selection, opens another dlg box showing possible offspring, upon confirm, breed, upon cancel, go back to prev box
	public JButton compete; //idk man TODO
	public JButton update;
	
	public JLabel nameLabel; //the alien's breed, pattern, color, etc
	public JLabel value;
	public JLabel intel;
	public JLabel strength;
	public JLabel picture; //TODO will probably have to switch this to a graphics pane or something because recoloring. for now, leave it
	
	public JComboBox<Alien> selection;
	public Alien toDisplay;
	public ImageIcon breedPic; //<< need the other things too.
	
	public Profile user;
	
	/**
	 * Creates a panel for managing aliens, given the user's profile.
	 * 
	 * @param inUser This is used for adding/deleting aliens to/from the user's inventory
	 */
	public AlienPanel(Profile inUser) {
		//creates all of the buttons
		sell = new JButton("Sell");
		rename = new JButton("Rename");
		breed = new JButton("Breed");
		compete = new JButton("Compete (WIP) ");
		nameLabel = new JLabel("Name: ");
		value = new JLabel("$");
		intel = new JLabel("Intelligence: ");
		strength = new JLabel("Strength");
		
		//sets to null because i haven't done this yet TODO
		breedPic = null;
		picture = null;
		
		//populates the combo box with the user's aliens
		selection = new JComboBox<Alien>(inUser.aliens.toArray(new Alien[inUser.aliens.size()]));
		//shows the first alien in the user's inventory. 
		selection.setSelectedItem(0);
		toDisplay = (Alien)selection.getSelectedItem();
		
		this.setLayout(new GridLayout(9, 1));
		
		//adds each component
		add(selection);
		add(nameLabel);
		add(value);
		add(intel);
		add(strength);
		add(rename);
		add(breed);
		add(compete);
		add(sell);
		
		user = inUser;
		
		alienSetup();
		updateLabels();
	}
	
	/**
	 * Sets up the actionListeners for all of the components in the alien panel
	 */
	private void alienSetup() {
		sell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//opens confirmation box
				//if yes...
				if(JOptionPane.showConfirmDialog(null, "Do you want to sell this" + toDisplay.getName() + "alien?", "Sell Alien", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
					user.addCoins(toDisplay.getValue());
					user.aliens.remove(selection.getSelectedItem());
					selection.removeItemAt(selection.getSelectedIndex());
				}
			}
		});
		rename.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//opens input dialog box and asks for new name.
				String newName = JOptionPane.showInputDialog("Please type the new name: ");
				if(newName != null )
					toDisplay.setName(newName);
				updateLabels();
			}
		});
		breed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ArrayList<Alien> tempAliens = new ArrayList<Alien>(user.aliens);
				tempAliens.remove(selection.getSelectedItem());
				
				Alien otherAlien = (Alien)JOptionPane.showInputDialog(null, "Pick a second alien:", "Breeding", JOptionPane.PLAIN_MESSAGE, null, tempAliens.toArray(), null);
				if(otherAlien != null && JOptionPane.showInputDialog(null, "Are you sure? these are the possible offspring:", "Breeding Confirm", JOptionPane.PLAIN_MESSAGE, null, ((Alien)selection.getSelectedItem()).generatePotentialOffspring(otherAlien).toArray(), null) != null) {
					ArrayList<Alien> maybeBabies = ((Alien)selection.getSelectedItem()).generatePotentialOffspring(otherAlien);
					
					Alien baby = (maybeBabies.get((int)(Math.random() * maybeBabies.size())));
					user.add(baby);
					user.addExp(baby.getValue()/2);
					
					JOptionPane.showMessageDialog(null, "YAY! You got a(n) " + baby);
					updateComboBox();
				}
			}
		});
		selection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selection.getItemCount() > 0) {
					//gets the selected item and displays its name and breed
					updateLabels();
				}
			}
		});
		compete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Alien opponent = new Alien(user.getLevel());
				Alien self = (Alien)selection.getSelectedItem();
				int newIntel = 1;
				int sides;
				//TODO this can probably be made more efficient and pretty and stuff
				switch(JOptionPane.showOptionDialog(null, "What do you want to compete in?", "Compete!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, COMPETITIONS, COMPETITIONS[0])) {
					case 0: //Intelligence Competition
						//shows the opponent's stat and allows the user to roll a dice
						sides = JOptionPane.showOptionDialog(null, "Your oponent has this Intelligence Level: " + opponent.getIntel() + "\nRoll a dice?",
								"Intelligence Competition",
								JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.QUESTION_MESSAGE,
								null,
								DICE,
								DICE[0]) * 2;
						 
						//subtracts the cost of the dice, unless the user can't pay
							if(sides * 10 > user.getCoins())
								sides = 0;
							else
								user.addCoins(-(sides * 10));
						 
						//multiplies the new intelligence
						newIntel = self.getIntel() * ((int)(Math.random() * sides) + 1);

						//shows if the user won!
						if(opponent.getIntel() > newIntel) {
							 
							//if they lost, subtract coins based on the dice they rolled
							JOptionPane.showMessageDialog(null, "Oh no! Your opponent won... -$" + (sides * 10 + 10) + " ...");
							
							//subtracts the coins
								if(sides * 10 + 10 > user.getCoins())
									user.addCoins(-(sides * 10 + 10));
								else
									user.addCoins(-user.getCoins());
						}
						else if(opponent.getIntel() < newIntel) {
							 
							//if they won, they get their opponent alien!
							JOptionPane.showMessageDialog(null, "YAY! YOU WON! You win the alien you competed against! It's a(n) " + opponent.getName());
							user.add(opponent);
						}
						else
							 
							//if it's a tie, nothing happens
							JOptionPane.showMessageDialog(null, "Oh! It was a tie! Nothing happens.");
						break;
						
					case 1: //Strength Competition
						//shows the opponent and allows the user to roll a dice to increase their chances
						sides = JOptionPane.showOptionDialog(null, "Your oponent has this Intelligence Level: " + opponent.getStrength() + "\nRoll a dice?",
								"Intelligence Competition",
								JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.QUESTION_MESSAGE,
								null,
								DICE,
								DICE[0]) * 2;
						
						//subtracts the cost of the dice, unless the user can't pay
						if(sides * 10 > user.getCoins())
							sides = 0;
						else
							user.addCoins(-(sides * 10));
						
						//creates the new statistic
						newIntel = self.getStrength() * ((int)(Math.random() * sides) + 1);
						
						//checks if the user won!
						if(opponent.getStrength() > newIntel) {
							
							//If the user lost, subtract the amount of sides plus 10
							JOptionPane.showMessageDialog(null, "Oh no! Your opponent won... -$" + (sides * 10 + 10) + " ...");
							
							//subtracts the coins
							if(sides * 10 + 10 > user.getCoins())
								user.addCoins(-(sides * 10 + 10));
							else
								user.addCoins(-user.getCoins());
						}
						else if(opponent.getStrength() < newIntel) {
							
							//if the user won, they get the alien they competed against!
							JOptionPane.showMessageDialog(null, "YAY! YOU WON! You win the alien you competed against! It's a(n) " + opponent.getName());
							user.add(opponent);
						}
						else
							
							//if it was a tie, nothing happens
							JOptionPane.showMessageDialog(null, "Oh! It was a tie! Nothing happens.");
						break;
					default:
				}
				updateComboBox();
			}
		});
	}
	/**
	 * Updates the alien panel labels to show the selected alien's information, as well as have the
	 * jcombobox show the selected aliens.
	 */
	public void updateComboBox() {
		//selection.removeAllItems();
		for(int i = 0; selection.getItemCount() > 0; i++) {
			//System.out.println("thing at 0 " + selection.getItemAt(0));
			selection.removeItemAt(0);
		}
		//DefaultComboBoxModel<Alien> things = new DefaultComboBoxModel<Alien>(user.aliens.toArray(new Alien[user.aliens.size()]));
		//selection.setModel(things);
		//for(int i = 0; i < things.getSize(); i++) { System.out.println("in box" + selection.getItemAt(i));}
		for(Alien adding : user.aliens) {
			selection.addItem(adding);
			//System.out.println(adding);
			//System.out.println("actual " + selection.getSelectedItem());
			updateLabels();
		}
	}
	public void updateLabels() {
		toDisplay = (Alien)selection.getSelectedItem();
		try {
			nameLabel.setText("Name: " + toDisplay.getName());
			value.setText("$" + toDisplay.getValue());
			intel.setText("Intelligence: " + toDisplay.getIntel());
			strength.setText("Strength: " + toDisplay.getStrength());
		} catch(NullPointerException n) {
			
		}
	}
}
