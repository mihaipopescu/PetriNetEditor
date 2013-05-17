package pNEd;

import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;


public class PetriNetObjectHandler extends MouseInputAdapter implements ActionListener {
	
	protected Container content;
	protected PetriNetObject object;
	protected boolean canDrag = false;
	protected Point dragStart = null;
	
	public PetriNetObjectHandler(PetriNetObject pnObject, Container contentPane) {
		object = pnObject;
		content = contentPane;
	}
	
	/** Creates the popup menu that the user will see when they right click on a component */
	public JPopupMenu getPopup() {

		JPopupMenu popup = new JPopupMenu();	
		JMenuItem delItem = new JMenuItem("Delete");
		popup.add(delItem);
		JMenuItem isoItem = new JMenuItem("Isolate");
		popup.add(isoItem);
		delItem.addActionListener(this);
		isoItem.addActionListener(this);
	    return popup;
	}
	
	private void showPopup(MouseEvent e) {		
			JPopupMenu p = getPopup();
			p.show(object, e.getX(), e.getY());
	}	
	
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd.equals("Delete")) {
			PlaceTransitionObject pltr = (PlaceTransitionObject)object;
			pltr.delete();
			pltr.deleteAttachedArcs();
		}
		if(cmd.equals("Isolate")) {
			PlaceTransitionObject pltr = (PlaceTransitionObject)object;
			pltr.deleteAttachedArcs();
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton()==MouseEvent.BUTTON3) {
			
			object.setColor(Color.RED);
			object.repaint();
			showPopup(e);
			
		} else {
			
			if(GuiView.createArc) {
				object.setColor(Color.GREEN);
				object.repaint();
				
				boolean isPlace = GuiView.arcPlace != null;
				boolean isTransition = GuiView.arcTransition != null;
				
				if(!isPlace && !isTransition)
					if (object instanceof Place)
						GuiView.arcPlace = (Place)object;
					else
						if(object instanceof Transition)
							GuiView.arcTransition = (Transition)object;
				
				if ( isPlace && !isTransition && object instanceof Transition) {
					GuiView.arcTransition = (Transition)object;					
					
					Arc arc = new Arc(GuiView.arcPlace, GuiView.arcTransition);
					content.add(arc);					
					arc.setSize(content.getSize().width, content.getSize().height);
					
					GuiView.arcPlace.addConnectTo(arc);
					GuiView.arcTransition.addConnectFrom(arc);
					
		    		GuiView.arcPlace.setColor(Color.BLACK);
		    		GuiView.arcTransition.setColor(Color.BLACK);
		    		
		    		GuiView.arcPlace.repaint();
		    		GuiView.arcTransition.repaint();
		    		
		    		GuiView.arcPlace = null;
		    		GuiView.arcTransition = null;
		    		
		    		arc.repaint();
		    		
				}
				else
				if (!isPlace && isTransition && object instanceof Place) {
					GuiView.arcPlace = (Place)object;
					
					Arc arc = new Arc(GuiView.arcTransition, GuiView.arcPlace);
					content.add(arc);
					arc.setSize(content.getSize().width, content.getSize().height);
		    		
		    		
					GuiView.arcPlace.addConnectFrom(arc);
					GuiView.arcTransition.addConnectTo(arc);
					
		    		GuiView.arcPlace.setColor(Color.BLACK);
		    		GuiView.arcTransition.setColor(Color.BLACK);
		    		
		    		GuiView.arcPlace.repaint();
		    		GuiView.arcTransition.repaint();
		    		
		    		GuiView.arcPlace = null;
		    		GuiView.arcTransition = null;
		    		
		    		arc.repaint();
				}
			} else {
				object.setColor(Color.BLUE);
				object.repaint();
				canDrag = true;
			}
		}			
	}	
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if(canDrag) {
			PlaceTransitionObject pltr = (PlaceTransitionObject)object;
			Point aPoint = e.getPoint();
			if(dragStart==null) dragStart = aPoint;
			aPoint = SwingUtilities.convertPoint(object, aPoint, content);
			pltr.move(aPoint.x, aPoint.y, dragStart.x, dragStart.y);
			pltr.repaint();
			content.repaint();
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		canDrag = false;
		dragStart=null;
		if(e.getButton()==MouseEvent.BUTTON1) {
			object.setColor(Color.BLACK);
			object.repaint();
		}
	}
}
