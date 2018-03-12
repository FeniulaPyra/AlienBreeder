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
				toDisplay.setName(JOptionPane.showInputDialog("Please type the new name: "));
				updateLabels();
			}
		});
		breed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//opens box to select another alien, presents possible offspring to confirm, breeds aliens
				//es muy dificil!
				//TODO babies don't have names!
				//TODO messy!
				//TODO 
				ArrayList<Alien> tempAliens = new ArrayList<Alien>(user.aliens);
				tempAliens.remove(selection.getSelectedItem());
				
				Alien otherAlien = (Alien)JOptionPane.showInputDialog(null, "Pick a second alien:", "Breeding", JOptionPane.PLAIN_MESSAGE, null, tempAliens.toArray(), null);
				if(JOptionPane.showInputDialog(null, "Are you sure? these are the possible offspring:", "Breeding Confirm", JOptionPane.PLAIN_MESSAGE, null, ((Alien)selection.getSelectedItem()).generatePotentialOffspring(otherAlien).toArray(), null) != null) {
					ArrayList<Alien> maybeBabies = ((Alien)selection.getSelectedItem()).generatePotentialOffspring(otherAlien);
					
					Alien baby = (maybeBabies.get((int)(Math.random() * maybeBabies.size())));
					user.add(baby);
					user.addExp(baby.getValue()/2);
					
					JOptionPane.showMessageDialog(null, "YAY! You got a(n) " + baby);
					updateComboBox();
				}
			}
		});
		compete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO WIP
				//needs to generate a bunch of other aliens (or just 1) and start competition
				//idk what the competition will be o dear
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
	}
	/**
	 * Updates the alien panel labels to show the selected alien's information, as well as have the
	 * jcombobox show the selected aliens.
	 */
	//TODO there is a weird thing here it adds the aliens twice?
	public void updateComboBox() {
		selection.removeAllItems();
		for(Alien adding : user.aliens) {
			selection.addItem(adding);
		}
	}
	public void updateLabels() {
		/*selection.removeAllItems();
		//TODO what the heck it runs the following 5 lines of code twice, but not the previous one???
		System.out.println("after deleting");
		for(int i = 0; i < user.aliens.size(); i++) {
			selection.addItem(user.aliens.get(i));
			System.out.println(selection.getItemCount() + "adding one to alien selection" + user.aliens.size());
		}
		System.out.println("out");
		for(Alien adding : user.aliens) {
			selection.addItem(adding);
			System.out.println("adding one to alien selection");
		}*/
		toDisplay = (Alien)selection.getSelectedItem();
		
		nameLabel.setText("Name: " + toDisplay.getName());
		value.setText("$" + toDisplay.getValue());
		intel.setText("Intelligence: " + toDisplay.getIntel());
		strength.setText("Strength: " + toDisplay.getStrength());
	}
}
