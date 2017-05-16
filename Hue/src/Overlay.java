import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

    public class Overlay extends JPanel {

    	//wird die zone gespeichert wenn gedraggt wird
    	Zone draggingZone;
    	//wird die zuletzt selektierte zone gespeichert (f�r die einstellungen und l�schen usw.)
    	//wird farblich gekennzeichnet
    	Zone aktiveZone;
    	int feld; //1 links - oben ,2 rechts - oben, 3 links - unten, 4 rechts - unten, 5 mitte
    	//dragAreaSize
        int daSize = 10;
    	//mittelkreisradius
        int mkR = 25;
        //zonen minimum
        int zoneMin = 50;
        
        
        //erstellt das fenster und zeigt es an
        public void overlayShow(){
        	
        	JFrame overlayFrame = new JFrame("Zonen");
			overlayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Overlay overlay = new Overlay();
            overlayFrame.setContentPane(overlay);
            
            overlayFrame.setBounds(overlayFrame.getGraphicsConfiguration().getBounds());
            overlayFrame.getContentPane().setLayout(new java.awt.BorderLayout());
            overlayFrame.getContentPane().setBackground(new Color(0, 0, 0, 01));
            overlayFrame.setUndecorated(true);
            overlayFrame.setBackground(new Color(0, 0, 0, 0));
            overlayFrame.setAlwaysOnTop(true);
            overlayFrame.getContentPane().setLayout(null);
            
            overlayFrame.setVisible(true);
        }        
        
        Overlay() {
            MyMouseListener listener = new MyMouseListener();
            addMouseListener(listener);
            addMouseMotionListener(listener);
            
        	if(main.zonen[0] != null){
        		aktiveZone = main.zonen[0];
        	}
            
        }


        //listener fangen die position ab
        class MyMouseListener extends MouseAdapter implements MouseMotionListener{

        	//wenn ein "Feld" geklickt wird wird sich dieses gemerkt
            public void mousePressed(MouseEvent e) {
            	for(Zone z: main.zonen){
                	
            		//Linkes eck x & y zwischen    e.x >= z.x- daSize/2 && e.x <= z.x + daSize/2 
            		//links oben
            		if(
            		e.getX() >= z.x-daSize/2 && e.getX() <= z.x+daSize+2 &&
            		e.getY() >= z.y-daSize/2 && e.getY() <= z.y+daSize+2		
            		){
            			System.out.print("links oben!");
            			draggingZone = z;
            			aktiveZone = z;
            			feld = 1;
            			//repaint();
            		}
            		//rechts oben
            		else if( 
            			e.getX() >= z.x+z.width-daSize/2 && e.getX() <= z.x+z.width+daSize+2 &&
            	    	e.getY() >= z.y-daSize/2 && e.getY() <= z.y+daSize+2	
            		){
            			System.out.print("rechts oben!");
            			draggingZone = z;
            			aktiveZone = z;
            			feld = 2;
            		}
            		//links unten
            		else if( 
            			e.getX() >= z.x-daSize/2 && e.getX() <= z.x+daSize+2 &&
            	    	e.getY() >= z.y+z.height-daSize/2 && e.getY() <= z.y+z.height+daSize+2	
            		){
            			System.out.print("links unten!");
            			draggingZone = z;
            			aktiveZone = z;
            			feld = 3;
            		}
            		//rechts unten
            		else if( 
            			e.getX() >= z.x+z.width-daSize/2 && e.getX() <= z.x+z.width+daSize+2 &&
            			e.getY() >= z.y+z.height-daSize/2 && e.getY() <= z.y+z.height+daSize+2	
            		){
            			System.out.print("rechts unten!");
            			draggingZone = z;
            			aktiveZone = z;
            			feld = 4;
            		}    		
            		//mitte
            		else if( 
            			e.getX() >= z.x+z.width/2-mkR/2 && e.getX() <= z.x+z.width/2+mkR+2 &&
            			e.getY() >= z.y+z.height/2-mkR/2 && e.getY() <= z.y+z.height/2+mkR+2	
            		){
            			System.out.print("mitte!");
            			draggingZone = z;
            			aktiveZone = z;
            			feld = 5;
            		}   
            		
            	}
            }
            
            //beim loslassen wird das gemerkte feld und die zone wieder verworfen
            public void mouseReleased(MouseEvent e) {
                //setEndPoint(e.getX(), e.getY());
            	
            	draggingZone = null;
            	feld = 0;
                repaint();
            }
            
          //wird die maus bewegt werden die werte angepasst und erneut gemalt
            public void mouseDragged(MouseEvent e) {
                System.out.println("Mouse dragged");
                
                //falls eine zone ausgew�hlt ist
                if(draggingZone != null && feld != 0){

                	//links oben
                	if(feld == 1){
                		//neue weite berechen
                		 int newWidth = draggingZone.width + (draggingZone.x - e.getX());
                		 int newHeight = draggingZone.height + (draggingZone.y - e.getY());
                		//pr�fen ob mindestgr��en unterschritten wird
                        if(newWidth >= zoneMin)
                        {
                			draggingZone.width = newWidth;
                			draggingZone.x = e.getX();
                        }
                        if (newHeight >= zoneMin){
                			draggingZone.height = newHeight;
                			draggingZone.y = e.getY();
                        }
                	} else if
                	(feld == 2){
                	//neue weite berechen
               		 int newWidth = e.getX() - draggingZone.x;
               		 int newHeight = draggingZone.height + (draggingZone.y - e.getY());
               		 int newY = e.getY();
               		//pr�fen ob mindestgr��en unterschritten wird
                       if(newWidth >= zoneMin)
                       {
               			draggingZone.width = newWidth;
                       }
                       if (newHeight >= zoneMin){
                    	   
               			draggingZone.height = newHeight;
               			draggingZone.y = newY;
                       }
                	} else if
                	(feld == 3){
                	//neue werte berechen
               		 int newWidth = draggingZone.width + (draggingZone.x - e.getX());
               		 int newHeight =e.getY() - draggingZone.y ;
               		 int newX = e.getX();
               		//pr�fen ob mindestgr��en unterschritten wird
                       if(newWidth >= zoneMin)
                       {
                    	draggingZone.x = newX;
               			draggingZone.width = newWidth;
                       }
                       if (newHeight >= zoneMin){
               			draggingZone.height = newHeight;
                       }
                	} else if
                	(feld == 4){
                	//neue werte berechen
               		 int newWidth = e.getX() - draggingZone.x;
               		 int newHeight =e.getY() - draggingZone.y;
               		//pr�fen ob mindestgr��en unterschritten wird
                       if(newWidth >= zoneMin)
                       {
               			draggingZone.width = newWidth;
                       }
                       if (newHeight >= zoneMin){
               			draggingZone.height = newHeight;
                       }
                	} else if
                	(feld == 5){
                	//neue werte berechen
               		 int newX =e.getX() - draggingZone.width/2;
               		 int newY =e.getY() - draggingZone.height/2;

               		 draggingZone.x = newX;
               		 draggingZone.y = newY;

                	}

                }
                repaint();
            
            }
            
       public void mouseMoved(MouseEvent e) {
            }
        }

        
        //paint step
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
		    //alle Zonen malen
			for(Zone z: main.zonen){				
		        //2D graphics kann die liniendicke anpassen
	            Graphics2D g2 = (Graphics2D) g;
	            
	            //wenn die aktive Zone gemalt wird --> rot
	            if(aktiveZone == z){
	            g2.setColor(Color.red);
	            }else{	            
	            g2.setColor(Color.black);
	            }
	            
	            g2.setStroke(new BasicStroke(3));
	            g2.drawRect(z.x,z.y, z.width, z.height);
	            
	            //drag-areas malen
	            g2.setStroke(new BasicStroke(1));
	            
	            //ecken werden ausgemalt
	            g2.setColor(new Color(50,50,50,50));
	            g2.fillRect(z.x-daSize/2,z.y-daSize/2, daSize, daSize);
	            g2.setColor(Color.black);
	            g2.drawRect(z.x-daSize/2,z.y-daSize/2, daSize, daSize);
	            
	            //ecken werden ausgemalt
	            g2.setColor(new Color(50,50,50,50));
	            g2.fillRect(z.x-daSize/2+z.width,z.y-daSize/2, daSize, daSize);
	            g2.setColor(Color.black);	            
	            g2.drawRect(z.x-daSize/2+z.width,z.y-daSize/2, daSize, daSize);
	            

	            //ecken werden ausgemalt
	            g2.setColor(new Color(50,50,50,50));
	            g2.fillRect(z.x-daSize/2,z.y-daSize/2+z.height, daSize, daSize);
	            g2.setColor(Color.black);
	            g2.drawRect(z.x-daSize/2,z.y-daSize/2+z.height, daSize, daSize);	        
	            

	            //ecken werden ausgemalt
	            g2.setColor(new Color(50,50,50,50));
	            g2.fillRect(z.x-daSize/2+z.width,z.y-daSize/2+z.height, daSize, daSize);
	            g2.setColor(Color.black);
	            g2.drawRect(z.x-daSize/2+z.width,z.y-daSize/2+z.height, daSize, daSize);	            
	            
	            
	            //mittelkreuz f�r bewegen der Zone malen
	            int cX = z.x + z.width / 2;
	            int cY = z.y + z.height / 2;
	            
	            g2.setColor(new Color(50,50,50,50));
	            g.fillOval(cX - mkR/2, cY - mkR/2, mkR, mkR);
	            g2.setColor(Color.black);
	            g.drawOval(cX - mkR/2, cY - mkR/2, mkR, mkR);

	            //kreuz
	            g.drawLine(cX-mkR/2, cY-mkR/2, cX+mkR/2, cY+mkR/2);
	            g.drawLine(cX+mkR/2, cY-mkR/2, cX-mkR/2, cY+mkR/2);
	            
			}  
        }

    }