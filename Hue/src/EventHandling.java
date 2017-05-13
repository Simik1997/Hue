import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

public class EventHandling implements ActionListener {
	
	

	@Override
	public void actionPerformed(ActionEvent al) {
		
		if (al.getSource() == gui.btn){
			System.exit(0);
		}
		
	}
	

}