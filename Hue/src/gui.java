import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class gui {

	public static Button button;
	
	public gui(){
	JFrame frame = new JFrame();
	
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
	
	button = new Button("New button");
	button.setBackground(new Color(102, 204, 102));
	
	frame.getContentPane().add(button, BorderLayout.CENTER);
	
	frame.setVisible(true);
	}
	
}
