package pNEd;

import java.awt.Color;
import java.awt.Graphics;

public class Transition extends PlaceTransitionObject {

	static final int CX = 10;
	static final int CY = 30;
	static int TransitionObjCount = 0;
	
	Transition(int posX, int posY) {
		super(posX,posY);
		TransitionObjCount++;
		width = CX;
		height = CY;
		setLabelText("T" + TransitionObjCount);
		setCenter(posX, posY);
		updateBounds();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.fillRect(PLACE_TRANSITION_OFFSET, PLACE_TRANSITION_OFFSET, CX, CY);		
	}
	
}
