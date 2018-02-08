package panels;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.*;

import general.General;
import general.Profile;
import items.Artifact;
import items.InventoryItem;

public class ShopPanel extends JPanel {
	//The items the user can sell
	JComboBox toSell;
	
	//the artifact the user can buy
	Artifact toBuy;
	
	//buy button
	JButton buy;
	//sell button
	JButton sell;
	
	/**
	 * Creates the shop panel, where the user can buy an artifact and sell their items for currency.
	 * @param inUser This is so that the panel can add currency and artifacts to the user's inventory, and remove items from the user's inventory.
	 */
	public ShopPanel(Profile inUser) {
		//sets the layout
		setLayout(new GridLayout(4, 1));
		ArrayList<InventoryItem> inventory = new ArrayList<InventoryItem>();
		
		//preps the sellable invnetory items. Does not include aliens because aliens are sold from the alien panel.
		inventory.addAll(inUser.artifacts);
		inventory.addAll(inUser.other);
		
		//creates objects to sell/buy
		toSell = new JComboBox<InventoryItem>((InventoryItem[])inventory.toArray());
		toBuy = new Artifact(inUser.getLevel());
	}
	/**
	 * Sets up the ActionListener for the buttons.
	 */
	public void shopSetup() {
		
	}
}
