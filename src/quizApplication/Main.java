/**
 *
 */
package quizApplication;

import persistency.TxtDB;
import model.*;

import java.util.ArrayList;
import java.util.HashSet;
import util.datumWrapper.Datum;

/**
 * @author Cool Tim
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {	
		TxtDB test = new TxtDB();
		ArrayList<Opdracht> list = new ArrayList<Opdracht>();
		KlassiekeOpdracht t = new KlassiekeOpdracht(4, new Datum(), "Wat is de hoofdstad van Frankrijk", "Parijs", 1, 15, OpdrachtCategorie.AARDRIJKSKUNDE, Leraar.CHARLOTTE_NEVEN);
		t.addHint("hint");
		list.add(t);
		test.schrijfOpdrachten(list);		
	}
}
