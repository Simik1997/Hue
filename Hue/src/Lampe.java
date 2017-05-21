import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * Anstatt die Funktionalität zum senden in jeder Instanz der Klasse zu halten, hab ich diese in die Statischen HueService Methoden gelegt.
 * @author AMP
 *
 */
public class Lampe {
/**
 * Die ID geht von HUE aus, und sollte nicht geändert werden, da sonst die falsche lampe angesteuert wird.
 * @param id
 */
	public Lampe(String id, String name) {
		this.id = id;
		this.name = name;
	}

	private String id;
	private String name;
	
	
	// doc: https://developers.meethue.com/
	
	// doc init Connection, send Statements: https://developers.meethue.com/documentation/getting-started 
	// test URL: 192.168.2.20/debug/clip.html
	
	/**
	 * Hier wird der Name der Lampe also z.B. "Nachttischlample" oder "Deckenlampe zurückgegeben"
	 */
	public String getName(){
		return name;
	}
	
	public String getId(){
		return id;
	}
}
