/**
 *
 */
package quizApplication;

import persistency.TxtDB;
import model.*;
import java.util.ArrayList;

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
		ArrayList<Leerling> list = new ArrayList<Leerling>();
		 
		list = test.leesLeerlingen();
		System.out.print(list.toString());
		
	}
}
