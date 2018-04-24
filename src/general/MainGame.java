package general;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import items.*;
import panels.*;

//TODO link to sql, move everything from GENERAL into a database. << sorta done
//TODO for some reason, when breeding, the name of the child is not set to the breeds and colors...
//TODO do the jframes

//TODO CLEAN EVERYTHINGGGGGG!!!
//TODO COMMENT EVERYTHINGGGG!!!
//TODO FIX EVERYTHINGGGGGGGG!!!

public class MainGame extends JFrame {
	
	public static JPanel mainPanel = new JPanel();
	public static JTabbedPane jTPane = new JTabbedPane();
	private static AlienPanel aliens;
	private static WorkPanel work;
	private static QuestPanel quests;
	public static ShopPanel shop;
	public static CollectionPanel collection;
	
	public static JButton saveButton = new JButton("Save"); //o joy
	public static JButton loadButton = new JButton("Load");
	
	public Profile user;

	private JLabel profName;
	private JLabel exp;
	private JLabel coins;
	private JLabel level;
	private static final String SAVE = "saves\\";
	
	
	public static void main(String args[]) {
		new MainGame();
	}
	
	public MainGame() {
		
		setSize(400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		this.setContentPane(jTPane);
		
		mainPanel.setLayout(new GridLayout(6, 1));
		
		General.sqlSetup();
		General.sqlUpdate();
		
		
		user = new Profile(); //TODO save/load sequence
		
		
		for(int i = 0; i < 4; i++) {
			user.add(new Alien(user.getLevel()));
		}
				
		coins = new JLabel("$");
		level = new JLabel("LVL");
		exp = new JLabel("EXP");
		
		updateValuesUI();
		
		mainPanel.add(coins);
		mainPanel.add(level);
		mainPanel.add(exp);
		mainPanel.add(saveButton);
		mainPanel.add(loadButton);

		aliens = new AlienPanel(user);
		work = new WorkPanel(user);
		quests = new QuestPanel(user);
		shop = new ShopPanel(user);
		collection = new CollectionPanel(user);
		
		jTPane.addTab("Main", mainPanel);
		jTPane.addTab("Aliens", aliens);
		jTPane.addTab("Work", work);
		jTPane.addTab("Quests", quests);
		jTPane.addTab("Shop", shop);
		jTPane.addTab("Collection", collection);
		buttonWatcherMainScreen();
	}
	
	public void buttonWatcherMainScreen() {
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					File saveFile = new File(SAVE + JOptionPane.showInputDialog("Please type your character name: "));
					
					FileOutputStream fos = new FileOutputStream(saveFile);
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					
					if(saveFile.exists()) {
						saveFile.delete();
					}
					
					saveFile.createNewFile();
					
					oos.writeObject(user);
					oos.close();
				}catch(IOException i) {
					
					System.out.println("error in saving!");
					i.printStackTrace();
				}
			}
		});
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					FileInputStream fis = new FileInputStream((File)JOptionPane.showInputDialog(null, "Please select your save file: ", "Load", JOptionPane.QUESTION_MESSAGE, null, (new File(SAVE)).listFiles(), null));
					ObjectInputStream ois = new ObjectInputStream(fis);
					
					user = (Profile)ois.readObject();
					
					for(int i = 0; i < 3; i++) {
						System.out.println(user.aliens.get(i));
					
					}
					aliens.user = user;
					aliens.updateComboBox();
					
					ois.close();
					
				}catch(IOException o) {	
					System.out.println("Error: Could not load user profile.");
					o.printStackTrace();
				
				}catch(ClassNotFoundException c) {
				
					System.out.println("Error: The loaded file is invalid. Make sure you don't have any non-profile files in the save folder!");
					c.printStackTrace();
				}
			}
		});
		jTPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				
				shop.shopUpdate();
				shop.updateSellables();
				
				updateValuesUI();
			}
		});
	}

	public void WIP() {
		JOptionPane.showConfirmDialog(this, "WIP", "Coming Soon!", JOptionPane.OK_CANCEL_OPTION);
	}
	public void updateValuesUI() {
		user.checkLevelUp();
		coins.setText("$" + user.getCoins());
		level.setText("LVL " + user.getLevel());
		exp.setText("EXP " + user.getExp() + "/" + user.getMaxExp());
	}
}
