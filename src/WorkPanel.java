import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

//TODO use a nested flow-layout in a 2-row, 1-column gridlayout
//GAH ^

public class WorkPanel extends JPanel {
	JButton keep;
	JButton discard;
	JButton goHome;
	
	ArrayList<InventoryItem> pocket = new ArrayList<InventoryItem>();
	//NOTE each object takes one slot. there is NO stacking
	InventoryItem item;
	
	Profile user;
	
	public WorkPanel(Profile inUser) {
		keep = new JButton("Keep");
		discard = new JButton("Discard");
		goHome = new JButton("Go Home");
		user = inUser;
		this.setVisible(true);
	}
	
	public void buttonSetupWorkScreen() {
		keep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(pocket.size() <= user.getPocket()) {
					pocket.add(item);
				}
				loadItem();
			}
		});
		discard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadItem();
			}
		});
		goHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
	}
	
	public void loadItem() {
		int random = (int)(Math.random() * 100);
		if(random == 0)
			item = new Alien(user.getLevel());
		else if(random > 0 && random < 50)
			item = new Artifact(user.getLevel());
		else 
			item = new Junk();
	}
}