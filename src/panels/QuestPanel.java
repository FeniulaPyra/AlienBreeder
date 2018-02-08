package panels;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import general.General;
import general.Profile;
import items.Alien;

public class QuestPanel extends JPanel {
	//The quest descriptions
	JTextArea singleQuest;
	JLabel groupQuest;
	
	//The quest completion buttons
	JButton completeSingle;
	JButton completeGroup;
	
	//the alien for the single quest
	Alien single;
	
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
		singleQuest = new JTextArea("");
		groupQuest = new JLabel("Type: \n");
		
		//creates the complete buttons
		completeSingle = new JButton("Complete Single");
		completeGroup = new JButton("Complete Group");
		
		//sets layout
		setLayout(new GridLayout(2, 2));
		
		//adds all of the components
		add(singleQuest);
		add(completeSingle);
		add(groupQuest);
		add(completeGroup);
	}
	
}
