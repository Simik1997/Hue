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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Window.Type;



public class ZonenDialog {
	
	
	public JTextField txtZonenName;
	private JButton btnZoneDelete;
	private JButton btnbernehmen;
	private JButton btnAbbrechen;
	private JFrame frmZoneneditor;
	public static Button btnZoneNew, button;
	
	public ZonenDialog(){
	frmZoneneditor = new JFrame();
	frmZoneneditor.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	frmZoneneditor.setResizable(false);
	frmZoneneditor.setTitle("Zoneneditor");
	frmZoneneditor.setBounds(0,0,271,137);
	
	frmZoneneditor.getContentPane().setLayout(null);
	
	JLabel lblName = new JLabel("Name");
	lblName.setBounds(6, 12, 100, 20);
	frmZoneneditor.getContentPane().add(lblName);
	
	txtZonenName = new JTextField();
	txtZonenName.setText("Name");
	txtZonenName.setBounds(6, 43, 140, 20);
	
	txtZonenName.getDocument().addDocumentListener(new DocumentListener() {
		@Override
		public void changedUpdate(DocumentEvent e) {
			updateName();
		}

		@Override
		public void insertUpdate(DocumentEvent arg0) {
			updateName();
		}

		@Override
		public void removeUpdate(DocumentEvent arg0) {
			updateName();
		}
		
		void updateName(){
			
            for (int i = 0; i < main.zonen.size(); i++) {
            	Zone z = main.zonen.get(i);
            	if(z.uuid == main.ov.aktiveZoneID){
            		main.zonen.get(i).setName(txtZonenName.getText());
            	}
            }
		}
	});
	
	frmZoneneditor.getContentPane().add(txtZonenName);
	txtZonenName.setColumns(10);
	
	JButton btnZoneNew = new JButton("Neu");
	btnZoneNew.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			main.zonen.add(new Zone(200, 200, 200, 200, "Neue Zone"));
			main.ov.reNewPaint();
			
		}
	});
	btnZoneNew.setBounds(166, 11, 89, 23);
	;
	frmZoneneditor.getContentPane().add(btnZoneNew);
	
	btnZoneDelete = new JButton("L\u00F6schen");
	btnZoneDelete.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
            for (int i = 0; i < main.zonen.size(); i++) {
            	Zone z = main.zonen.get(i);
            	if(z.uuid == main.ov.aktiveZoneID){
            		main.zonen.remove(i);
            		main.ov.selectFirstZone();
            		main.ov.reNewPaint();
            	}
            }
		}
	});
	btnZoneDelete.setBounds(166, 42, 89, 23);
	frmZoneneditor.getContentPane().add(btnZoneDelete);
	
	btnbernehmen = new JButton("\u00DCbernehmen");
	btnbernehmen.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
		
			//Overlay und dialog schließen
			main.ov.hide();
			main.zd.hide();
			main.gui.fillComboZones();
		}
	});
	btnbernehmen.setBounds(16, 74, 239, 23);
	frmZoneneditor.getContentPane().add(btnbernehmen);

	frmZoneneditor.setAlwaysOnTop(true);


	}

	public void show(){
		frmZoneneditor.setVisible(true);
	}
	public void hide(){
		frmZoneneditor.setVisible(false);
	}
}