import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.swing.JFrame;

/**
 * hue gaming/streaming
 * 
 * Die Software soll die Hue lampen optimiert für G/S ansteueren. Die Software
 * soll leicht konfigurierbar, ressourcen schonend, sein.
 * 
 * Funktionalität Laden (Automatisch) Speichern Bridge/Lampen hinzufügen
 * Screenzonen für Lampen/Gruppen erstellen Hotkey umschaltung an/aus
 * 
 * Globale Einstellungen Updatespeed Transitionspeed Helligkeit Sättigung
 * Speedup Modus
 * 
 * Soundmit einbeziehen? lautstärken schwelle lautstärken/helligkeits faktor
 * 
 * 
 * 
 * Funktionsstruktur
 * 
 * getColorFromRegion(x,y,x1,y1, genauigkeit, updatespeed) doColorGrading(color,
 * helligkeit, sättigung) sendToGroup(gruppe, color,transitionspeed)
 * sendToLamp(lampe, color,transitionspeed)
 * 
 * @author Alex
 * 
 * TODO:
 * Gui
 * Zonengui
 * Einstellungen
 * Laden
 * Speichern
 * Colorblading
 * 
 */

public class main {

	//DEMODATEN
	public static Zone[] zonen = {
			new Zone(200,200,150,150),
			new Zone(500,400,600,300),
	};
	
	public int kontrast;
	
	public static void main(String[] args) throws AWTException {
		//Klasse Gui ist die GUI
		Gui gui = new Gui();
		//Zu testzwecken BTN einfärben

		//Overlay
		createOverlay();
		
		//drawO.showZonen();
		
		
		//Zu testzwecken BTN einfarben
		while (true) {
			//berechnet farbe und weist der zoone zu.
			sortColors(getScreen());
			
			
			Color c = zonen[0].color;
			
						
			gui.button.setBackground(c);
			
			//Buttons neben den Labels für die einzelnen farbwerte
			gui.btnred.setBackground(new Color(c.getRed(),0,0));
			gui.btngreen.setBackground(new Color(0,c.getGreen(),0));
			gui.btnblue.setBackground(new Color(0,0,c.getBlue()));

			//lable geben rgb wert an
			String returnred = String.valueOf(c.getRed());
			String returngreen = String.valueOf(c.getGreen());
			String returnblue = String.valueOf(c.getBlue());

			
			//ruckgabe an Textfeld 
			gui.red.setText(returnred);
			gui.blue.setText(returnblue);
			gui.green.setText(returngreen);
			// Versuch den einzelnen Farbwert auszulesen und als Farbe neben dem Textfeld dazustellen
			
			//gui.btnred.setBackground(Color.decode("255"));
			
		}
		
		
		
		
		
	}

	// macht einen screenshot des Bildschirms
	// TODO: Screenshot nur von dem umschließenden Rechteck der zu
	// berücksichtigenden Zonen!
	// --> Besser Performance bei mehreren Monitoren. ICH BRAUCH SOWAS...
	public static BufferedImage getScreen() throws AWTException {
		Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage capture = new Robot().createScreenCapture(screenRect);
		return capture;
	}

	// bekommt das "BufferedImage" und die Zonen soll daraus die Farbwert lesen,
	// und anhand der Koordinaten in die Zonen aufteilen
	// EDIT: Es werden für Performancesteigerungen nicht alle Pixel abgesucht,
	// sondern nur die in Zonen enthaltenen.
	
	// TODO: Farben in Zonen aufteilen
	public static void sortColors(BufferedImage capture){

		//Für jede Zone Farbwerte ermitteln
		for(Zone z: zonen){
			
			//gibt ein array der farbwerte für die Zone zurück
			int[] zonenFarben = null;
			//startX, startY, width, height, rgbArray, offset?, scansize?
			zonenFarben = capture.getRGB(z.x,z.y,z.width,z.height, new int[z.width * z.height], 0, z.width);
			//TODO: Farbendurchschnitt errechnen und für Zone speichern
			//System.out.println("Colors : "+Arrays.toString(zonenFarben));
			
			z.color = averageColor(zonenFarben);
			
		}
  }
	
	public static Color averageColor(int[] colors){
	 
	 int redBucket = 0;
	 int greenBucket = 0;
	 int blueBucket = 0;
	 int colorCount = 0;

	 for(int cNum: colors){
		
		 //INT in Color umwandeln
		 Color c = new Color(cNum);
		 
         colorCount++;
         //Farbwerte aller einräge addieren
         redBucket += c.getRed();
         greenBucket += c.getGreen();
         blueBucket += c.getBlue();
         
	 }
	 
	 //Durschnittsfarbe 
	 //Farben gesamt / anzahl farben
	 Color averageColor = new Color(redBucket / colorCount,greenBucket / colorCount,blueBucket / colorCount);

	return averageColor;
 	}

	public static void createOverlay(){
        JFrame f = new JFrame("Draw Box Mouse 2");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setContentPane(new Overlay());

		f.setBounds(f.getGraphicsConfiguration().getBounds());
		//f.setBackground(new Color(0, true));
		f.getContentPane().setLayout(new java.awt.BorderLayout());
		f.getContentPane().setBackground(new Color(0, 0, 0, 01));
		f.setUndecorated(true);
		f.setBackground(new Color(0, 0, 0, 01));
		f.setAlwaysOnTop(true);
        f.setVisible(true);
		
	}
	
}
