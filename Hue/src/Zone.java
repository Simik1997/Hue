import java.awt.Color;


public class Zone {

	public int x;
	public int y;
	public int width;
	public int height;
	public Color color;
	
	//innerhalb einer Zone sind die Lampen definiert
	//eine Zone hat auﬂerdem eine bestimmte Region (x,y,width,height)
	public Zone(int x,int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = Color.white;
	}
	
}
