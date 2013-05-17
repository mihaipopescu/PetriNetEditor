package pNEd;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

public class GuiView extends JPanel implements Constants, MouseListener, MouseMotionListener {

	public static DataLayer data = new DataLayer();
	public static boolean createArc = false;
	public static Place arcPlace = null;
	public static Transition arcTransition = null;

	private int iCreateType = CRTYPE_INVALID;	
	private int iMode = MODE_SELECT;
	public boolean enableGrid = false;
	static Point aPoint = null;
	
	
	public GuiView() {
		
		setLayout(null);
		setOpaque(true);
		setDoubleBuffered(true);
		setBackground(Color.WHITE);
		addMouseListener(this);		
	}
	
	public void setCreateType(int type) {
		iCreateType = type;
	}

	public void setMode(int mode) {
		iMode = mode;
	}

	public void mouseMoved(MouseEvent e) {	}
	
	public void mouseDragged(MouseEvent e) { }
	
    public void mouseClicked(MouseEvent e) { }
    
    public void mouseEntered(MouseEvent e) { }
    
    public void mouseExited(MouseEvent e)  { }    
   
    public void mousePressed(MouseEvent e) { }
    
    public void mouseReleased(MouseEvent e) {
    	
    	int x = e.getX();
    	int y = e.getY();
    	
    	
    	if(iMode==MODE_CREATE && e.getButton()==MouseEvent.BUTTON1) {
	    	switch(iCreateType) {
	    	
	    	case CRTYPE_PLACE:	    			    		
	        	Place pl = new Place(x, y);
	        	add(pl);
	        	GuiView.data.places.add(pl);
	        	PetriNetObjectHandler plhandler = new PetriNetObjectHandler(pl, this);
	        	pl.addMouseListener(plhandler);
	        	pl.addMouseMotionListener(plhandler);
	        	pl.repaint();	        	    	
	        	
	    		break;
	    		
	    	case CRTYPE_TRANSITION:
	    		Transition tr = new Transition(x, y);
	    		add(tr);
	    		GuiView.data.transitions.add(tr);
	    		PetriNetObjectHandler trhandler = new PetriNetObjectHandler(tr, this); 
	    		tr.addMouseListener(trhandler);
	    		tr.addMouseMotionListener(trhandler);
	    		tr.repaint();
	    		break;
	    		
//	    	case CRTYPE_ARC:
//	    		if(aPoint==null) {
//	    			
//	    			aPoint = new Point(x, y);
//	    			
//	    		} else {
//	    			Place pla = new Place(x, y);
//	    			add(pla);
//	    			pla.repaint();
//	    			
//	    			Transition tra = new Transition(aPoint.x, aPoint.y);
//	    			add(tra);
//	    			tra.repaint();
//	    			
//	    			Arc ar = new Arc(tra, pla);
//		    		add(ar);
//		    		ar.setSize(getSize().width, getSize().height);
//		    		ar.repaint();
//		    		
//		    		aPoint = null;
//	    		}
//	    		break;
	    	}
    	}
    	
    	validate();    	
    }
	
	public void updatePreferredSize() {
		// iterate over net objects
		// setPreferredSize() accordingly
		Component[] components=getComponents();
		Dimension d=new Dimension(0,0);
		for(int i=0;i<components.length;i++) {
			//if(components[i] instanceof SelectionObject) continue; // SelectionObject not included
			Rectangle r=components[i].getBounds();
			int x=r.x+r.width+100;
			int y=r.y+r.height+100;
			if(x>d.width)  d.width =x;
			if(y>d.height) d.height=y;
		}
		setPreferredSize(d);

		getParent().validate();
	}
	
	public void addNewPetriNetObject(PetriNetObject newObj) {
		

		if(newObj!=null) {
			
			if(newObj instanceof Place) {
				add(newObj);
			}
				
			
			
		}
			
	}
	
    private void drawGrid(Graphics g, int gridSpace) {
        Insets insets = getInsets();
        int firstX = insets.left;
        int firstY = insets.top;
        int lastX = getWidth() - insets.right;
        int lastY = getHeight() - insets.bottom;
        
        //Draw vertical lines.
        int x = firstX;
        while (x < lastX) {
            g.drawLine(x, firstY, x, lastY);
            x += gridSpace;
        }
        
        //Draw horizontal lines.
        int y = firstY;
        while (y < lastY) {
            g.drawLine(firstX, y, lastX, y);
            y += gridSpace;
        }
    }
	
	public void paintComponent(Graphics g) {		
		super.paintComponent(g);
		if(enableGrid) {
			g.setColor(Color.LIGHT_GRAY);
			drawGrid(g, 50);
		}
	}

}
