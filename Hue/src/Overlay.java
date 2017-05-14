import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

    public class Overlay extends JPanel {

    	
    	Zone aktiveZone;
    	int feld; //1 links - oben ,2 rechts - oben, 3 links - unten, 4 rechts - unten, 5 mitte
    	//dragAreaSize
        int daSize = 10;
    	//mittelkreisradius
        int mkR = 25;
        //zonen minimum
        int zoneMin = 50;
        
        
        
        
        Overlay() {
            MyMouseListener listener = new MyMouseListener();
            addMouseListener(listener);
            addMouseMotionListener(listener);
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
            			aktiveZone = z;
            			feld = 2;
            		}
            		//links unten
            		else if( 
            			e.getX() >= z.x-daSize/2 && e.getX() <= z.x+daSize+2 &&
            	    	e.getY() >= z.y+z.height-daSize/2 && e.getY() <= z.y+z.height+daSize+2	
            		){
            			System.out.print("links unten!");
            			aktiveZone = z;
            			feld = 3;
            		}
            		//rechts unten
            		else if( 
            			e.getX() >= z.x+z.width-daSize/2 && e.getX() <= z.x+z.width+daSize+2 &&
            			e.getY() >= z.y+z.height-daSize/2 && e.getY() <= z.y+z.height+daSize+2	
            		){
            			System.out.print("rechts unten!");
            			aktiveZone = z;
            			feld = 4;
            		}    		
            		//mitte
            		else if( 
            			e.getX() >= z.x+z.width/2-mkR/2 && e.getX() <= z.x+z.width/2+mkR+2 &&
            			e.getY() >= z.y+z.height/2-mkR/2 && e.getY() <= z.y+z.height/2+mkR+2	
            		){
            			System.out.print("mitte!");
            			aktiveZone = z;
            			feld = 5;
            		}   
            		
            	}
            }
            
            //beim loslassen wird das gemerkte feld und die zone wieder verworfen
            public void mouseReleased(MouseEvent e) {
                //setEndPoint(e.getX(), e.getY());
            	
            	aktiveZone = null;
            	feld = 0;
                repaint();
            }
            
          //wird die maus bewegt werden die werte angepasst und erneut gemalt
            public void mouseDragged(MouseEvent e) {
                System.out.println("Mouse dragged");
                
                //falls eine zone ausgewählt ist
                if(aktiveZone != null && feld != 0){

                	//links oben
                	if(feld == 1){
                		//neue weite berechen
                		 int newWidth = aktiveZone.width + (aktiveZone.x - e.getX());
                		 int newHeight = aktiveZone.height + (aktiveZone.y - e.getY());
                		//prüfen ob mindestgrößen unterschritten wird
                        if(newWidth >= zoneMin)
                        {
                			aktiveZone.width = newWidth;
                			aktiveZone.x = e.getX();
                        }
                        if (newHeight >= zoneMin){
                			aktiveZone.height = newHeight;
                			aktiveZone.y = e.getY();
                        }
                	} else if
                	(feld == 2){
                	//neue weite berechen
               		 int newWidth = e.getX() - aktiveZone.x;
               		 int newHeight = aktiveZone.height + (aktiveZone.y - e.getY());
               		 int newY = e.getY();
               		//prüfen ob mindestgrößen unterschritten wird
                       if(newWidth >= zoneMin)
                       {
               			aktiveZone.width = newWidth;
                       }
                       if (newHeight >= zoneMin){
                    	   
               			aktiveZone.height = newHeight;
               			aktiveZone.y = newY;
                       }
                	} else if
                	(feld == 3){
                	//neue werte berechen
               		 int newWidth = aktiveZone.width + (aktiveZone.x - e.getX());
               		 int newHeight =e.getY() - aktiveZone.y ;
               		 int newX = e.getX();
               		//prüfen ob mindestgrößen unterschritten wird
                       if(newWidth >= zoneMin)
                       {
                    	aktiveZone.x = newX;
               			aktiveZone.width = newWidth;
                       }
                       if (newHeight >= zoneMin){
               			aktiveZone.height = newHeight;
                       }
                	} else if
                	(feld == 4){
                	//neue werte berechen
               		 int newWidth = e.getX() - aktiveZone.x;
               		 int newHeight =e.getY() - aktiveZone.y;
               		//prüfen ob mindestgrößen unterschritten wird
                       if(newWidth >= zoneMin)
                       {
               			aktiveZone.width = newWidth;
                       }
                       if (newHeight >= zoneMin){
               			aktiveZone.height = newHeight;
                       }
                	} else if
                	(feld == 5){
                	//neue werte berechen
               		 int newX =e.getX() - aktiveZone.width/2;
               		 int newY =e.getY() - aktiveZone.height/2;

               		 aktiveZone.x = newX;
               		 aktiveZone.y = newY;

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
	            g2.setColor(Color.black);
	            g2.setStroke(new BasicStroke(3));
	            g2.drawRect(z.x,z.y, z.width, z.height);
	            
	            //drag-areas malen
	            g2.setColor(Color.black);
	            g2.setStroke(new BasicStroke(1));
	            g2.drawRect(z.x-daSize/2,z.y-daSize/2, daSize, daSize);
	            g2.drawRect(z.x-daSize/2+z.width,z.y-daSize/2, daSize, daSize);
	            g2.drawRect(z.x-daSize/2,z.y-daSize/2+z.height, daSize, daSize);
	            g2.drawRect(z.x-daSize/2+z.width,z.y-daSize/2+z.height, daSize, daSize);
		        
	            //mittelkreuz für bewegen der Zone malen
	            int cX = z.x + z.width / 2;
	            int cY = z.y + z.height / 2;
	            g.drawOval(cX - mkR/2, cY - mkR/2, mkR, mkR);
	            //kreuz
	            g.drawLine(cX-mkR/2, cY-mkR/2, cX+mkR/2, cY+mkR/2);
	            g.drawLine(cX+mkR/2, cY-mkR/2, cX-mkR/2, cY+mkR/2);
			}  
        }

    }