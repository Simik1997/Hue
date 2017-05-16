import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.JButton;


public class ZonenDialog {
	
	private JTextField txtZonenName;
	private JButton btnZoneDelete;
	private JButton btnKopieren;
	private JButton btnbernehmen;
	private JButton btnAbbrechen;
	
	public static Button btnZoneNew, button;
	
	public ZonenDialog(){
	JFrame frmZoneneditor = new JFrame();
	frmZoneneditor.setResizable(false);
	frmZoneneditor.setTitle("Zoneneditor");
	frmZoneneditor.setBounds(0,0,271,169);
	
	frmZoneneditor.getContentPane().setLayout(null);
	
	JLabel lblName = new JLabel("Name");
	lblName.setBounds(10, 12, 100, 20);
	frmZoneneditor.getContentPane().add(lblName);
	
	txtZonenName = new JTextField();
	txtZonenName.setBounds(6, 43, 140, 20);
	frmZoneneditor.getContentPane().add(txtZonenName);
	txtZonenName.setColumns(10);
	
	Button btnZoneNew = new Button("Neu");
	btnZoneNew.setBounds(166, 11, 89, 23);
	btnZoneNew.addActionListener(main.ev);
	frmZoneneditor.getContentPane().add(btnZoneNew);
	
	btnZoneDelete = new JButton("L\u00F6schen");
	btnZoneDelete.setBounds(166, 42, 89, 23);
	frmZoneneditor.getContentPane().add(btnZoneDelete);
	
	btnKopieren = new JButton("Kopieren");
	btnKopieren.setBounds(166, 76, 89, 23);
	frmZoneneditor.getContentPane().add(btnKopieren);
	
	btnbernehmen = new JButton("\u00DCbernehmen");
	btnbernehmen.setBounds(139, 110, 116, 23);
	frmZoneneditor.getContentPane().add(btnbernehmen);
	
	btnAbbrechen = new JButton("Abbrechen");
	btnAbbrechen.setBounds(10, 110, 116, 23);
	frmZoneneditor.getContentPane().add(btnAbbrechen);
	frmZoneneditor.setAlwaysOnTop(true);
	frmZoneneditor.setVisible(true);

	}
}