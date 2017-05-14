import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

    public class Overlay extends JPanel {

    	
    	Zone draggingZone;
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
            //Controls
            
            //TODO: @Phil GUI für den Zoneneditor gestalten
            //TODO: Delete/Copy Buttons sollen neben der Zone dargestellt werden
            //TODO: Editfelder für manuelle Koordinateneingabe
            //bin nicht sicher was benötigt wird (neuanlegen, Zoneneditor schließen usw)
            //ggf. noch mehr Ergänzen falls nötig
        	JLabel lred = new JLabel("");
        	lred.setBounds(10, 140, 100, 20);
        	overlayFrame.getContentPane().add(lred);
        	
            JButton b1 = new JButton();     
            b1.setBounds(0,0,300,30);
            b1.setVisible(true);
            b1.setText("Neue Zone");
            overlayFrame.add(b1);
            
            JButton b2 = new JButton(); 
            b2.setLayout(null);
            b2.setBounds(0,100,300,30);
            b2.setVisible(true);
            b2.setText("Zoneneditor verlassen");
            overlayFrame.add(b2);
            
            overlayFrame.setBounds(overlayFrame.getGraphicsConfiguration().getBounds());
            overlayFrame.getContentPane().setLayout(new java.awt.BorderLayout());
            overlayFrame.getContentPane().setBackground(new Color(0, 0, 0, 01));
            overlayFrame.setUndecorated(true);
            overlayFrame.setBackground(new Color(0, 0, 0, 01));
            overlayFrame.setAlwaysOnTop(true);
            overlayFrame.getContentPane().setLayout(null);

            overlayFrame.setVisible(true);
        }        
        
        Overlay() {
            MyMouseListener listener = new MyMouseListener();
            addMouseListener(listener);
            addMouseMotionListener(listener);
            
            //beim erstellen des Oberlay werden für jede Zone die AktionButtons erstellt
            //falls neue Zonen erstellt werden werden diese Buttons in dem moment ersellt, in welchem eine neue Zone erstellt wird.
           
            /**
            for(Zone z: main.zonen){
            	
            	
            	Frame f = main.overlayFrame;
            	
                JButton b3 = new JButton(); 
                b3.setLayout(null);
                //b3.setBounds(z.x + z.width +10,z.y,100,30);
                b3.setBounds(1000,1000,100,30);
                b3.setVisible(true);
                b3.setText("Delete");
                f.add(b3);
                
            }
            //gui updaten
            main.overlayFrame.revalidate();
            main.overlayFrame.repaint();
        	**/
        
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
            			feld = 2;
            		}
            		//links unten
            		else if( 
            			e.getX() >= z.x-daSize/2 && e.getX() <= z.x+daSize+2 &&
            	    	e.getY() >= z.y+z.height-daSize/2 && e.getY() <= z.y+z.height+daSize+2	
            		){
            			System.out.print("links unten!");
            			draggingZone = z;
            			feld = 3;
            		}
            		//rechts unten
            		else if( 
            			e.getX() >= z.x+z.width-daSize/2 && e.getX() <= z.x+z.width+daSize+2 &&
            			e.getY() >= z.y+z.height-daSize/2 && e.getY() <= z.y+z.height+daSize+2	
            		){
            			System.out.print("rechts unten!");
            			draggingZone = z;
            			feld = 4;
            		}    		
            		//mitte
            		else if( 
            			e.getX() >= z.x+z.width/2-mkR/2 && e.getX() <= z.x+z.width/2+mkR+2 &&
            			e.getY() >= z.y+z.height/2-mkR/2 && e.getY() <= z.y+z.height/2+mkR+2	
            		){
            			System.out.print("mitte!");
            			draggingZone = z;
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
                
                //falls eine zone ausgewählt ist
                if(draggingZone != null && feld != 0){

                	//links oben
                	if(feld == 1){
                		//neue weite berechen
                		 int newWidth = draggingZone.width + (draggingZone.x - e.getX());
                		 int newHeight = draggingZone.height + (draggingZone.y - e.getY());
                		//prüfen ob mindestgrößen unterschritten wird
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
               		//prüfen ob mindestgrößen unterschritten wird
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
               		//prüfen ob mindestgrößen unterschritten wird
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
               		//prüfen ob mindestgrößen unterschritten wird
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