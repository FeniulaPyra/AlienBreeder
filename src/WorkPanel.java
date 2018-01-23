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
	JButton drop;
	
	JComboBox<InventoryItem> items;
	
	JLabel itemName;
	
	ArrayList<InventoryItem> pocket = new ArrayList<InventoryItem>();
	//NOTE each object takes one slot. there is NO stacking
	InventoryItem item;
	
	Profile user;
	
	public WorkPanel(Profile inUser) {
		keep = new JButton("Keep");
		discard = new JButton("Discard");
		goHome = new JButton("Go Home");
		drop = new JButton("Drop Item");
		items = new JComboBox<InventoryItem>(pocket.toArray(new InventoryItem[pocket.size()]));
		itemName = new JLabel("");
		
		setLayout(new GridLayout(7, 1));
		
		user = inUser;
		add(itemName);
		add(keep);
		add(discard);
		add(new JLabel("Pocket:"));
		add(items);
		add(drop);
		add(goHome);
		
		buttonSetupWorkScreen();
		loadItem();
	}
	
	public void buttonSetupWorkScreen() {
		keep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(pocket.size() < user.getPocket()) {
					pocket.add(item);
					items.addItem(item);
				}
				loadItem();
				
			}
		});
		discard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadItem();
				//TODO do the thing yay
			}
		});
		goHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		items.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
	}
	
	public void loadItem() {
		int random = (int)(Math.random() * 100);
		if(random == 0)
			item = new Alien(user.getLevel());
		else if(random > 0 && random < 25)
			item = new Artifact(General.BREEDS[1], "Cardboard", "Cup");
		else 
			item = new Junk();
		itemName.setText(item.getName());
	}
	public ArrayList<InventoryItem> dump() {
		for(int i = 0; i < items.getItemCount(); i++) {
			pocket.add(items.getItemAt(i));
		}
		return pocket;
	}
}