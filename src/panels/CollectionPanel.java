package panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
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
import items.InventoryItem;

public class CollectionPanel extends JPanel{
	JPanel buttonPanel;
	JScrollPane listPanel;
	
	JButton showArts;
	JButton showMyArts;
	JButton showAliens;
	JButton showMyAliens;
	
	JList<InventoryItem> achievements;
	DefaultListModel<InventoryItem> achievementList;
	
	Profile user;
	
	public CollectionPanel(Profile inUser) {
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		
		
		showArts = new JButton("Artifacts");
		showMyArts = new JButton("My artifacts");
		showAliens = new JButton("Aliens");
		showMyAliens = new JButton("My Aliens");
		
		achievementList = new DefaultListModel<InventoryItem>();

		achievements = new JList<InventoryItem>(achievementList);
		achievements.setCellRenderer(new CollectionCellRenderer());
		//achievements.setModel(achievementList);
		listPanel = new JScrollPane(achievements);
		
		this.setLayout(new GridLayout(1, 2));
		this.add(listPanel);
		this.add(buttonPanel);
		
		//listPanel.add(achievements);
		buttonPanel.add(showAliens);
		buttonPanel.add(showMyAliens);
		buttonPanel.add(showArts);
		buttonPanel.add(showMyArts);
		
		user = inUser;
		
		add(buttonPanel);
		add(listPanel);
		collectionSetup();
	}
	
	public void collectionSetup() {
		showAliens.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAliens.setText("Loading...");
				
				achievementList.removeAllElements();
				
				ArrayList<Alien> allAliens = General.getAllAliens(false);
				
				for(int i = 0; i < allAliens.size(); i++) {
					achievementList.addElement(allAliens.get(i));
				}				
				showAliens.setText("Aliens");
			}
		});
		showMyAliens.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showMyAliens.setText("Loading...");
				
				achievementList.removeAllElements();
				
				ArrayList<Alien> gotten = General.getAllAliens(true);
				
				for(int i = 0; i < gotten.size(); i++) {
					achievementList.addElement(gotten.get(i));
				}
				
				showMyAliens.setText("My Aliens");
			}
		});
		showArts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				achievementList.removeAllElements();
				
				for(int i = 0; i < General.getArtifactSize(); i++) {
					achievementList.addElement(General.getArt(i));
				}
				
				achievements.setModel(achievementList);
			}
		});
		showMyArts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				achievementList.removeAllElements();
				
				for(int i = 0; i < General.getArtifactSize(); i++) {
					if(General.haveArtifact(i)) {
						achievementList.addElement(General.getArt(i));
					}
				}
				
				achievements.setModel(achievementList);
			}
		});
	}
}


class CollectionCellRenderer extends JLabel implements ListCellRenderer {

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				
		if((((InventoryItem)value).getItemType() == 'a' && General.haveAlien((Alien)value)) || (((InventoryItem)value).getItemType() == 'r' && General.haveArtifact((Artifact)value))){
			setBackground(Color.green);
		}
		else {
			setBackground(Color.gray);
		}
		setText(((InventoryItem)value).getName());
		setOpaque(true);
		return this;
	}
}
