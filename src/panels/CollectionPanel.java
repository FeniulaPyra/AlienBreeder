package panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
		listPanel = new JScrollPane();
		
		showArts = new JButton("Artifacts");
		showMyArts = new JButton("My artifacts");
		showAliens = new JButton("Aliens");
		showMyAliens = new JButton("My Aliens");
		
		achievementList = new DefaultListModel<InventoryItem>();

		achievements = new JList<InventoryItem>();
		achievements.setCellRenderer(new CollectionCellRenderer());
		achievements.setModel(achievementList);
		
		this.setLayout(new GridLayout(1, 2));
		this.add(listPanel);
		this.add(buttonPanel);
		
		listPanel.add(achievements);
		
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
				achievementList.removeAllElements();
				
				int size = General.getAlienSize();
				
				for(int i = 0; i < size; i++) {
					achievementList.addElement(General.getAlien(i));
				}
				
				achievements.setModel(achievementList);
			}
		});
		showMyAliens.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				achievementList.removeAllElements();
				
				for(int i = 0; i < General.getAlienSize(); i++) {
					if(General.haveAlien(i)) {
						achievementList.addElement(General.getAlien(i));
					}
				}
				
				achievements.setModel(achievementList);
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


class CollectionCellRenderer extends DefaultListCellRenderer {
	public Component getListCellRendererComponent(JList list, InventoryItem value, int index, boolean isSelected, boolean cellHasFocus) {
		Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		if((value.getItemType() == 'a' && General.haveAlien((Alien)value)) || (value.getItemType() == 'r' && General.haveArtifact((Artifact)value))){
			c.setForeground(new Color(200, 255, 200));
		}
		else {
			c.setForeground(new Color(255, 200, 200));
		}
		return c;
	}
}
