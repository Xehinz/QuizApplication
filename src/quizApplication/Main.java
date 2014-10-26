/**
 *
 */
package quizApplication;

import model.Leraar;
import model.Opdracht;
import model.OpdrachtCategorie;

/**
 * @author Cool Tim
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Opdracht o = new Opdracht("Wie ben ik?", "Ben", OpdrachtCategorie.AARDRIJKSKUNDE, Leraar.CHARLOTTE_NEVEN);
		System.out.println(o);
	}

}
