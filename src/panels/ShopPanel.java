package panels;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	
	JLabel buyInfo;
	
	//buy button
	JButton buy;
	//sell button
	JButton sell;
	
	Profile user;
	
	/**
	 * Creates the shop panel, where the user can buy an artifact and sell their items for currency.
	 * @param inUser This is so that the panel can add currency and artifacts to the user's inventory, and remove items from the user's inventory.
	 */
	public ShopPanel(Profile inUser) {
		//sets the layout
		setLayout(new GridLayout(4, 1));
		ArrayList<InventoryItem> inventory = new ArrayList<InventoryItem>();
		
		//preps the sellable inventory items. Does not include aliens because aliens are sold from the alien panel.
		try {
			inventory.addAll(inUser.artifacts);
			inventory.addAll(inUser.other);
		}
		catch(NullPointerException n) {
		}
		//creates objects to sell/buy
		toSell = new JComboBox<InventoryItem>(inventory.toArray(new InventoryItem[inventory.size()]));
		toBuy = new Artifact(inUser.getLevel());
		
		sell = new JButton("Sell");
		buy = new JButton("Buy");
		
		buyInfo = new JLabel("$" + toBuy.getValue() + " " + toBuy.getName());
		
		add(toSell);
		add(sell);
		add(buyInfo);
		add(buy);
		
		user = inUser;
	}
	/**
	 * Sets up the ActionListener for the buttons.
	 */
	public void shopSetup() {
		buy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toSell.addItem(toBuy);
				user.add(toBuy);
				user.addCoins(toBuy.getValue() * 2);
				toBuy = new Artifact(user.getLevel());
				shopUpdate();
			}
		});
		sell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				user.addCoins(((InventoryItem)toSell.getSelectedItem()).getValue());
				toSell.removeItem(toSell.getSelectedItem());
				
			}
		});
	}
	public void shopUpdate() {
		buyInfo.setText("$" + toBuy.getValue() + " " + toBuy.getName());
	}
}
