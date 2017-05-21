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
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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

	//public static Zone[] zonen = {
	//		new Zone(200,200,150,150, "Zone1"),
	//		new Zone(500,400,600,300, "Zone2"),
	//};
	public static ArrayList<Zone> zonen = new ArrayList<Zone>();
	public static ArrayList<Lampe> lampen = new ArrayList<Lampe>();
	
	public static int sattigungsWert = 100;
	public static int transSpeed = 0;
	public static Overlay ov;
	public static ZonenDialog zd;
	public static Gui gui;
	public static String bridgeKey, bridgeIp; 
	
	public static void main(String[] args) throws AWTException {
		
		//Klasse Gui ist die GUI
		gui = new Gui();
		
		//demodaten
		
		zonen.add(new Zone(300,300,300,300, "Zone1"));
		zonen.add(new Zone(100,100,150,150, "Zone2"));

		lampen.add(new Lampe("1","a"));
		lampen.add(new Lampe("2","b"));
		lampen.add(new Lampe("3","c"));
		
		//Overlay
		ov = new Overlay();
		
		//zonen bearbeiten dialog für das Overlay
		zd = new ZonenDialog();

		
		//Zu testzwecken BTN einfarben
		while (true) {
			//berechnet farbe und weist der zoone zu.
			sortColors(getScreen());
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
			zonenFarben = capture.getRGB(z.getX(),z.getY(),z.getWidth(),z.getHeight(), new int[z.getWidth() * z.getHeight()], 0, z.getWidth());
			//TODO: Farbendurchschnitt errechnen und für Zone speichern
			//System.out.println("Colors : "+Arrays.toString(zonenFarben))
			z.setColor(averageColor(zonenFarben));
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

	
	public static Color stattigung(Color c, int prozent) {
		//prozent 100 = keine änderung - 50 weniger farbe 150 mehr farbe
		//je heller der ton werden soll desto höher müssen die einzelnen farbwerte werden
		//deshalb wird der farbwert mit dem faktor multipliziert
		int red = c.getRed();
		int green = c.getGreen();
		int blue = c.getBlue();
		
		int newRed = red * prozent / 100;
		int newGreen = green * prozent / 100;		
		int newBlue = blue * prozent / 100;	
		
		//farbwert darf 255 nicht überschreiten
		
		if(newRed> 255){
			newRed = 255;
		}
		if(newGreen> 255){
			newGreen = 255;
		}
		if(newBlue> 255){
			newBlue = 255;
		}
		
		Color c2 = new Color(newRed, newGreen, newBlue);
		
		return c2;
	}
	
	public static void getAllBlubs(){
		try {
			URL url = new URL(
					"http://192.168.2.20/api/Q99KFY3tPBPEChwADgscZUermNNmRWjEjuKugTdM/lights/");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}

