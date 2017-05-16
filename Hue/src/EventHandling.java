import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

public class EventHandling implements ActionListener {
	
	//btnZoneNew

	@Override
	public void actionPerformed(ActionEvent al) {
		
		if (al.getSource() == Gui.button){
			System.exit(0); 
		}
		else if(al.getSource() == ZonenDialog.btnZoneNew){
			System.exit(0); 
			
		}
		
	}
	

}