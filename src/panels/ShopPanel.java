package panels;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.*;

import general.General;
import general.Profile;
import items.Artifact;
import items.InventoryItem;

public class ShopPanel extends JPanel {
	JComboBox toSell;
	Artifact toBuy;
	
	JButton buy;
	JButton sell;
	
	public ShopPanel(Profile inUser) {
		setLayout(new GridLayout(4, 1));
		ArrayList<InventoryItem> inventory = new ArrayList<InventoryItem>();
		inventory.addAll(inUser.artifacts);
		inventory.addAll(inUser.other);
		
		toSell = new JComboBox<InventoryItem>((InventoryItem[])inventory.toArray());
		toBuy = new Artifact(inUser.getLevel());
	}
	
	public void shopSetup() {
		
	}
}
