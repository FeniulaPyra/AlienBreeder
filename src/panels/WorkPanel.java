package panels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import items.*;
import general.*;

//TODO use a nested flow-layout in a 2-row, 1-column gridlayout << apparently i did not do that :/
//GAH ^

public class WorkPanel extends JPanel {
	//buttons
	JButton keep;
	JButton discard;
	JButton goHome;
	JButton drop;
	
	//the pocket of items for the jcombobox
	JComboBox<InventoryItem> items;
	
	//the name of the item presented
	JLabel itemName;
	
	//the pocket of items. there is no stacking!
	ArrayList<InventoryItem> pocket = new ArrayList<InventoryItem>();
	
	//the selected item
	InventoryItem item;
	
	//the user
	Profile user;
	
	/**
	 * Creates the panel the user uses for "work," or collecting random objects.
	 * 
	 * @param inUser This is so that the panel can add things to the user's inventory.
	 */
	public WorkPanel(Profile inUser) {
		//creates all of the components in the work panel
		keep = new JButton("Keep");
		discard = new JButton("Discard");
		goHome = new JButton("Go Home");
		drop = new JButton("Drop Item");
		items = new JComboBox<InventoryItem>(pocket.toArray(new InventoryItem[pocket.size()]));
		itemName = new JLabel("");
		
		//sets the layout
		setLayout(new GridLayout(7, 1));
		
		//sets the user
		user = inUser;
		
		//adds each component
		add(itemName);
		add(keep);
		add(discard);
		add(new JLabel("Pocket:")); //This is a constant, so we don't really need to store it elsewhere.
		add(items);
		add(drop);
		add(goHome);
		
		//sets up the workpanel.
		workSetup();
		loadItem();
	}
	
	/**
	 * Sets up the actionListeners for the work buttons and the item pocket for the user
	 */
	public void workSetup() {
		keep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//adds the item to the user's pocket if there is room and loads a new item.
				if(pocket.size() < user.getPocket()) {
					pocket.add(item);
					items.addItem(item);
				}
				loadItem();
				
			}
		});
		discard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//loads a new item and does not keep the current item
				loadItem();
			}
		});
		goHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO adds all of the current pocket items to the user's inventory.
				user.addMany(pocket);
				pocket.removeAll(pocket);
				items.removeAllItems();
				MainGame.shop.shopUpdate();
			}
		});
		drop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//removes the selected item from the user's pocket
				pocket.remove(items.getSelectedIndex());
				items.removeItemAt(items.getSelectedIndex());
			}
		});
		items.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
	}
	
	/**
	 * Creates either a junk item (74% chance), an artifact (25% chance), or an alien (1% chance);
	 */
	public void loadItem() {
		int random = (int)(Math.random() * 100);
		if(random == 0)
			item = new Alien(user.getLevel());
		else if(random > 0 && random < 25)
			item = new Artifact(user.getLevel());
		else 
			item = new Junk();
		itemName.setText(item.getName());
	}
	
	/**
	 * Sends the user's pocket out. This is supposed to be for adding the items in the pocket to the user's inventory.
	 * @return An ArrayList of InventoryItems that is the user's pocket.
	 */
	public ArrayList<InventoryItem> dump() {
		/*for(int i = 0; i < items.getItemCount(); i++) {
			pocket.add(items.getItemAt(i));
		}*/
		return pocket;
	}
}