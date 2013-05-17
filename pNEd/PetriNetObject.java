package pNEd;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JComponent;

public abstract class PetriNetObject extends JComponent{

	protected String label = new String();
	protected Rectangle bounds = new Rectangle(); //used in selction 
	protected boolean objSelected = false;	
	protected Color objColor = Color.BLACK;
	
	/**
	 * Returns the label of a Petri Net Object
	 * @return
	 */
	public String getLabelText() {
		return label;
	}
	
	@Override
	protected void paintComponent(Graphics g) {	
		super.paintComponent(g);
	}
	
	public void delete() {
		Container c = getParent();
		if(c!=null) c.remove(this);
		c.repaint();
	}
	
	public void setColor(Color c) {
		objColor = c;
	}
	
	public void toggleSelected() {
		objSelected = !objSelected;
	}
	
	public void select() {
		objSelected = true;
	}

	public void deselect() {
		objSelected = false;
	}
	
	public boolean isSelected() {
		return objSelected;
	}
}
