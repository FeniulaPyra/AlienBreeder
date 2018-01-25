package panels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	
	public AlienPanel(Profile inUser) {
		sell = new JButton("Sell");
		rename = new JButton("Rename");
		breed = new JButton("Breed");
		compete = new JButton("Compete (WIP) ");
		nameLabel = new JLabel("Name: ");
		value = new JLabel("$");
		intel = new JLabel("Intelligence: ");
		strength = new JLabel("Strength");
		
		breedPic = null;
		picture = null;
		
		//populates the combo box with the user's aliens
		selection = new JComboBox<Alien>(inUser.aliens.toArray(new Alien[inUser.aliens.size()]));
		//shows the first alien in the user's inventory. 
		selection.setSelectedItem(null);
		toDisplay = null;
		
		this.setLayout(new GridLayout(9, 1));
		
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
	}

	private void alienSetup() {
		sell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//opens confirmation box
				//if yes...
				if(JOptionPane.showConfirmDialog(null, "Do you want to sell this" + toDisplay.getName() + "alien?", "Sell Alien", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
					user.addCoins(toDisplay.getValue());
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
			}
		});
		compete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO WIP
			}
		});
		selection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selection.getSelectedItem() != null) {
					//gets the selected item and displays its name and breed
					updateLabels();
				}
			}
		});
	}
	public void updateLabels() {
		toDisplay = (Alien)selection.getSelectedItem();
		
		nameLabel.setText("Name: " + toDisplay.getName());
		value.setText("$" + toDisplay.getValue());
		intel.setText("Intelligence: " + toDisplay.getIntel());
		strength.setText("Strength: " + toDisplay.getStrength());
	}
}
