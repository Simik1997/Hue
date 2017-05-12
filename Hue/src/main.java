import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

/**
 * 
 * V 0.01
 * 
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
 */

public class main {

	//DEMODATEN
	public static Zone[] zonen = {
			new Zone(0,0,100,100),
			new Zone(50,0,200,100),
	};
	

	public static void main(String[] args) throws AWTException {
		// Klasse Gui ist die GUI
		gui gui = new gui();

		sortColors(getScreen());
		
		// setzt den "testButton" auf die Mausfarbe
		//while (true) {
		//	gui.button.setBackground(getMouseColor());
		//}
	}

	// macht einen screenshot des Bildschirms
	// TODO: Screenshot nur von dem umschließenden Rechteck der zu
	// berücksichtigenden Zonen!
	// --> Besser Performance bei mehreren Monitoren. ICH BRAUCH SOWAS...
	public static BufferedImage getScreen() throws AWTException {
		Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit()
				.getScreenSize());
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
			zonenFarben = capture.getRGB(z.x,z.y,z.width,z.height, new int[z.width * z.height], 0, 0);
			
			//zum test eine Farbe aus dem Array ausgeben und in eine "Color" konvertieren
			Color c = new Color(zonenFarben[10]);
			System.out.println("ZonenFarben [10]: "+c);
			
			//TODO: Farbendurchschnitt errechnen und für Zone speichern

		}
  }

	
	
	
	//VERALTET! a
	
	// Funktion ermittelt über einen "Roboter" die Farbei bei einem Pixel
	// ist angeblich zu langsam, man müsste jeden Pixel einmal vom Bildschirm
	// ablesen
	// schnellere methode mit "BufferedImages" in Arbeit
	public static Color getMouseColor() throws AWTException {
		PointerInfo pointer;
		pointer = MouseInfo.getPointerInfo();
		Point coord = pointer.getLocation();
		Robot robot = new Robot();
		coord = MouseInfo.getPointerInfo().getLocation();
		Color color = robot.getPixelColor((int) coord.getX(),
				(int) coord.getY());
		robot.delay(10);
		return color;
	}

}
