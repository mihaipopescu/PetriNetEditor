package pNEd;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.UIManager;


public class PetriNetEditor extends JFrame implements Constants, ActionListener {
		
	private boolean createMode = false;
	private GuiView drawingPane;
	
	
	public PetriNetEditor() {
		super("PetriNetEditor");
		initGUI();
	}
	
	private void initGUI()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception exc) {
			System.err.println("Error loading L&F: " + exc);
		}
		
		setSize(600, 400);
		
		JMenuBar menubar = new JMenuBar();
		
		JMenu filemnu = new JMenu("File");
			JMenuItem openItem = new JMenuItem("Open"); 	// PNML File
	        openItem.setMnemonic('O');	        
	        openItem.setAccelerator(KeyStroke.getKeyStroke("control O"));
	        JMenuItem saveItem = new JMenuItem("Save");
	        saveItem.setMnemonic('S');
	        saveItem.setAccelerator(KeyStroke.getKeyStroke("control S"));
	        JMenuItem quitItem = new JMenuItem("Quit");
	        quitItem.setMnemonic('Q');
	        quitItem.setAccelerator(KeyStroke.getKeyStroke("control Q"));
	    
	    filemnu.setMnemonic('F');
	    filemnu.add(openItem);
	    filemnu.add(saveItem);
	    saveItem.addActionListener(this);
	    filemnu.addSeparator();
	    filemnu.add(quitItem);
	    
	    menubar.add(filemnu);
	    
		JMenu editmnu = new JMenu("Edit");
		editmnu.setMnemonic('E');
			JMenu pnew = new JMenu("Create");
			pnew.setMnemonic('C');
				JCheckBoxMenuItem createSwitch = new JCheckBoxMenuItem("Create Mode");
				createSwitch.setMnemonic('O');
				createSwitch.setAccelerator(KeyStroke.getKeyStroke("control C"));
				pnew.add(createSwitch);
				
				pnew.addSeparator();
				
				JRadioButtonMenuItem place = new JRadioButtonMenuItem("Place"); 
				place.setMnemonic('P');
				place.setAccelerator(KeyStroke.getKeyStroke("control P"));
			
				JRadioButtonMenuItem transition = new JRadioButtonMenuItem("Transition");
				transition.setMnemonic('T');
				transition.setAccelerator(KeyStroke.getKeyStroke("control T"));
				
				JRadioButtonMenuItem arc = new JRadioButtonMenuItem("Arc");
				arc.setMnemonic('A');
				arc.setAccelerator(KeyStroke.getKeyStroke("control A"));
			
				ButtonGroup bg = new ButtonGroup();
				bg.add(place);
				bg.add(transition);
				bg.add(arc);				
				
								
			pnew.add(place);
			pnew.add(transition);
			pnew.add(arc);
		
		JMenu grid = new JMenu("Grid");
			JCheckBoxMenuItem hide = new JCheckBoxMenuItem("Hide Grid");
			hide.setMnemonic('H');
			hide.setSelected(true);
			hide.setAccelerator(KeyStroke.getKeyStroke("control G"));
			grid.add(hide);
			grid.addSeparator();
			
		
		editmnu.add(grid);
		editmnu.add(pnew);
		
		menubar.add(editmnu);
		
		setJMenuBar(menubar);	
		
		place.addActionListener(this);
		transition.addActionListener(this);
		arc.addActionListener(this);
		hide.addActionListener(this);
		createSwitch.addActionListener(this);
		quitItem.addActionListener(this);
		
		drawingPane =  new GuiView();
		getContentPane().add(drawingPane);		
		 
	}	

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		drawingPane.createArc = false;
		
		if(cmd.equals("Quit"))
			System.exit(0);
		else
		if(cmd.equals("Save")) {
			GuiView.data.makeConnectionMap();
			GuiView.data.saveConnectionMap("petri.txt");
		}
		else
		if(cmd.equals("Load")) {
			System.out.println("Not yet implemented!");
		}
		else
		if(cmd.equals("Place"))
			drawingPane.setCreateType(CRTYPE_PLACE);			
		else
		if(cmd.equals("Transition"))
			drawingPane.setCreateType(CRTYPE_TRANSITION);
		else
		if(cmd.equals("Arc")) {
			drawingPane.createArc = true;
			drawingPane.setCreateType(CRTYPE_ARC);
		}
		else
		if(cmd.equals("Create Mode")) {
			createMode = !createMode;
			
			if(createMode) 
				drawingPane.setMode(MODE_CREATE);
			else
				drawingPane.setMode(MODE_SELECT);
		}			
		else
		if(cmd.equals("Hide Grid")) {
			drawingPane.enableGrid = !drawingPane.enableGrid;
			drawingPane.repaint();
			drawingPane.validate();
		}

	}


}
