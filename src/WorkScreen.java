import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class WorkScreen {
	JButton back;
	JButton keep;
	JButton discard;
	
	ArrayList<Object> pocket = new ArrayList<Object>();
	//NOTE each object takes one slot. there is NO stacking
	
	public WorkScreen() {
		back = new JButton("Back");
		keep = new JButton("Keep");
		discard = new JButton("Discard");
	}
	public void buttonWatcherWorkScreen() {
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//exits
				//adds all pocket things to respective arrays in main
			}
		});
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
}