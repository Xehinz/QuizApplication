/**
 *
 */
package quizApplication;

import java.util.ArrayList;

import model.Leerling;
import persistency.TxtLeerlingLeesSchrijf;

/**
 * @author Cool Tim
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Leerling leerling1 = new Leerling("Jos", "Verbeek", 3);
		Leerling leerling2 = new Leerling("Maria", "Ratel", 5);

		ArrayList<Leerling> leerlingen = new ArrayList<Leerling>();
		leerlingen.add(leerling1);
		leerlingen.add(leerling2);

		TxtLeerlingLeesSchrijf schrijver = new TxtLeerlingLeesSchrijf();
		schrijver.useCSV(true);
		schrijver.schrijf(leerlingen);

		ArrayList<Leerling> opgehaaldUitCSV = schrijver.lees();
		for (Leerling l : opgehaaldUitCSV) {
			System.out.println(l);
		}

	}
}
