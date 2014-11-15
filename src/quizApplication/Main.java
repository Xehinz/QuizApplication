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
		KlassiekeOpdracht kl = new KlassiekeOpdracht(4, new Datum(), "Wat is de hoofdstad van Frankrijk", "Parijs", 1, 15, OpdrachtCategorie.AARDRIJKSKUNDE, Leraar.CHARLOTTE_NEVEN);
		kl.addHint("... - Dakar");
		Meerkeuze m = new Meerkeuze("Welk van de volgende dieren is geen zoogdier?", "Schildpad", 1, 20, OpdrachtCategorie.WETENSCHAPPEN, Leraar.MARIA_AERTS);
		m.setOpties("Olifant;Schildpad;Hond");
		m.addHint("traag maar hard");
		Opsomming o = new Opsomming(4, new Datum(), "Wat zijn de 2 grootste eilanden van de Azoren", "Sao Miguel;Pico", 1, 15, OpdrachtCategorie.AARDRIJKSKUNDE, Leraar.CHARLOTTE_NEVEN, false);
		o.addHint("groen & berg");
		Reproductie r = new Reproductie(4, new Datum(), "Hoe ontstaan nieuwe eilanden", "lava;erosie", 2, 1, 15, OpdrachtCategorie.AARDRIJKSKUNDE, Leraar.CHARLOTTE_NEVEN);
		r.addHint("vulkaan");
		list.add(kl);
		list.add(m);
		list.add(o);
		list.add(r);
		test.schrijfOpdrachten(list);
		System.out.print(test.leesOpdrachten());
	}
}
