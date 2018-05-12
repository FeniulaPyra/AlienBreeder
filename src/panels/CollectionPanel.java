package panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ScrollPaneLayout;

import general.General;
import general.Profile;
import items.Alien;
import items.Artifact;
import items.Breed;
import items.InventoryItem;

public class CollectionPanel extends JPanel{
	JPanel buttonPanel;
	JScrollPane listPanel;
	
	JLabel instructions;
	
	JButton showArts;
	JButton showMyArts;
	JButton showMyAliens;
	
	JComboBox breedToShow;
	DefaultComboBoxModel<Breed> breedsList;
	
	JList<InventoryItem> achievements;
	DefaultListModel<InventoryItem> achievementList;
	
	Profile user;
	
	public CollectionPanel(Profile inUser) {
		//sets up the button panel
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		
		//sets up the buttons
		showArts = new JButton("Show Artifacts");
		showMyArts = new JButton("My artifacts");
		showMyAliens = new JButton("My Aliens");
		
		//Sets up the list of aliens
		achievementList = new DefaultListModel<InventoryItem>();
		achievements = new JList<InventoryItem>(achievementList);
		achievements.setCellRenderer(new CollectionCellRenderer());
		
		//sets up the scrollpane
		listPanel = new JScrollPane(achievements);
		
		//sets up the breed selector
		breedsList = new DefaultComboBoxModel<Breed>();
		breedToShow = new JComboBox<Breed>(breedsList);
		breedToShow.setMaximumSize(new Dimension(200, 20));
		breedToShow.setSelectedItem(null);
		
		for(int i = 0; i < General.BREEDS.length; i++)
			breedsList.addElement(General.BREEDS[i]);

		//sets the formatting in the frame
		this.setLayout(new GridLayout(1, 2));
		this.add(listPanel);
		this.add(buttonPanel);
		
		//adds all of the buttons and things
		buttonPanel.add(breedToShow);
		buttonPanel.add(showMyAliens);
		buttonPanel.add(showArts);
		buttonPanel.add(showMyArts);
		
		user = inUser;
		
		//adds the panels to the frame and sets up listeners
		add(buttonPanel);
		add(listPanel);
		collectionSetup();
	}
	
	//sets up actionlisteners
	public void collectionSetup() {
		//when user changes shown breed
		breedToShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//clears list
				achievementList.removeAllElements();
				
				ArrayList<Alien> allAliens = General.getAllAliens(false);
				
				//adds all the aliens of that breed
				for(int i = 0; i < allAliens.size(); i++) {
					if(allAliens.get(i).getBreed().equals(breedToShow.getSelectedItem()))
						achievementList.addElement(allAliens.get(i));
				}
			}
		});
		//shows all the aliens the user has ever gotten
		showMyAliens.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//clears list
				achievementList.removeAllElements();
	
				ArrayList<Alien> gotten = General.getAllAliens(true);
				
				//adds all aliens user has
				for(int i = 0; i < gotten.size(); i++) {
					achievementList.addElement(gotten.get(i));
				}
				
			}
		});
		//shows all artifacts
		showArts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//clears list
				achievementList.removeAllElements();
				
				ArrayList<Artifact> gotten = General.getAllArts(false);
				
				//adds all the arts 
				for(int i = 0; i < gotten.size(); i++) {
					achievementList.addElement(gotten.get(i));
				}
				
				//achievements.setModel(achievementList);
			}
		});
		showMyArts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				achievementList.removeAllElements();
				
				ArrayList<Artifact> gotten = General.getAllArts(true);
				
				for(int i = 0; i < gotten.size(); i++) {
					achievementList.addElement(gotten.get(i));
				}
				
				//achievements.setModel(achievementList);
			}
		});
	}
}


class CollectionCellRenderer extends JLabel implements ListCellRenderer {

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		if(((InventoryItem)value).getItemType() == 'r'){
			
			ArrayList<Artifact> gotten = General.getAllArts(true);
			boolean haveArt = false;
			
			//because this program hates the haveArtifact method. -_-
			for(int i = 0; i < gotten.size(); i++) {
				if(gotten.get(i).toString().equals(((Artifact)value).toString())) {
					haveArt = true;
				}
			}
			if(haveArt)
				//colors it green if have the art
				setBackground(Color.green);
			else
				//colors gray if not
				setBackground(Color.gray);
		}
		else if(((InventoryItem)value).getItemType() == 'a' && General.haveAlien((Alien)value)){
			//colors green if have alien
			setBackground(Color.green);
		}
		else {
			//colors gray if not.
			setBackground(Color.gray);
		}
		setText(((InventoryItem)value).getName());
		setOpaque(true);
		return this;
	}
}
