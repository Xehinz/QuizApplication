package persistency;

import java.io.IOException;
import java.util.ArrayList;

import model.Leerling;
import model.Opdracht;
import model.OpdrachtAntwoord;
import model.Quiz;
import model.QuizDeelname;
import model.QuizOpdracht;
import model.KlassiekeOpdracht;
import model.Meerkeuze;
import model.Opsomming;
import model.Reproductie;

/**
 * klasse die het gebruik van de Txt databank beheert.
 *
 * @author Adriaan Kuipers
 * @version 15/11/2014
 *
 */

class TxtDB implements DBStrategy {

	private TxtLeerlingLeesSchrijf txtLeerlingLeesSchrijf;
	private TxtOpdrachtAntwoordLeesSchrijf txtOpdrachtAntwoordLeesSchrijf;
	private TxtQuizDeelnameLeesSchrijf txtQuizDeelnameLeesSchrijf;
	private TxtQuizLeesSchrijf txtQuizLeesSchrijf;
	private TxtQuizOpdrachtLeesSchrijf txtQuizOpdrachtLeesSchrijf;
	private TxtKlassiekeOpdrachtLeesSchrijf txtKlassiekeOpdrachtLeesSchrijf;
	private TxtReproductieLeesSchrijf txtReproductieLeesSchrijf;
	private TxtMeerkeuzeLeesSchrijf txtMeerkeuzeLeesSchrijf;
	private TxtOpsommingLeesSchrijf txtOpsommingLeesSchrijf;

	public TxtDB(boolean useCSV) {
		this.txtLeerlingLeesSchrijf = new TxtLeerlingLeesSchrijf(useCSV);		
		this.txtOpdrachtAntwoordLeesSchrijf = new TxtOpdrachtAntwoordLeesSchrijf(useCSV);
		this.txtQuizDeelnameLeesSchrijf = new TxtQuizDeelnameLeesSchrijf(useCSV);
		this.txtQuizLeesSchrijf = new TxtQuizLeesSchrijf(useCSV);
		this.txtQuizOpdrachtLeesSchrijf = new TxtQuizOpdrachtLeesSchrijf(useCSV);
		this.txtKlassiekeOpdrachtLeesSchrijf = new TxtKlassiekeOpdrachtLeesSchrijf(useCSV);
		this.txtReproductieLeesSchrijf = new TxtReproductieLeesSchrijf(useCSV);
		this.txtMeerkeuzeLeesSchrijf = new TxtMeerkeuzeLeesSchrijf(useCSV);
		this.txtOpsommingLeesSchrijf = new TxtOpsommingLeesSchrijf(useCSV);
	}

	@Override
	public ArrayList<Opdracht> leesOpdrachten() throws IOException {
		ArrayList<Object> opdrachtObjecten = new ArrayList<Object>();
		opdrachtObjecten.addAll(txtKlassiekeOpdrachtLeesSchrijf.lees());
		opdrachtObjecten.addAll(txtReproductieLeesSchrijf.lees());
		opdrachtObjecten.addAll(txtMeerkeuzeLeesSchrijf.lees());
		opdrachtObjecten.addAll(txtOpsommingLeesSchrijf.lees());
		ArrayList<Opdracht> opdrachten = new ArrayList<Opdracht>();
		for (Object opdracht : opdrachtObjecten) {
			opdrachten.add((Opdracht) opdracht);
		}
		return opdrachten;
	}

	@Override
	public ArrayList<Leerling> leesLeerlingen() throws IOException {
		return txtLeerlingLeesSchrijf.lees();
	}

	@Override
	public ArrayList<Quiz> leesQuizzen() throws IOException {
		return txtQuizLeesSchrijf.lees();
	}

	@Override
	public ArrayList<PseudoQuizDeelname> leesQuizDeelnames() throws IOException {
		return txtQuizDeelnameLeesSchrijf.lees();
	}

	@Override
	public ArrayList<PseudoQuizOpdracht> leesQuizOpdrachten() throws IOException {
		return txtQuizOpdrachtLeesSchrijf.lees();
	}

	@Override
	public ArrayList<PseudoOpdrachtAntwoord> leesOpdrachtAntwoorden() throws IOException {
		return txtOpdrachtAntwoordLeesSchrijf.lees();
	}

	@Override
	public void schrijfOpdrachten(ArrayList<Opdracht> opdrachten) throws IOException {
		ArrayList<Opdracht> klassiekeopdrachten = new ArrayList<Opdracht>();
		ArrayList<Opdracht> meerkeuzeopdrachten = new ArrayList<Opdracht>();
		ArrayList<Opdracht> opsommingsopdrachten = new ArrayList<Opdracht>();
		ArrayList<Opdracht> reproductieopdrachten = new ArrayList<Opdracht>();
		for (Opdracht opdracht : opdrachten) {
			if (opdracht instanceof KlassiekeOpdracht) {
				klassiekeopdrachten.add(opdracht);
			}
			if (opdracht instanceof Meerkeuze) {
				meerkeuzeopdrachten.add(opdracht);
			}
			if (opdracht instanceof Opsomming) {
				opsommingsopdrachten.add(opdracht);
			}
			if (opdracht instanceof Reproductie) {
				reproductieopdrachten.add(opdracht);
			}
		}
		txtKlassiekeOpdrachtLeesSchrijf.schrijf(klassiekeopdrachten);
		txtMeerkeuzeLeesSchrijf.schrijf(meerkeuzeopdrachten);
		txtOpsommingLeesSchrijf.schrijf(opsommingsopdrachten);
		txtReproductieLeesSchrijf.schrijf(reproductieopdrachten);
	}

	@Override
	public void schrijfLeerlingen(ArrayList<Leerling> leerlingen) throws IOException {
		txtLeerlingLeesSchrijf.schrijf(leerlingen);
	}

	@Override
	public void schrijfQuizzen(ArrayList<Quiz> quizzen) throws IOException {
		txtQuizLeesSchrijf.schrijf(quizzen);
	}

	@Override
	public void schrijfQuizDeelnames(ArrayList<QuizDeelname> quizDeelnames) throws IOException {
		txtQuizDeelnameLeesSchrijf.schrijf(quizDeelnames);
	}

	@Override
	public void schrijfQuizOpdrachten(ArrayList<QuizOpdracht> quizOpdrachten) throws IOException {
		txtQuizOpdrachtLeesSchrijf.schrijf(quizOpdrachten);
	}

	@Override
	public void schrijfOpdrachtAntwoorden(
			ArrayList<OpdrachtAntwoord> opdrachtAntwoorden) throws IOException {
		txtOpdrachtAntwoordLeesSchrijf.schrijf(opdrachtAntwoorden);
	}

}
