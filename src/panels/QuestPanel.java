package panels;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import general.Profile;

public class QuestPanel extends JPanel {
	JLabel singleQuest;
	JLabel groupQuest;
	JButton completeSingle;
	JButton completeGroup;
	
	Profile user;
	
	public QuestPanel(Profile inUser) {
		user = inUser;
	}
	
}
