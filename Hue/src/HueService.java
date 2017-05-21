import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import jdk.nashorn.internal.parser.JSONParser;

import org.json.*;

/**
 * 
 * Idee: Dieses Service soll alle lampen und gruppen aus dem HUE System laden.
 * + und die Befehle für die Lampen an das System senden
 * --> Atommare funktion, parameter ergänzt statt vars aus fremden klassen auslesen
 * @author AMP
 * Empfehlung: mit Jackson JSON in Objekte Parsen
 */
public class HueService {
	
	
	
	//gibt den AuthKey zurück und legt einen neuen benutzer an
	//url connection
	public static String registerKey(String ip){
		String key = null;
		main.gui.setHueMessage("Verbinden...", Color.black);
		try {
			String nachricht = "Unbekannter Fehler";
		    URL url = new URL("http://"+ip+"/api/");
		    URLConnection conn = url.openConnection();
		    conn.setDoOutput(true);
		    OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
		    String data = "{\"devicetype\":\"hue_hoe\"}";
		    writer.write(data);
		    writer.flush();
		    String line;
		    BufferedReader reader = new BufferedReader(new 
		                                     InputStreamReader(conn.getInputStream()));
		    while ((line = reader.readLine()) != null) {
		      System.out.println(line);
		      //wenn description enthalten --> fehlermeldung
		      if(line.contains("\"description\":\"")){
		      //nach "description":" kommt die Nachricht
		       String sub1[] = line.split("\"description\":\"");
		       String sub2[] = sub1[1].split("\"");
		       nachricht = sub2[0];
		       main.gui.setHueMessage(nachricht, Color.RED);
		      } 
		      //if username wird 
		      else if(line.contains("\"username\":\"")){
				      //nach "description":" kommt die Nachricht
				       String sub1[] = line.split("\"username\":\"");
				       String sub2[] = sub1[1].split("\"");
				       key = sub2[0];
				       main.gui.setHueMessage("Erfolgreich verbunden", Color.GREEN);
		      }
		    }   
		    writer.close();
		    reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			main.gui.setHueMessage("Keine Verbindung möglich", Color.RED);
		}
		return key;
	}

	//http get
	public static void getAllLampen(String ip, String authKey){
		//um doppelte lampen zu vermeiden werden alle gelöscht
		//aus der globalen liste
		main.lampen.clear();
		//aus allen zonen die lampen löschen
		for (int i = 0; i < main.zonen.size(); i++) {
    		Zone z = main.zonen.get(i);    
    		
    		z.lampen.clear();
		}
		
		try {
			
			String urlString = "http://"+ip+"/api/"+authKey+"/lights/";
			  
			URL url = new URL(urlString);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			String strTemp = "";
			while (null != (strTemp = br.readLine())) {
				System.out.println(strTemp);
				
				//prüfen ob fehler
				if(true){
					
				main.lampen.clear();	
				
				lampenToArray(strTemp);
				
				main.gui.updateGui();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	static void lampenToArray(String lJSON){
		
		// bisher lese ich nur die notwendigen werte aus
		
		JSONObject req = new JSONObject(lJSON);	
		
		//bekommt die ids (übergeordneten "objektnamen") raus
		JSONArray lampenIDs = req.names();
		
		//für jede ID den namen rausfinden, und in der Liste speichern
		for (int i = 0; i < lampenIDs.length(); ++i) {
		    
			String id = lampenIDs.get(i).toString();
			//bekommt anhand des Objektnamens/ID den namen raus
			String name = req.getJSONObject(lampenIDs.get(i).toString()).getString("name");
			
		    //System.out.println(lampenIDs.get(i));
		    //System.out.println(req.getJSONObject(lampenIDs.get(i).toString()).getString("name"));
		    main.lampen.add(new Lampe(id, name));
		}
		
	}
	
	
	
	
	
	
	
	//Entwicklungsfunktionen
	
	public static void testLampe1(){		
	try {
		URL url = new URL(
				"http://192.168.2.238/api/DMtrpMyJqPXyV7FiP0NTx14eknN-qWOgg4rwLsAc/lights/1/state");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("PUT");
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("Accept", "application/json");
		OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
		osw.write("{"
				+ "\"on\":true,"
				+ "\"bri\":254,"
				+ "\"xy\":[0.1,0.21]," 
				+"\"transitiontime\":1}");
		osw.flush();
		osw.close();
		System.out.println("response for blub:" + connection.getResponseMessage());

	} catch (IOException e) {
		e.printStackTrace();
	}
	
}

}
