import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.UUID;

    public class Overlay extends JPanel {

    	//wird die zone gespeichert wenn gedraggt wird
    	Zone draggingZone;
    	//wird die zuletzt selektierte zone gespeichert (für die einstellungen und löschen usw.)
    	//wird farblich gekennzeichnet
    	UUID aktiveZoneID;
    	int feld; //1 links - oben ,2 rechts - oben, 3 links - unten, 4 rechts - unten, 5 mitte
    	//dragAreaSize
        int daSize = 10;
    	//mittelkreisradius
        int mkR = 25;
        //zonen minimum
        int zoneMin = 50;

        JFrame overlayFrame = new JFrame("Zonen");
        //Overlay overlay = new Overlay();
        //erstellt das fenster und zeigt es an
    
        public void show(){
            overlayFrame.setVisible(true);
            
            updateGuiValues();
        }
        
        public void hide(){
        	overlayFrame.setVisible(false);
        }
        
        Overlay() {
            MyMouseListener listener = new MyMouseListener();
            addMouseListener(listener);
            addMouseMotionListener(listener);
            
        	if(main.zonen.size() > 0){
        		aktiveZoneID = main.zonen.get(0).uuid;
        	}
			overlayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           
            overlayFrame.setContentPane(this);
            
            overlayFrame.setBounds(overlayFrame.getGraphicsConfiguration().getBounds());
            overlayFrame.getContentPane().setLayout(new java.awt.BorderLayout());
            overlayFrame.getContentPane().setBackground(new Color(0, 0, 0, 01));
            overlayFrame.setUndecorated(true);
            overlayFrame.setBackground(new Color(0, 0, 0, 0));
            overlayFrame.setAlwaysOnTop(true);
            overlayFrame.getContentPane().setLayout(null);
            
        }


        //listener fangen die position ab
        class MyMouseListener extends MouseAdapter implements MouseMotionListener{

        	//wenn ein "Feld" geklickt wird wird sich dieses gemerkt
            public void mousePressed(MouseEvent e) {
                for (int i = 0; i < main.zonen.size(); i++) {
                	Zone z = main.zonen.get(i);
                	
                	//aktiveZoneIndex = i;
            		//Linkes eck x & y zwischen    e.x >= z.getX()- daSize/2 && e.x <= z.getX() + daSize/2 
            		//links oben
            		if(
            		e.getX() >= z.getX()-daSize/2 && e.getX() <= z.getX()+daSize+2 &&
            		e.getY() >= z.getY()-daSize/2 && e.getY() <= z.getY()+daSize+2		
            		){
            			draggingZone = z;

            			feld = 1;
            			//repaint();
            		}
            		//rechts oben
            		else if( 
            			e.getX() >= z.getX()+z.getWidth()-daSize/2 && e.getX() <= z.getX()+z.getWidth()+daSize+2 &&
            	    	e.getY() >= z.getY()-daSize/2 && e.getY() <= z.getY()+daSize+2	
            		){
            			draggingZone = z;
            			aktiveZoneID = z.uuid;
            			feld = 2;
            		}
            		//links unten
            		else if( 
            			e.getX() >= z.getX()-daSize/2 && e.getX() <= z.getX()+daSize+2 &&
            	    	e.getY() >= z.getY()+z.getHeight()-daSize/2 && e.getY() <= z.getY()+z.getHeight()+daSize+2	
            		){
            			draggingZone = z;
            			aktiveZoneID = z.uuid;
            			feld = 3;
            		}
            		//rechts unten
            		else if( 
            			e.getX() >= z.getX()+z.getWidth()-daSize/2 && e.getX() <= z.getX()+z.getWidth()+daSize+2 &&
            			e.getY() >= z.getY()+z.getHeight()-daSize/2 && e.getY() <= z.getY()+z.getHeight()+daSize+2	
            		){
            			System.out.print("rechts unten!");
            			draggingZone = z;
            			aktiveZoneID = z.uuid;
            			feld = 4;
            		}    		
            		//mitte
            		else if( 
            			e.getX() >= z.getX()+z.getWidth()/2-mkR/2 && e.getX() <= z.getX()+z.getWidth()/2+mkR+2 &&
            			e.getY() >= z.getY()+z.getHeight()/2-mkR/2 && e.getY() <= z.getY()+z.getHeight()/2+mkR+2	
            		){
            			draggingZone = z;
            			aktiveZoneID = z.uuid;
            			feld = 5;
            		}   
            		
            	}
                updateGuiValues();
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
               
                //falls eine zone ausgewählt ist
                if(draggingZone != null && feld != 0){

                	//links oben
                	if(feld == 1){
                		//neue weite berechen
                		 int newWidth = draggingZone.getWidth() + (draggingZone.getX() - e.getX());
                		 int newHeight = draggingZone.getHeight() + (draggingZone.getY() - e.getY());
                		//prüfen ob mindestgrößen unterschritten wird
                        if(newWidth >= zoneMin)
                        {
                			draggingZone.setWidth(newWidth);
                			draggingZone.setX(e.getX());
                        }
                        if (newHeight >= zoneMin){
                			draggingZone.setHeight(newHeight);
                			draggingZone.setY(e.getY());
                        }
                	} else if
                	(feld == 2){
                	//neue weite berechen
               		 int newWidth = e.getX() - draggingZone.getX();
               		 int newHeight = draggingZone.getHeight() + (draggingZone.getY() - e.getY());
               		 int newY = e.getY();
               		//prüfen ob mindestgrößen unterschritten wird
                       if(newWidth >= zoneMin)
                       {
               			draggingZone.setWidth(newWidth);
                       }
                       if (newHeight >= zoneMin){
                    	   
               			draggingZone.setHeight(newHeight);
               			draggingZone.setY(newY);
                       }
                	} else if
                	(feld == 3){
                	//neue werte berechen
               		 int newWidth = draggingZone.getWidth() + (draggingZone.getX() - e.getX());
               		 int newHeight =e.getY() - draggingZone.getY() ;
               		 int newX = e.getX();
               		//prüfen ob mindestgrößen unterschritten wird
                       if(newWidth >= zoneMin)
                       {
                    	draggingZone.setX(newX);
               			draggingZone.setWidth(newWidth);
                       }
                       if (newHeight >= zoneMin){
               			draggingZone.setHeight(newHeight);
                       }
                	} else if
                	(feld == 4){
                	//neue werte berechen
               		 int newWidth = e.getX() - draggingZone.getX();
               		 int newHeight =e.getY() - draggingZone.getY();
               		//prüfen ob mindestgrößen unterschritten wird
                       if(newWidth >= zoneMin)
                       {
               			draggingZone.setWidth(newWidth);
                       }
                       if (newHeight >= zoneMin){
               			draggingZone.setHeight(newHeight);
                       }
                	} else if
                	(feld == 5){
                	//neue werte berechen
               		 int newX =e.getX() - draggingZone.getWidth()/2;
               		 int newY =e.getY() - draggingZone.getHeight()/2;

               		 draggingZone.setX(newX);
               		 draggingZone.setY(newY);

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
            
            for (int i = 0; i < main.zonen.size(); i++) {
            	Zone z = main.zonen.get(i);
            	
		        //2D graphics kann die liniendicke anpassen
	            Graphics2D g2 = (Graphics2D) g;
	            
	            //wenn die aktive Zone gemalt wird --> rot
	            if(aktiveZoneID == z.uuid){
	            g2.setColor(Color.red);
	            }else{	            
	            g2.setColor(Color.black);
	            }
	            
	            g2.setStroke(new BasicStroke(3));
	            g2.drawRect(z.getX(),z.getY(), z.getWidth(), z.getHeight());
	            
	            //drag-areas malen
	            g2.setStroke(new BasicStroke(1));
	            
	            //ecken werden ausgemalt
	            g2.setColor(new Color(50,50,50,50));
	            g2.fillRect(z.getX()-daSize/2,z.getY()-daSize/2, daSize, daSize);
	            g2.setColor(Color.black);
	            g2.drawRect(z.getX()-daSize/2,z.getY()-daSize/2, daSize, daSize);
	            
	            //ecken werden ausgemalt
	            g2.setColor(new Color(50,50,50,50));
	            g2.fillRect(z.getX()-daSize/2+z.getWidth(),z.getY()-daSize/2, daSize, daSize);
	            g2.setColor(Color.black);	            
	            g2.drawRect(z.getX()-daSize/2+z.getWidth(),z.getY()-daSize/2, daSize, daSize);
	            

	            //ecken werden ausgemalt
	            g2.setColor(new Color(50,50,50,50));
	            g2.fillRect(z.getX()-daSize/2,z.getY()-daSize/2+z.getHeight(), daSize, daSize);
	            g2.setColor(Color.black);
	            g2.drawRect(z.getX()-daSize/2,z.getY()-daSize/2+z.getHeight(), daSize, daSize);	        
	            

	            //ecken werden ausgemalt
	            g2.setColor(new Color(50,50,50,50));
	            g2.fillRect(z.getX()-daSize/2+z.getWidth(),z.getY()-daSize/2+z.getHeight(), daSize, daSize);
	            g2.setColor(Color.black);
	            g2.drawRect(z.getX()-daSize/2+z.getWidth(),z.getY()-daSize/2+z.getHeight(), daSize, daSize);	            
	            
	            
	            //mittelkreuz für bewegen der Zone malen
	            int cX = z.getX() + z.getWidth() / 2;
	            int cY = z.getY() + z.getHeight() / 2;
	            
	            g2.setColor(new Color(50,50,50,50));
	            g.fillOval(cX - mkR/2, cY - mkR/2, mkR, mkR);
	            g2.setColor(Color.black);
	            g.drawOval(cX - mkR/2, cY - mkR/2, mkR, mkR);

	            //kreuz
	            g.drawLine(cX-mkR/2, cY-mkR/2, cX+mkR/2, cY+mkR/2);
	            g.drawLine(cX+mkR/2, cY-mkR/2, cX-mkR/2, cY+mkR/2);
	            
			}  
        }
        
        void updateGuiValues(){

            for (int i = 0; i < main.zonen.size(); i++) {
            	Zone z = main.zonen.get(i);
        	
            	if(z.uuid == aktiveZoneID){
            		main.zd.txtZonenName.setText(z.getName());
            	}
            }
        }
        
        public void reNewPaint(){
        	
        	repaint();
        	overlayFrame.setVisible(false);
        	overlayFrame.setVisible(true);
        	updateGuiValues();
        }

        public void selectFirstZone(){
        	if(main.zonen.size() > 0){
        		main.ov.aktiveZoneID = main.zonen.get(0).uuid;
        		updateGuiValues();
        	}
        }
    }