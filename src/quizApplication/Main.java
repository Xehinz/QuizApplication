/**
 *
 */
package quizApplication;

import persistency.TxtDB;
import model.*;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author Cool Tim
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {	
		Opdracht opdracht1 = new KlassiekeOpdracht(OpdrachtCategorie.AARDRIJKSKUNDE, Leraar.CHARLOTTE_NEVEN);
		Opdracht opdracht2 = new KlassiekeOpdracht(OpdrachtCategorie.WETENSCHAPPEN, Leraar.MARIA_AERTS);
		
		HashSet<Opdracht> opdrachten = new HashSet<Opdracht>();
		opdrachten.add(opdracht1);
		opdracht1.setMaxAantalPogingen(23);
		System.out.println(opdrachten.contains(opdracht1));
		
	}
}
