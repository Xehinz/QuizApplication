package persistency;

import java.util.ArrayList;

import model.Leerling;
import model.Opdracht;
import model.OpdrachtAntwoord;
import model.Quiz;
import model.QuizDeelname;
import model.QuizOpdracht;

/**
 * klasse die het gebruik van de Txt databank beheert.
 *
 * @author Adriaan Kuipers
 * @version 09/11/2014
 *
 */

public class TxtDB implements DBStrategy {
	
	TxtLeerlingLeesSchrijf txtLeerlingLeesSchrijf;
	TxtOpdrachtAntwoordLeesSchrijf txtOpdrachtAntwoordLeesSchrijf;
	TxtOpdrachtLeesSchrijf txtOpdrachtLeesSchrijf;
	TxtQuizDeelnameLeesSchrijf txtQuizDeelnameLeesSchrijf;
	TxtQuizLeesSchrijf txtQuizLeesSchrijf;
	TxtQuizOpdrachtLeesSchrijf txtQuizOpdrachtLeesSchrijf;
	
	

	@Override
	public ArrayList<Opdracht> leesOpdrachten() {
		return txtOpdrachtLeesSchrijf.lees();
	}

	@Override
	public ArrayList<Leerling> leesLeerlingen() {
		return txtLeerlingLeesSchrijf.lees();
	}

	@Override
	public ArrayList<Quiz> leesQuizzen() {
		return txtQuizLeesSchrijf.lees();
	}

	@Override
	public ArrayList<PseudoQuizDeelname> leesQuizDeelnames() {
		return txtQuizDeelnameLeesSchrijf.lees();
	}

	@Override
	public ArrayList<PseudoQuizOpdracht> leesQuizOpdrachten() {
		return txtQuizOpdrachtLeesSchrijf.lees();
	}

	@Override
	public ArrayList<PseudoOpdrachtAntwoord> leesOpdrachtAntwoorden() {
		return txtOpdrachtAntwoordLeesSchrijf.lees();
	}

	@Override
	public void schrijfOpdrachten(ArrayList<Opdracht> opdrachten) {
		txtOpdrachtLeesSchrijf.schrijf(opdrachten);
	}

	@Override
	public void schrijfLeerlingen(ArrayList<Leerling> leerlingen) {
		txtLeerlingLeesSchrijf.schrijf(leerlingen);
	}

	@Override
	public void schrijfQuizzen(ArrayList<Quiz> quizzen) {
		txtQuizLeesSchrijf.schrijf(quizzen);
	}

	@Override
	public void schrijfQuizDeelnames(ArrayList<QuizDeelname> quizDeelnames) {
		txtQuizDeelnameLeesSchrijf.schrijf(quizDeelnames);
	}

	@Override
	public void schrijfQuizOpdrachten(ArrayList<QuizOpdracht> quizOpdrachten) {
		txtQuizOpdrachtLeesSchrijf.schrijf(quizOpdrachten);
	}

	@Override
	public void schrijfOpdrachtAntwoorden(ArrayList<OpdrachtAntwoord> opdrachtAntwoorden) {
		txtOpdrachtAntwoordLeesSchrijf.schrijf(opdrachtAntwoorden);
	}

}
