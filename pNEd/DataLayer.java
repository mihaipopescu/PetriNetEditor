package pNEd;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

public class DataLayer implements Constants {

	/** Hashtable which maps PlaceTransitionObjects to their list of connected arcs */
	private Hashtable<PlaceTransitionObject, ArrayList<PlaceTransitionObject>> arcsMap = null;
	
	public ArrayList<Place> places;
	public ArrayList<Transition> transitions;

	  
	public DataLayer() {
		arcsMap = new Hashtable<PlaceTransitionObject, ArrayList<PlaceTransitionObject>>();
		places = new ArrayList<Place>();
		transitions = new ArrayList<Transition>();
	}

	
	public void makeConnectionMap() {		
		
		Iterator<Place> pit;
		Iterator<Transition> tit;
				
		arcsMap.clear();
		
		pit = places.iterator();
		while(pit.hasNext()) {
			Place pl = pit.next();			
			ArrayList<PlaceTransitionObject> ptal = new ArrayList<PlaceTransitionObject>();
			ArrayList<Transition> trs = pl.getConnectedTransitions();
			tit = trs.iterator();
			while(tit.hasNext()) {
				PlaceTransitionObject pltr = tit.next(); 
				ptal.add(pltr);
			}
			if(ptal.size()!=0) arcsMap.put(pl, ptal);
		}
		
		tit = transitions.iterator();
		while(tit.hasNext()) {
			Transition tr = tit.next();			
			ArrayList<PlaceTransitionObject> ptal = new ArrayList<PlaceTransitionObject>();
			ArrayList<Place> pls = tr.getConnectedPlaces();
			pit = pls.iterator();
			while(pit.hasNext()) {
				PlaceTransitionObject pltr = pit.next();
				ptal.add(pltr);
			}
			if(ptal.size()!=0) arcsMap.put(tr, ptal);
		}
	}
	
	public boolean saveConnectionMap(String dataFile) {
		Enumeration<PlaceTransitionObject> e = arcsMap.keys();
		BufferedWriter out = null;
		
		
		try {
		
			out = new BufferedWriter(new FileWriter(dataFile));			
			
			
			while(e.hasMoreElements()) {
				PlaceTransitionObject key = e.nextElement();			
				ArrayList<PlaceTransitionObject> plar = arcsMap.get(key);
				out.write(key.toString() + "\r\n");		
				
				Iterator<PlaceTransitionObject> ptit = plar.iterator();			
				while(ptit.hasNext()) {
					PlaceTransitionObject pltr = ptit.next();
					if(pltr instanceof Place) {
						Place pl = (Place)pltr;
						out.write("->" + pl.toString()+"\r\n"); 
					}					
					else
					if (pltr instanceof Transition) {
						Transition tr = (Transition)pltr;
						out.write("->" + tr.toString()+"\r\n");
					}
				}
			}
			out.close();
			
		}
		catch(FileNotFoundException ex) {
			System.err.println("File not found!");
			return false;	
		}
		catch (IOException ex) {
			System.err.println("IO Exception !"); 
			return false;
		}
		
		return true;
	}
	
	
}
