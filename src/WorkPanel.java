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
	
	ArrayList<Object> pocket = new ArrayList<Object>();
	//NOTE each object takes one slot. there is NO stacking
	Object Item;
	
	Profile customer;
	
	public WorkPanel(Profile inUser) {
		keep = new JButton("Keep");
		discard = new JButton("Discard");
		customer = inUser;
		this.setVisible(true);
	}
	public void buttonWatcherWorkScreen() {
		keep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//adds item to pocket
				//loads a new object
			}
		});
		discard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//loads a new item
			}
		});
	}
	public void loadItem() {
		switch((int)(Math.random() *3)) {
			case 0:
				
				break;
			case 1:
				break;
			case 2:
				break;
			default:
				break;
		}
	}
}