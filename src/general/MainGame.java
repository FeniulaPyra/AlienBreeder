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


public class MainGame extends JFrame {
	
	//shows user info and save/load stuff
	public static JPanel mainPanel = new JPanel();
	//the pane that includes all other panes.
	public static JTabbedPane jTPane = new JTabbedPane();
	//shows info on user's aliens
	private static AlienPanel aliens;
	//for collecting random stuff to sell or keep
	private static WorkPanel work;
	//for completing single- and multiple-alien quests
	private static QuestPanel quests;
	//shopping for aliens, arts and for selling stuff
	public static ShopPanel shop;
	//for viewing all the aliens/artifacts the user has ever gotten.
	public static CollectionPanel collection;
	
	//save/load buttons
	public static JButton saveButton = new JButton("Save");
	public static JButton loadButton = new JButton("Load");
	
	//the directory for saving the user's profile/quests
	private static final String SAVE = "saves\\";
	
	//the user. this is so that the user can save.
	public Profile user;

	//the profile name
	private JLabel profName;
	//the experience the user has out of the exp needed for the next level
	private JLabel exp;
	//user's coins
	private JLabel coins;
	//user's level
	private JLabel level;
	
	//***FOR DEBUG***\\
	private static JButton addGold = new JButton("Gold++");
	private static JButton getAlienOf = new JButton("Get alien");
	
	
	public static void main(String args[]) {
		//starts the program
		new MainGame();
	}
	
	public MainGame() {
		//sets the size of the screen. relatively arbitrary
		setSize(400, 400);
		//makes the thing close
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//now it's not hiding!
		setVisible(true);
		//it gets screwed up if user messes with the size, so don't let user do that!
		setResizable(false);
		//makes it its own thing
		setLocationRelativeTo(null);
		//makes the main pane be the tabbed pane
		this.setContentPane(jTPane);
		
		//sets the main panel to align stuff on a grid.
		mainPanel.setLayout(new GridLayout(6, 1));
		
		//sets up the sql stuff!
		General.sqlSetup();
		General.sqlUpdate();
		General.resetAll();
		
		//makes a new user
		user = new Profile(); 
		
		//adds 4 random aliens for the user to get started with.
		for(int i = 0; i < 4; i++) {
			user.add(new Alien(user.getLevel()));
		}
		
		//sets the labels for these.
		coins = new JLabel("$");
		level = new JLabel("LVL");
		exp = new JLabel("EXP");
		
		updateValuesUI();
		
		//adds all the buttons and labels
		mainPanel.add(coins);
		mainPanel.add(level);
		mainPanel.add(exp);
		mainPanel.add(saveButton);
		mainPanel.add(loadButton);
		mainPanel.add(addGold);
		mainPanel.add(getAlienOf);
		
		//creates all of the different tabs
		aliens = new AlienPanel(user);
		work = new WorkPanel(user);
		quests = new QuestPanel(user);
		shop = new ShopPanel(user);
		collection = new CollectionPanel(user);
		
		//adds all of the tabs (including this one)
		jTPane.addTab("Main", mainPanel);
		jTPane.addTab("Aliens", aliens);
		jTPane.addTab("Work", work);
		jTPane.addTab("Quests", quests);
		jTPane.addTab("Shop", shop);
		jTPane.addTab("Collection", collection);
		
		//sets up the actionlisteners for each button
		buttonWatcherMainScreen();
	}
	
	//sets up the actionlisteners
	public void buttonWatcherMainScreen() {
		
		//saves stuff when button is pressed
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//creates a file based on the user's input name
					File saveFile = new File(SAVE + JOptionPane.showInputDialog("Please type your character name: "));
					
					FileOutputStream fos = new FileOutputStream(saveFile);
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					
					//if that file already exists, delete it so that we can overwrite it.
					if(saveFile.exists()) {
						saveFile.delete();
					}
					
					//creates the save!
					saveFile.createNewFile();
					
					//writes the user to the file!
					oos.writeObject(user);
					oos.close();
					
				}catch(IOException i) {
					System.out.println("error in saving!");
					i.printStackTrace();
				}
			}
		});
		//loads a user
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//shows a dialog listing the save files in the system and loads that as the input stream
					FileInputStream fis = new FileInputStream((File)JOptionPane.showInputDialog(null, "Please select your save file: ", "Load", JOptionPane.QUESTION_MESSAGE, null, (new File(SAVE)).listFiles(), null));
					ObjectInputStream ois = new ObjectInputStream(fis);
					
					//loads the user! Unfortunately loses all achievements in the database beyond what was in user's inventory when they saved.
					user = new Profile((Profile)ois.readObject());
					
					//makes sure the alien panel is up to date with the new user.
					aliens.user = user;
					aliens.updateComboBox();
					
					//closes the input stream
					ois.close();
					
				}
				catch(IOException o) {	
					System.out.println("Error: Could not load user profile.");
					o.printStackTrace();
				
				}
				catch(ClassNotFoundException c) {
					System.out.println("Error: The loaded file is invalid. Make sure you don't have any non-profile files in the save folder!");
					c.printStackTrace();
				}
			}
		});
		//checks when the user changes tabs
		jTPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				//updates all the labels and things
				shop.shopUpdate();
				shop.updateSellables();
				aliens.updateComboBox();
				updateValuesUI();
			}
		});
		//add gold button
		addGold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//adds lots of coins for cheating
				user.addCoins(1000000);
			}
		});
		//gives the user an alien of their choice based on the ID. May require access to the db or looking at the alien hashcode function.
		getAlienOf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//takes user input for id
				int id = Integer.parseInt(JOptionPane.showInputDialog("Enter the ID: "));
				//gets and gives the alien
				user.add(General.getAlien(id));
			}
		});
	}

	//will show a popup saying work in progress. to be used in buttons where things aren't done.
	public void WIP() {
		JOptionPane.showConfirmDialog(this, "WIP", "Coming Soon!", JOptionPane.OK_CANCEL_OPTION);
	}
	
	//updates labels
	public void updateValuesUI() {
		//checks lvl
		user.checkLevelUp();
		//updates coins
		coins.setText("$" + user.getCoins());
		//updates level
		level.setText("LVL " + user.getLevel());
		//updates exp
		exp.setText("EXP " + user.getExp() + "/" + user.getMaxExp());
	}
}
