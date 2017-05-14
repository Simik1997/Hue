import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.JButton;


public class Gui {

	
	public static Button button, btnred, btngreen, btnblue;
	public static Button btn;
	JLabel farbfld1, lred, lgreen, lblue;
	JTextField red,red1, green, blue;
	
	
	public Gui(){
	JFrame frame = new JFrame();
	frame.setBounds(0,0,428,396);
	
	JMenuBar menuBar = new JMenuBar();
	frame.setJMenuBar(menuBar);
	
	JMenu mnDatei = new JMenu("Datei");
	menuBar.add(mnDatei);
	
	JMenuItem mntmNeu = new JMenuItem("Neu");
	mnDatei.add(mntmNeu);
	
	JMenuItem mntmOpen = new JMenuItem("\u00D6ffnen");
	mnDatei.add(mntmOpen);
	
	JMenuItem mntmSpeichern = new JMenuItem("Speichern");
	mnDatei.add(mntmSpeichern);
	
	JMenuItem mntmSpeichernUnter = new JMenuItem("Speichern unter...");
	mnDatei.add(mntmSpeichernUnter);
	
	JMenu mnEinstellungen = new JMenu("Einstellungen");
	menuBar.add(mnEinstellungen);
	
	JMenuItem mntmZurcksetzen = new JMenuItem("Zur\u00FCcksetzen");
	mnEinstellungen.add(mntmZurcksetzen);
	
	farbfld1 = new JLabel("Farbfeld:");
	farbfld1.setBounds(10, 10, 100, 20);
	frame.getContentPane().add(farbfld1);
	
	button = new Button("");
	button.setEnabled(false);
	button.setBounds(10,40,200,80);
	button.setBackground(new Color(102, 204, 102));
	
	btnred = new Button("");
	btnred.setEnabled(false);
	btnred.setBounds(110,140,40,20);
	btnred.setBackground(new Color(102, 204, 102));
	frame.getContentPane().add(btnred);
	
	btngreen = new Button("");
	btngreen.setEnabled(false);
	btngreen.setBounds(110,160,40,20);
	btngreen.setBackground(new Color(102, 204, 102));
	frame.getContentPane().add(btngreen);
	
	btnblue = new Button("");
	btnblue.setEnabled(false);
	btnblue.setBounds(110,180,40,20);
	btnblue.setBackground(new Color(102, 204, 102));
	frame.getContentPane().add(btnblue);
	
	btn = new Button("Exit");
	btn.setBounds(10,230,300,80);
	btn.addActionListener(new EventHandling());
	frame.getContentPane().add(btn);
	
	lred = new JLabel("Red:");
	lred.setBounds(10, 140, 100, 20);
	frame.getContentPane().add(lred);
	
	red = new JTextField();
	red.setBounds(50,140,60,20);
	red.setEditable(false);
	frame.getContentPane().add(red);
	
	lgreen = new JLabel("Green:");
	lgreen.setBounds(10, 160, 100, 20);
	frame.getContentPane().add(lgreen);
	
	green = new JTextField();
	green.setBounds(50,160,60,20);
	green.setEditable(false);
	frame.getContentPane().add(green);
	
	lblue = new JLabel("Blue:");
	lblue.setBounds(10, 180, 100, 20);
	frame.getContentPane().add(lblue);
	
	blue = new JTextField();
	blue.setBounds(50,180,60,20);
	blue.setEditable(false);
	frame.getContentPane().add(blue);
	/*
	red1 = new JTextField();
	red1.setBounds(10,200,360,20);
	red1.setEditable(false);
	frame.getContentPane().add(red1);
	*/	
	frame.getContentPane().add(button, BorderLayout.CENTER);
	
	frame.getContentPane().setLayout(null);
	frame.setVisible(true);

	}
}