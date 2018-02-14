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

public class QuestPanel extends JPanel {
	//The quest descriptions
	JLabel singleQuest;
	JLabel groupQuest;
	
	//The quest completion buttons
	JButton completeSingle;
	JButton completeGroup;
	
	//the alien for the single quest
	Alien single;
	String multiValueA; //can be any of the three non-breed values
	String multiValueB;
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
		
		//sets the quests
		singleQuest = new JLabel("");
		groupQuest = new JLabel("Type: \n");
		
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
	}
	public void questSetup() {
		completeSingle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(user.aliens.contains(single)) {
					user.addExp(single.getValue() * 2);
					user.addCoins(single.getValue() * 2);
					user.aliens.remove(single);
					single = new Alien(user.getLevel());
				}
			}
		});
		completeGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(user.aliens.containsAll(multi)) {
					
				}
			}
		});
	}
	
}
