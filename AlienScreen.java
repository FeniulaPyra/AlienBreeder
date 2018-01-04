import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

//TODO use a boxlayout nested in a 2-column, 1-row gridlayout
//Gah ^
public class AlienScreen extends JFrame {
	public JButton sell; //opens confirmation box, yes sells, no cancels
	public JButton rename; //opens dlg box with input, takes new name, has cancel button
	public JButton breed; //opens dlg box with list of other aliens. upon selection, opens another dlg box showing possible offspring, upon confirm, breed, upon cancel, go back to prev box
	public JButton compete; //idk man TODO
	
	public JLabel name; //alien name
	public JLabel breedLabel; //the alien's breed, pattern, color, etc
	public JLabel picture; //TODO will probably have to switch this to a graphics pane or something because recoloring. for now, leave it
	
	public JComboBox<Alien> selection;
	public Alien toDisplay;
	
	AlienScreen(Profile inUser) {
		sell = new JButton("Sell");
		rename = new JButton("Rename");
		breed = new JButton("Breed");
		compete = new JButton("Compete (WIP) ");
		
		//populates the combo box with the user's aliens
		selection = new JComboBox<Alien>((Alien[])inUser.aliens.toArray());
		//shows the first alien in the user's inventory. 
		selection.setSelectedItem(inUser.aliens.get(0));
		toDisplay = (Alien)selection.getSelectedItem();
	}

	private void alienButtonSetup() {
		sell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//opens confirmation box
				//if yes...
				if(JOptionPane.showConfirmDialog(null, "Do you want to sell this" + toDisplay.getName() + "alien?", "Sell Alien", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
					
				}
			}
		});
		rename.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//opens input dialog box and asks for new name.
				toDisplay.setName(JOptionPane.showInputDialog("Please type the new name: ")); //TODO Check for possible problems with hexadress: not sure if JCB does copy constructors or not.
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
				MainGame.WIP();
			}
		});
	}
}
