package pNEd;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;


public class Arc extends PetriNetObject {

	protected PlaceTransitionObject fromPetri;
	protected PlaceTransitionObject toPetri;
	
	Arc(PlaceTransitionObject from, PlaceTransitionObject to) {
		fromPetri = from;
		toPetri = to;
	}
	
	private static void drawArrow(Graphics2D g2d, int x0, int y0, int x1, int y1, float stroke) {		
		double aDir=Math.atan2(x0-x1,y0-y1);	// direction of the arrow
		g2d.drawLine(x1,y1,x0,y0);				// draws the line
	    g2d.setStroke(new BasicStroke(1f));		// make the arrow head solid even if dash pattern has been specified
	    Polygon tmpPoly=new Polygon();
	    int i1=12+(int)(stroke*2);
	    int i2=6+(int)stroke;					// make the arrow head the same size regardless of the length
	    tmpPoly.addPoint(x1,y1);				// arrow tip
	    tmpPoly.addPoint(x1+xCor(i1,aDir+.5),y1+yCor(i1,aDir+.5));
	    tmpPoly.addPoint(x1+xCor(i2,aDir),y1+yCor(i2,aDir));
	    tmpPoly.addPoint(x1+xCor(i1,aDir-.5),y1+yCor(i1,aDir-.5));
	    tmpPoly.addPoint(x1,y1);				// arrow tip
	    g2d.drawPolygon(tmpPoly);				// draw the empty arrow
	    g2d.fillPolygon(tmpPoly);				// fills the arrow		
	}
	
	private static int yCor(int len, double dir) {return (int)(len * Math.cos(dir));}
	private static int xCor(int len, double dir) {return (int)(len * Math.sin(dir));}
	
	public PlaceTransitionObject getSource() {
		return fromPetri;
	}
	
	public PlaceTransitionObject getTarget() {
		return toPetri;
	}
	
	@Override
	protected void paintComponent(Graphics g) {	
		super.paintComponent(g);
		
		double dir;
		int x0 = fromPetri.positionX;
		int y0 = fromPetri.positionY;
		int x1 = toPetri.positionX;
		int y1 = toPetri.positionY;

		if(fromPetri instanceof Place && toPetri instanceof Transition) {
			
			x0 = x0 + Place.PLACE_RADIUS/2;
		    y0 = y0 + Place.PLACE_RADIUS/2;
		    y1 = y1 + Transition.CY/2;
		    dir = Math.atan2(x0-x1,y0-y1);
		    
			if(x0 > x1) {
				x1 = x1 + Transition.CX;
			}
			
			x0 = x0 - xCor(Place.PLACE_RADIUS/2, dir);
			y0 = y0 - yCor(Place.PLACE_RADIUS/2, dir);
			
		} else if(fromPetri instanceof Transition && toPetri instanceof Place) {
			
			x1 = x1 + Place.PLACE_RADIUS/2;
		    y1 = y1 + Place.PLACE_RADIUS/2;
		    y0 = y0 + Transition.CY/2;
		    
		    dir = Math.atan2(x0-x1,y0-y1);
		    
			if(x1 > x0) {
				x0 = x0 + Transition.CX;				
			}	

			x1 = x1 + xCor(Place.PLACE_RADIUS/2, dir);
			y1 = y1 + yCor(Place.PLACE_RADIUS/2, dir);
		
		}
		
		drawArrow((Graphics2D)g, x0, y0, x1, y1, 2.5f);		
	}
}
