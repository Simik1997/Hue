import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

public class EventHandling implements ActionListener {
	
	//btnZoneNew

	@Override
	public void actionPerformed(ActionEvent al) {
		
		if (al.getSource() == ZonenDialog.button){
			System.exit(0); 
		}
		else if(al.getSource() == Gui.btn2){
			System.exit(0); 
			
		}
		
	}
	

}