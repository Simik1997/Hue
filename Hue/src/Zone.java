import java.awt.Color;
import java.util.ArrayList;
import java.util.UUID;


//getter funktionen sind notwendig damit wenn das objekt in JSON geparst wird diese werte angezeigt werden


public class Zone {
	UUID uuid;
	private int x;
	private int y;
	private int width;
	private int height;
	private Color color;
	private String name;
	public ArrayList<Lampe> lampen = new ArrayList<Lampe>();
	
	//innerhalb einer Zone sind die Lampen definiert
	//eine Zone hat auﬂerdem eine bestimmte Region (x,y,width,height)
	public Zone(int x,int y, int width, int height,String name){
		this.uuid = UUID.randomUUID();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = Color.white;
		this.name = name; 
		//lampen.add(new Lampe(111));
	}
	
	public int getX(){
		return x;
	}
	
	public void setX(int x2){
		x = x2;
	}
	
	public int getY(){
		return y;		
	}
	
	public void setY(int y2){
		y = y2;
	}
	
	public int getWidth(){
		return width;
	}
	
	public void setWidth(int w){
		width = w;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void setHeight(int h){
		height = h;
	}
	
	public Color getColor(){
		return color;
	}
	
	public void setColor(Color c){
		color = c;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String n){
		name = n;
	}
	
	public ArrayList<Lampe> getLampen(){
		return lampen;
	}
	
}
