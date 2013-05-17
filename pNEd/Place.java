package pNEd;

import java.awt.Color;
import java.awt.Graphics;

public class Place extends PlaceTransitionObject {

	static final int PLACE_RADIUS = 30;
	static int countPlace = 0;
	
	Place(int posX, int posY) {
		super(posX, posY);
		countPlace++;
		setLabelText("P" + countPlace);
		height = width = PLACE_RADIUS;
		setCenter(posX, posY);
		updateBounds();
	}
	
	@Override
	protected void paintComponent(Graphics g) {		
		super.paintComponent(g);		
		g.drawOval(PLACE_TRANSITION_OFFSET, PLACE_TRANSITION_OFFSET, PLACE_RADIUS, PLACE_RADIUS);
				
	}

	
	
}
