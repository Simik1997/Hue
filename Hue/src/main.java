import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * hue gaming/streaming
 * 
 * Die Software soll die Hue lampen optimiert f�r G/S ansteueren. Die Software
 * soll leicht konfigurierbar, ressourcen schonend, sein.
 * 
 * Funktionalit�t Laden (Automatisch) Speichern Bridge/Lampen hinzuf�gen
 * Screenzonen f�r Lampen/Gruppen erstellen Hotkey umschaltung an/aus
 * 
 * Globale Einstellungen Updatespeed Transitionspeed Helligkeit S�ttigung
 * Speedup Modus
 * 
 * Soundmit einbeziehen? lautst�rken schwelle lautst�rken/helligkeits faktor
 * 
 * 
 * 
 * Funktionsstruktur
 * 
 * getColorFromRegion(x,y,x1,y1, genauigkeit, updatespeed) doColorGrading(color,
 * helligkeit, s�ttigung) sendToGroup(gruppe, color,transitionspeed)
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
			//new Zone(50,0,200,100),
	};
	
	public int kontrast;
	
	public static void main(String[] args) throws AWTException {
		// Klasse Gui ist die GUI
		gui gui = new gui();
 
		
		
		//Zu testzwecken BTN einf�rben
		while (true) {
			//berechnet farbe und wei�t der zoone zu.
			sortColors(getScreen());
			
			gui.button.setBackground(zonen[0].color);
			gui.red.setText(zonen[0].color.toString());
		}
	}

	// macht einen screenshot des Bildschirms
	// TODO: Screenshot nur von dem umschlie�enden Rechteck der zu
	// ber�cksichtigenden Zonen!
	// --> Besser Performance bei mehreren Monitoren. ICH BRAUCH SOWAS...
	public static BufferedImage getScreen() throws AWTException {
		Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage capture = new Robot().createScreenCapture(screenRect);
		return capture;
	}

	// bekommt das "BufferedImage" und die Zonen soll daraus die Farbwert lesen,
	// und anhand der Koordinaten in die Zonen aufteilen
	// EDIT: Es werden f�r Performancesteigerungen nicht alle Pixel abgesucht,
	// sondern nur die in Zonen enthaltenen.
	
	// TODO: Farben in Zonen aufteilen
	public static void sortColors(BufferedImage capture){

		//F�r jede Zone Farbwerte ermitteln
		for(Zone z: zonen){
			
			//gibt ein array der farbwerte f�r die Zone zur�ck
			int[] zonenFarben = null;
			//startX, startY, width, height, rgbArray, offset?, scansize?
			zonenFarben = capture.getRGB(z.x,z.y,z.width,z.height, new int[z.width * z.height], 0, z.width);
			//TODO: Farbendurchschnitt errechnen und f�r Zone speichern
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
         //Farbwerte aller einr�ge addieren
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
