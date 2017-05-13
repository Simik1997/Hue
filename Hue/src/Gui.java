import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;


public class Gui {

	
	public static Button button;
	public static Button btn;
	JLabel farbfld1;
	JTextField red, green, blue;
	
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
	
	btn = new Button("Exit");
	btn.setBounds(10,230,300,80);
	btn.addActionListener(new EventHandling());
	frame.getContentPane().add(btn);
	
	red = new JTextField();
	red.setBounds(10,140,360,20);
	red.setEditable(false);
	frame.getContentPane().add(red);
	
	
	frame.getContentPane().add(button, BorderLayout.CENTER);
	
	frame.getContentPane().setLayout(null);
	frame.setVisible(true);
	}
}

