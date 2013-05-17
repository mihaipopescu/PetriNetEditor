package pNEd;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class PlaceTransitionObject extends PetriNetObject{

	static final int PLACE_TRANSITION_OFFSET = 10;
	
	/** Position X of the object on the screen */
	protected int positionX;
	/** Position X of the object on the screen */
	protected int positionY;
	protected int width;
	protected int height;
	
	protected boolean selected;
		
	private Collection<Arc> connectTo = new LinkedList<Arc>();
	private Collection<Arc> connectFrom = new LinkedList<Arc>();

	public PlaceTransitionObject(int posX, int posY) {
		positionX = posX;
		positionY = posY;
	}
		
	public void setLabelText(String text) {
		label = text;
	}
	
	public void addConnectTo(Arc toArc) {
		connectTo.add(toArc);
	}
	
	public void addConnectFrom(Arc fromArc) {
		connectFrom.add(fromArc);
	}
	
	public void removeFromArc(Arc oldArc) {
		connectFrom.remove(oldArc);
	}

	public void removeToArc(Arc oldArc) {
		connectTo.remove(oldArc);
	}
	
	public ArrayList<Place> getConnectedPlaces() {
		Arc arc;
		ArrayList<Place> places = new ArrayList<Place>();
		
		Iterator<Arc> it = connectTo.iterator();
		while(it.hasNext()) {
			arc = it.next();
			places.add((Place)arc.toPetri);
		}
		
		return places;
	}
	
	public ArrayList<Transition> getConnectedTransitions() {
		Arc arc;
		ArrayList<Transition> transitions = new ArrayList<Transition>();
		
		Iterator<Arc> it = connectTo.iterator();
		while(it.hasNext()) {
			arc = it.next();
			transitions.add((Transition)arc.toPetri);
		}
		
		return transitions;
	}
	
	//removes any attached arcs
	public void deleteAttachedArcs() {
		Arc arc;
		
		Iterator<Arc> arcsFrom = connectFrom.iterator();		
		while(arcsFrom.hasNext()) {			
			arc = arcsFrom.next();
			arc.fromPetri.removeToArc(arc);
			arcsFrom.remove();
			arc.delete();
		}
		
		Iterator<Arc> arcsTo = connectTo.iterator();		 
		while(arcsTo.hasNext()) {
			arc = arcsTo.next();
			arc.toPetri.removeFromArc(arc);
			arcsTo.remove();
			arc.delete();
		}
	}
	
	public void updateBounds() {
		bounds.setBounds(positionX, positionY, width, height);
		bounds.grow(PLACE_TRANSITION_OFFSET, PLACE_TRANSITION_OFFSET);
		width += PLACE_TRANSITION_OFFSET;
		height+= PLACE_TRANSITION_OFFSET;
		setBounds(bounds);
	}
	
	public void move(int X, int Y, int dx, int dy) {
		positionX = X-dx;
		positionY = Y-dy;
		bounds.setLocation(positionX, positionY);
		positionX += PLACE_TRANSITION_OFFSET;
		positionY += PLACE_TRANSITION_OFFSET;
		setBounds(bounds);
	}
	
	public void setCenter(int X, int Y) {
		positionX = X - width/2;
		positionY = Y - height/2;		
	}
	
	@Override
	protected void paintComponent(Graphics g) {	
		super.paintComponent(g);
		g.drawString(label, 0, 8);		
		g.setColor(objColor);	
	}
}
