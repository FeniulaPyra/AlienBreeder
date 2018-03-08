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
	JComboBox<InventoryItem> toSell;
	
	//the artifact the user can buy
	Artifact toBuy;
	
	JLabel buyInfo;
	JLabel sellInfo;
	
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
		setLayout(new GridLayout(5, 1));

		//creates objects to sell/buy
		toSell = new JComboBox<InventoryItem>();
		toBuy = new Artifact(inUser.getLevel());
		
		sell = new JButton("Sell");
		buy = new JButton("Buy");
		
		buyInfo = new JLabel("$" + toBuy.getValue() + " " + toBuy.getName());
		sellInfo = new JLabel("$" + 0);
		
		add(toSell);
		add(sellInfo);
		add(sell);
		add(buyInfo);
		add(buy);
		
		user = inUser;
		shopSetup();
		updateSellables();
	}
	/**
	 * Sets up the ActionListener for the buttons.
	 */
	public void shopSetup() {
		buy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toSell.addItem(toBuy);
				user.add(toBuy);
				user.addCoins(toBuy.getValue() * -2);
				toBuy = new Artifact(user.getLevel());
				shopUpdate();
				updateSellables();
			}
		});
		sell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(toSell.getSelectedItem() != null) {
					user.addCoins(((InventoryItem)toSell.getSelectedItem()).getValue());

					switch(((InventoryItem)toSell.getSelectedItem()).getItemType()) {
						case 'r':
							user.artifacts.remove(toSell.getSelectedItem());
							toSell.removeItem(toSell.getSelectedIndex());
							break;
						case 'j':
							user.other.remove(toSell.getSelectedItem());
							toSell.removeItem(toSell.getSelectedIndex());

							break;
						default:
							
					}
					shopUpdate();
					updateSellables();
				}
			}
		});
		toSell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shopUpdate();
			}
		});
	}
	public void shopUpdate() {
		buyInfo.setText("$" + (toBuy.getValue() * 2) + " " + toBuy.getName());
		try {
			sellInfo.setText("$" + ((InventoryItem)toSell.getSelectedItem()).getValue());
		}
		catch(NullPointerException n) {
			sellInfo.setText("$" + 0);
		}
	}
	public void updateSellables() {
		try {
			for(InventoryItem art : user.artifacts) {
				toSell.addItem(art);
			}
			for(InventoryItem junk : user.other) {
				toSell.addItem(junk);
			}
		}
		catch(NullPointerException n) {}
	}
}
