import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.SwingConstants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Gui {
	public JSlider slSattigung,slTransit;
	
	private JLabel lbSattigungWert, lbTransitSpeed;
	private JLabel lblStatusanzeige;
	private JButton btnEntfernen, btnHueAction;
	public JTextField textField, txtbridgeIp;
	private JComboBox<ComboItem> comboBox;
	
	private JList listLampen, listLampenInZone;
	
	
	DefaultListModel<ComboItem> listModelLampen = new DefaultListModel();
	DefaultListModel<ComboItem> listModelZonenLampen = new DefaultListModel();
	
	public Gui(){
	JFrame frmHueHoe = new JFrame();
	frmHueHoe.addWindowListener(new WindowAdapter() {
		@Override
		public void windowOpened(WindowEvent arg0) {
			
			//wenn das fenster erstellt wird
			//werte laden 
			laden("lastSettings.ini");
			
			updateGui();
			//gui updaten
		}
	});
	frmHueHoe.setTitle("Hue Hoe");
	frmHueHoe.setBounds(0,0,687,465);
	
	JMenuBar menuBar = new JMenuBar();
	frmHueHoe.setJMenuBar(menuBar);
	
	JMenu mnDatei = new JMenu("Datei");
	menuBar.add(mnDatei);
	
	JMenuItem mntmOpen = new JMenuItem("\u00D6ffnen");
	mnDatei.add(mntmOpen);
	
	JMenuItem mntmSpeichern = new JMenuItem("Speichern unter");
	mntmSpeichern.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        
        	
        	//Pfad auswählen, und speichern nach
        	
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("/home/me/Documents"));
            int retrival = chooser.showSaveDialog(null);
            if (retrival == JFileChooser.APPROVE_OPTION) {

            	speichern(chooser.getSelectedFile()+".ini");

                
            }
        }

    });
	mnDatei.add(mntmSpeichern);
	
	JMenu mnFunktionen = new JMenu("Funktionen");
	menuBar.add(mnFunktionen);
	
	JMenuItem mntmLampenErneutLaden = new JMenuItem("Lampen erneut laden");
	mntmLampenErneutLaden.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
			//lampen laden
			HueService.getAllLampen(main.bridgeIp, main.bridgeIp);
		}
	});
	mnFunktionen.add(mntmLampenErneutLaden);
	
	frmHueHoe.getContentPane().setLayout(null);
	
	JButton btnAdd = new JButton("Hinzuf\u00FCgen");
	btnAdd.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
		
			
			//lampe wird aus der Oberen liste Entfernt und in die Liste der ausgewählten zone getan
			Lampe l;
			String lampenID = "";
			String lampenName = "";
			//
	        for (int i = 0; i < main.lampen.size(); i++) {
	        	l = main.lampen.get(i);
	        	
	        	ComboItem selLampen = (ComboItem) listLampen.getSelectedValue();
	        	String selLampenID = selLampen.getValue();
	        	
	        	if(String.valueOf(l.getId()).equals(selLampenID)){
	        		lampenID = selLampen.getValue();
	        		lampenName = selLampen.getLabel();
	        		//Lampe aus der der Hauptliste Liste entfernen
	        		
	        		main.lampen.remove(i);

	        	}
	        }
			//dar in der GUI liste nur die ID und der Name zu finden sind muss das Objekt aus der gesamten liste gesucht werden.
	        for (int i = 0; i < main.zonen.size(); i++) {
	        	Zone z = main.zonen.get(i);
	        	
				ComboItem selZone = (ComboItem) comboBox.getSelectedItem();
				String selUUID = selZone.getValue();
				
	        	if( z.uuid.toString().equals(selUUID)){
	        		
	        		if(lampenID != ""){
	        		z.lampen.add(new Lampe(lampenID, lampenName));
	        		}
	        	}
	        }

    		//GUI Listen erneut füllen
    		fillListLampen();
    		fillListZonenLampen();
		}
	});
	btnAdd.setBounds(352, 167, 212, 23);
	frmHueHoe.getContentPane().add(btnAdd);
	
	btnEntfernen = new JButton("Entfernen");
	btnEntfernen.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
			
			//lampe wird aus der Oberen liste Entfernt und in die Liste der ausgewählten zone getan
			Lampe l;
			Zone z2 = null;
			
			String lampenID = "";
			String lampenName = "";
			
	        for (int i = 0; i < main.zonen.size(); i++) {
	        	Zone z = main.zonen.get(i);
	        	
				ComboItem selZone = (ComboItem) comboBox.getSelectedItem();
				String selUUID = selZone.getValue();
				
	        	if( z.uuid.toString().equals(selUUID)){
	        		
	        		z2 = z;
	        	}
	        }

	        if (z2 != null){
	        for (int i = 0; i < z2.lampen.size(); i++) {
	        	
	        	l = z2.lampen.get(i);
	        	
	        	ComboItem selZoneLampen = (ComboItem) listLampenInZone.getSelectedValue();
	        	String selZoneLampenID = selZoneLampen.getValue();
	        	
	        	if(String.valueOf(l.getId()).equals(selZoneLampenID)){
	        		lampenID = selZoneLampenID;
	        		lampenName = l.getName();
	        		//Lampe aus der Zone entfernen
	        		z2.lampen.remove(i);

	        	}
	        }
	        
	        //lampe zu main.lampenliste hinzufügen
	        main.lampen.add(new Lampe(lampenID, lampenName));
	        
    		//GUI Liste erneut füllen
    		fillListLampen();
    		fillListZonenLampen();
		}
	        
	        //keine selektion
		}
	});
	btnEntfernen.setBounds(352, 232, 212, 23);
	frmHueHoe.getContentPane().add(btnEntfernen);
	
	comboBox = new JComboBox();
	comboBox.setBounds(352, 201, 212, 20);
	
	comboBox.addActionListener (new ActionListener () {
	    public void actionPerformed(ActionEvent e) {
	    	//Beim ändern der selektierung die untere liste updaten
	    	fillListZonenLampen();
	    	
	    }
	});

	frmHueHoe.getContentPane().add(comboBox);
		
	txtbridgeIp = new JTextField();
	txtbridgeIp.addKeyListener(new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent arg0) {
			main.bridgeIp = txtbridgeIp.getText();
			updateGui();

		}
	});
	txtbridgeIp.setBounds(10, 31, 197, 20);
	frmHueHoe.getContentPane().add(txtbridgeIp);
	txtbridgeIp.setColumns(10);
	
	JLabel lblbridgeIp = new JLabel("Bridge IP");
	lblbridgeIp.setBounds(10, 11, 197, 14);
	frmHueHoe.getContentPane().add(lblbridgeIp);
	
	lblStatusanzeige = new JLabel("Statusanzeige");
	lblStatusanzeige.setBounds(10, 62, 332, 14);
	frmHueHoe.getContentPane().add(lblStatusanzeige);
	
	JButton btnStart = new JButton("Start");
	btnStart.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
			//zum testen
			fillComboZones();
			fillListLampen();
		}
	});
	btnStart.setBounds(10, 322, 197, 69);
	frmHueHoe.getContentPane().add(btnStart);
	
	JButton btnNewButton = new JButton("Editor");
	btnNewButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
			main.ov.show();
			main.zd.show();
		}
	});
	
	btnNewButton.setBounds(571, 200, 89, 23);
	frmHueHoe.getContentPane().add(btnNewButton);
	
	slSattigung = new JSlider();
	slSattigung.setMinimum(50);
	slSattigung.setValue(100);
	slSattigung.setMaximum(150);
	slSattigung.setBounds(10, 112, 200, 26);
	
	slSattigung.addChangeListener(new ChangeListener() {
	    public void stateChanged(ChangeEvent e) {
	        JSlider source = (JSlider)e.getSource();

	        int wert = (int)source.getValue();
	        main.sattigungsWert = wert;
	        //Lable sättigung setzten
	        main.gui.lbSattigungWert.setText(main.sattigungsWert+" %");

	    }
	});
	
	frmHueHoe.getContentPane().add(slSattigung);
	
	JLabel lblSttigung = new JLabel("S\u00E4ttigung");
	lblSttigung.setBounds(10, 87, 133, 14);
	frmHueHoe.getContentPane().add(lblSttigung);
	
	lbSattigungWert = new JLabel("100 %");
	lbSattigungWert.setHorizontalAlignment(SwingConstants.RIGHT);
	lbSattigungWert.setBounds(165, 87, 42, 14);
	frmHueHoe.getContentPane().add(lbSattigungWert);
	
	JLabel lblTransitS = new JLabel("\u00DCbergangsgesch.");
	lblTransitS.setBounds(10, 149, 118, 14);
	frmHueHoe.getContentPane().add(lblTransitS);
	
	lbTransitSpeed = new JLabel("0 ms");
	lbTransitSpeed.setHorizontalAlignment(SwingConstants.RIGHT);
	lbTransitSpeed.setBounds(138, 149, 69, 14);
	frmHueHoe.getContentPane().add(lbTransitSpeed);
	
	slTransit = new JSlider();
	slTransit.setValue(0);
	slTransit.setMaximum(5000);
	slTransit.setBounds(10, 177, 200, 26);
	
	slTransit.addChangeListener(new ChangeListener() {
	    public void stateChanged(ChangeEvent e) {
	        JSlider source = (JSlider)e.getSource();

	        int wert = (int)source.getValue();
	        
	        main.transSpeed = wert;
	        
	        //Lable sättigung setzten
	        main.gui.lbTransitSpeed.setText(main.transSpeed+" ms");

	    }
	});
	
	frmHueHoe.getContentPane().add(slTransit);
	
	btnHueAction = new JButton("Reset");
	btnHueAction.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
			if(btnHueAction.getText() == "Reset"){
				
				main.bridgeKey = null;
				main.bridgeIp = null;
				
				//um doppelte lampen zu vermeiden werden alle gelöscht
				//aus der globalen liste
				main.lampen.clear();
				//aus allen zonen die lampen löschen
				for (int i = 0; i < main.zonen.size(); i++) {
	        		Zone z = main.zonen.get(i);    
	        		
	        		z.lampen.clear();
				}
				updateGui();
				
			} else if (btnHueAction.getText() == "Connect"){
				
				//registrieren
				main.bridgeKey = HueService.registerKey(main.bridgeIp);
				
				//lampen laden
				HueService.getAllLampen(main.bridgeIp, main.bridgeIp);
				
			}
			
			
		}
	});
	btnHueAction.setBounds(217, 30, 125, 23);
	frmHueHoe.getContentPane().add(btnHueAction);
	
	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setBounds(350, 31, 214, 125);
	frmHueHoe.getContentPane().add(scrollPane);
	
	listLampen = new JList(listModelLampen);
	scrollPane.setViewportView(listLampen);
	
	JScrollPane scrollPaneLampenZonen = new JScrollPane();
	scrollPaneLampenZonen.setBounds(352, 266, 212, 125);
	frmHueHoe.getContentPane().add(scrollPaneLampenZonen);
	
	listLampenInZone = new JList(listModelZonenLampen);
	scrollPaneLampenZonen.setViewportView(listLampenInZone);
	
	
	frmHueHoe.setVisible(true);
	}
	
	public void fillComboZones(){
		
		comboBox.removeAllItems();
		
        for (int i = 0; i < main.zonen.size(); i++) {
        	Zone z = main.zonen.get(i);
		
        	comboBox.addItem(new ComboItem(z.uuid.toString(), z.getName()));
        }
	}
	
	public void fillListLampen(){
		
		listModelLampen.clear();
		
        for (int i = 0; i < main.lampen.size(); i++) {
        		Lampe l = main.lampen.get(i);        	
        		listModelLampen.addElement(new ComboItem(l.getId(), l.getName()));
        }
	}
	
	public void fillListZonenLampen(){

		
		listModelZonenLampen.clear();
		
		//richtige zone suchen
        for (int i = 0; i < main.zonen.size(); i++) {
        		Zone z = main.zonen.get(i);        	
        		
        if(comboBox.getSelectedItem() != null){
				ComboItem selZone = (ComboItem) comboBox.getSelectedItem();
				String selUUID = selZone.getValue();
				
				//richtige zone suchen
	        	if( z.uuid.toString().equals(selUUID)){
	        		
	        		//lampen der zone
	                for (int i2 = 0; i2 < z.lampen.size(); i2++) {
	            		Lampe l = z.lampen.get(i2); 
	            		listModelZonenLampen.addElement(new ComboItem(l.getId(), l.getName()));
	          }
	        }
	       }
        }
	}
	
	public void updateGui(){
		
		//nicht bei jeder änderung muss die ganze gui geupdatet werden
		//vorgesehen für: start, laden, usw
		fillComboZones();
		fillListLampen();
		fillListZonenLampen();
		
		//Wenn kein Key hinterlegt ist eine registrierung notwendig
		if(main.bridgeKey == null || main.bridgeKey == ""){
			if(main.bridgeIp == null || main.bridgeIp == ""){
				setHueMessage("IP Eingeben",Color.red);
				setHueBtn("IP Eingeben");
			} else {
				setHueMessage("Registrieren",Color.red);
				setHueBtn("Connect");
			}
		}else {
				setHueMessage("OK",Color.green);
				setHueBtn("Reset");
		}
	}
	
	public void setHueMessage(String s,Color c){
		lblStatusanzeige.setText(s);
		lblStatusanzeige.setForeground(c);
	}
		
	public void setHueBtn(String s){
		btnHueAction.setText(s);
	}	

	public void speichernAlt(){
		

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("/home/me/Documents"));
        int retrival = chooser.showSaveDialog(null);
        if (retrival == JFileChooser.APPROVE_OPTION) {
            try {               	
            	File configFile = new File(chooser.getSelectedFile()+".hoe");
            	 
            	try {
            	    Properties props = new Properties();
            	    
            	    String bridgeIp = txtbridgeIp.getText();
            	    String sattigung = String.valueOf(slSattigung.getValue());
            	    String tspeed = String.valueOf(slTransit.getValue());
            	    String zonen = "";
            	  //zonen beinhalten eigenschaften und lampen
            	    //alle Zonen durch ";Z;" trennen
            	    //eigenschaften durch ";E;" trennen
            	    //lampen durch ";L;" trennen
            	    //lampeneigenschaften durch ";LE;"
            	    //extrem unsauber aber für die testphase ausreichend
            	    //int x,int y, int width, int height,String name, Lampen

                    for (int i = 0; i < main.zonen.size(); i++) {
                    	Zone z = main.zonen.get(i);	
                    	String zone = "";
                    	String lampe = "";
                    	//zoneneigenschaften auflisten
                    	zone = zone + String.valueOf(z.getX()) + ";E;";
                    	zone = zone + String.valueOf(z.getY()) + ";E;";
                    	zone = zone + String.valueOf(z.getWidth()) + ";E;";
                    	zone = zone + String.valueOf(z.getHeight()) + ";E;";
                    	zone = zone + z.getName() + ";E;";
                    	
                    	for (int i2 = 0; i2 < z.lampen.size(); i2++) {
                    		Lampe l = z.lampen.get(i2);
                    		lampe = lampe + String.valueOf(l.getId()) +";LE;"+l.getName()+";L;"; 
                    	}
                    	zone = zone + lampe + ";E;";
                    	zonen = zonen + zone + ";Z;";
                    }
            	    
                    String lampen ="";
                    //in "lampen" sind alle nicht zugewiesenen Lampen
                    //lampen beinhalten eigenschaften
            	    //eigenschaften durch ";E;" trennen
            	    //lampen durch ";L;" trennen
                    for (int i = 0; i < main.lampen.size(); i++) {
                    	Lampe l = main.lampen.get(i);	
                    	
                    	lampen = lampen + String.valueOf(l.getId()) + ";E;" +l.getName()+";L;"; 
                    }
                    
            	    props.setProperty("bridge-ip", bridgeIp);
            	    props.setProperty("bridge-key", "benötigt?");
            	    props.setProperty("sattigung", sattigung);
            	    props.setProperty("tspeed", tspeed);
            	    props.setProperty("zonen", zonen);
            	    props.setProperty("lampen", lampen);
            	    
            	    FileWriter writer = new FileWriter(configFile);
            	    props.store(writer, "HUE HOE");
            	    writer.close();
            	} catch (FileNotFoundException ex) {
            	    // file does not exist
            	} catch (IOException ex) {
            	    // I/O error
            	}
            	
            	
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
	}
	
	
	public void speichern(String filepfad){
		//Sämtliche Einstellungen werden gespeichert
		//dabei ist der unterschied ob die Config als "Backup"
		//Über "speichern unter" gespeichert wurde, oder automatisch in die
		//"lastsettings" gespeichert wird, welche automatisch geladen wird
		
		JSONArray jsonLampen = new JSONArray(main.lampen);
		String lampen = jsonLampen.toString();
		
		JSONArray jsonZonen = new JSONArray(main.zonen);
		String zonen = jsonZonen.toString();

		String bridgeIp = main.bridgeIp;
		String sattigung = String.valueOf(main.sattigungsWert);
		String bridgeKey = main.bridgeKey;
		String tspeed = String.valueOf(main.transSpeed);
		
		System.out.println(lampen);		
		System.out.println(zonen);

		//file erstellen und schreiben
		File saveFile = new File(filepfad);
		Properties props = new Properties();
	    props.setProperty("bridgeIp", bridgeIp);
	    props.setProperty("bridgeKey", bridgeKey);
	    props.setProperty("sattigung", sattigung);
	    props.setProperty("tspeed", tspeed);
	    props.setProperty("zonen", zonen);
	    props.setProperty("lampen", lampen);
	    
	    FileWriter writer;
		try {
			writer = new FileWriter(saveFile);
		    props.store(writer, "HUE HOE");
		    writer.close();
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void laden(String filepfad){
		//Sämtliche Einstellungen werden gespeichert
		//dabei ist der unterschied ob die Config als "Backup"
		//Über "speichern unter" gespeichert wurde, oder automatisch in die
		//"lastsettings" gespeichert wird, welche automatisch geladen wird
		
		JSONArray jsonLampen = new JSONArray(main.lampen);
		String lampen = jsonLampen.toString();
		
		JSONArray jsonZonen = new JSONArray(main.zonen);
		String zonen = jsonZonen.toString();

		String bridgeIp = main.bridgeIp;
		String sattigung = String.valueOf(main.sattigungsWert);
		String bridgeKey = main.bridgeKey;
		String tspeed = String.valueOf(main.transSpeed);
		
		System.out.println(lampen);		
		System.out.println(zonen);

		//file erstellen und schreiben
		File saveFile = new File(filepfad);
		Properties props = new Properties();
	    props.setProperty("bridgeIp", bridgeIp);
	    props.setProperty("bridgeKey", bridgeKey);
	    props.setProperty("sattigung", sattigung);
	    props.setProperty("tspeed", tspeed);
	    props.setProperty("zonen", zonen);
	    props.setProperty("lampen", lampen);
	    
	    FileWriter writer;
		try {
			writer = new FileWriter(saveFile);
		    props.store(writer, "HUE HOE");
		    writer.close();
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
