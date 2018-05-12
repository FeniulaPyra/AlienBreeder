package panels;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import general.General;
import general.Profile;
import items.Alien;
import items.Breed;

public class QuestPanel extends JPanel {
	//The quest descriptions
	JLabel singleQuest;
	JLabel groupQuest;
	
	//The quest completion buttons
	JButton completeSingle;
	JButton completeGroup;
	
	//the alien for the single quest
	Alien single;
	
	Breed multiBreed; 
	String multiValue; //can be any of the non-breed values
	ArrayList<Alien> multi;
	
	//the user
	Profile user;
	
	/**
	 * Creates the quest panel where the user can complete a single quest, where the user breeds a SINGLE alien, and a group quest, where
	 * the user breeds a COLLECTION of aliens.
	 * @param inUser This is so that the panel can remove the quest alien(s) from the user inventory.
	 */
	public QuestPanel(Profile inUser) {
		//sets the user
		user = inUser;

		single = General.getRandAlien(user.getLevel());
		
		multiBreed = General.getRandomBreed(user.getLevel());
		switch((int)(Math.random() * 3)) {
			case 0:
				multiValue = General.getRandomPattern();
				break;
			case 1:
				multiValue = General.getRandomPatternColor();
				break;
			default:
				multiValue = General.getRandomColor();
		}
		multi = General.getAliensOfType(multiBreed, multiValue);
		
		//sets the quests
		singleQuest = new JLabel("" + single);
		groupQuest = new JLabel("Type: " + multiValue + "\n Breed: " + multiBreed);
		
		//creates the complete buttons
		completeSingle = new JButton("Complete Single");
		completeGroup = new JButton("Complete Group");
		
		//sets layout
		setLayout(new GridLayout(4, 1));
		
		//adds all of the components
		add(singleQuest);
		add(completeSingle);
		add(groupQuest);
		add(completeGroup);
		questSetup();
	}
	public void questSetup() {
		completeSingle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				boolean hasAlien = false;
				int alien = 0;
				
				for(int i = 0; i < user.aliens.size(); i++) {
					if(user.aliens.get(i).equals(single)) {
						hasAlien = true;
						alien = i;
					}
				}
				if(hasAlien) {
					user.addExp(single.getValue() * 2);
					user.addCoins(single.getValue() * 2);
					user.aliens.remove(alien);
					single = new Alien(user.getLevel());
					System.out.println(single);
					updateQuests();
				}
			}
		});
		completeGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean hasAll = false;
				boolean missingAny = false;
				for(int u = 0; u < user.aliens.size() && !missingAny; u++) {
					for(int q = 0; q < multi.size(); q++) {
						if(user.aliens.get(u).equals(multi.get(q)))
							hasAll = true;
					}
					missingAny = !hasAll;
				}
				if(hasAll) {
					for(int i = 0; i < multi.size(); i++) {
						user.addExp(multi.get(i).getValue() * 2);
						user.addCoins(multi.get(i).getValue() * 2);
						user.aliens.remove(multi.get(i));
					}
					multiBreed = General.getRandomBreed(user.getLevel());
					switch((int)(Math.random() * 3)) {
						case 0:
							multiValue = General.getRandomPattern();
							break;
						case 1:
							multiValue = General.getRandomPatternColor();
							break;
						default:
							multiValue = General.getRandomColor();
					}
					multi = General.getAliensOfType(multiBreed, multiValue);
					updateQuests();
				}
			}
		});
	}
	public void updateQuests() {
		singleQuest.setText("" + single);// = new JLabel("" + single);
		System.out.println(single);
		groupQuest.setText("Type: " + multiValue + "\n Breed: " + multiBreed);
	}
	
}
