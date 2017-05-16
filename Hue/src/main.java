import java.awt.AWTException;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;
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
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.BorderLayout;

import javax.swing.SwingConstants;

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


//TODO: color grading
//TODO: hue integration

public class main {

	//DEMODATEN
	
	public static Zone[] zonen = {
			new Zone(200,200,150,150),
			new Zone(500,400,600,300),
	};
	
	public int kontrast;
	
	public static EventHandling ev;
	
	
	public static void main(String[] args) throws AWTException {
		
		ev = new EventHandling();
		
		//Klasse Gui ist die GUI
		Gui gui = new Gui();
		
		
		//Overlay
		Overlay ov = new Overlay();
		ov.overlayShow();
		//zonen bearbeiten dialog für das Overlay
		ZonenDialog zd = new ZonenDialog();
		
		
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
			//System.out.println("Colors : "+Arrays.toString(zonenFarben))
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

	
}
