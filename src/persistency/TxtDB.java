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
 * @version 14/11/2014
 *
 */

public class TxtDB implements DBStrategy {
	
	private TxtLeerlingLeesSchrijf txtLeerlingLeesSchrijf;
	private TxtOpdrachtAntwoordLeesSchrijf txtOpdrachtAntwoordLeesSchrijf;
	private TxtQuizDeelnameLeesSchrijf txtQuizDeelnameLeesSchrijf;
	private TxtQuizLeesSchrijf txtQuizLeesSchrijf;
	private TxtQuizOpdrachtLeesSchrijf txtQuizOpdrachtLeesSchrijf;
	private TxtOpdrachtLeesSchrijf txtOpdrachtLeesSchrijf;
	private TxtKlassiekeOpdrachtLeesSchrijf txtKlassiekeOpdrachtLeesSchrijf;
	private TxtReproductieLeesSchrijf txtReproductieLeesSchrijf;
	private TxtMeerkeuzeLeesSchrijf txtMeerkeuzeLeesSchrijf;
	private TxtOpsommingLeesSchrijf txtOpsommingLeesSchrijf;
	
	public TxtDB() {
		this.txtLeerlingLeesSchrijf = new TxtLeerlingLeesSchrijf();
		this.txtOpdrachtAntwoordLeesSchrijf = new TxtOpdrachtAntwoordLeesSchrijf();
		this.txtQuizDeelnameLeesSchrijf = new TxtQuizDeelnameLeesSchrijf();
		this.txtQuizLeesSchrijf = new TxtQuizLeesSchrijf();
		this.txtQuizOpdrachtLeesSchrijf = new TxtQuizOpdrachtLeesSchrijf();
		this.txtKlassiekeOpdrachtLeesSchrijf=  new TxtKlassiekeOpdrachtLeesSchrijf();
		this.txtReproductieLeesSchrijf = new TxtReproductieLeesSchrijf();
		this.txtMeerkeuzeLeesSchrijf = new TxtMeerkeuzeLeesSchrijf();
		this.txtOpsommingLeesSchrijf = new TxtOpsommingLeesSchrijf();
		this.txtOpdrachtLeesSchrijf = new TxtOpdrachtLeesSchrijf();
	}

	@Override
	public ArrayList<Opdracht> leesOpdrachten() {
		ArrayList<Opdracht> opdrachten = new ArrayList<Opdracht>();
		opdrachten.add(txtKlassiekeOpdrachtLeesSchrijf.lees());
		opdrachten.add(txtReproductieLeesSchrijf.lees());
		opdrachten.add(txtMeerkeuzeLeesSchrijf.lees());
		opdrachten.add(txtOpsommingLeesSchrijf.lees());
		return opdrachten;
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
