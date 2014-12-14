package persistency;

import java.io.IOException;
import java.util.ArrayList;

import model.KlassiekeOpdracht;
import model.Leerling;
import model.Meerkeuze;
import model.Opdracht;
import model.OpdrachtAntwoord;
import model.Opsomming;
import model.Quiz;
import model.QuizDeelname;
import model.QuizOpdracht;
import model.Reproductie;

class MySQLDB implements DBStrategy {

	private DBKlassiekeOpdrachtLeesSchrijf dbKlassiekeOpdrachtLeesSchrijf;
	private DBLeerlingLeesSchrijf dbLeerlingLeesSchrijf;
	private DBMeerkeuzeLeesSchrijf dbMeerkeuzeLeesSchrijf;
	private DBOpdrachtAntwoordLeesSchrijf dbOpdrachtAntwoordLeesSchrijf;
	private DBOpsommingLeesSchrijf dbOpsommingLeesSchrijf;
	private DBQuizDeelnameLeesSchrijf dbQuizDeelnameLeesSchrijf;
	private DBQuizLeesSchrijf dbQuizLeesSchrijf;
	private DBQuizOpdrachtLeesSchrijf dbQuizOpdrachtLeesSchrijf;
	private DBReproductieLeesSchrijf dbReproductieLeesSchrijf;
	
	public MySQLDB(String jdbcConnectionString) {
		this.dbKlassiekeOpdrachtLeesSchrijf = new DBKlassiekeOpdrachtLeesSchrijf(jdbcConnectionString);
		this.dbLeerlingLeesSchrijf = new DBLeerlingLeesSchrijf(jdbcConnectionString);
		this.dbMeerkeuzeLeesSchrijf = new DBMeerkeuzeLeesSchrijf(jdbcConnectionString);
		this.dbOpdrachtAntwoordLeesSchrijf = new DBOpdrachtAntwoordLeesSchrijf(jdbcConnectionString);
		this.dbOpsommingLeesSchrijf = new DBOpsommingLeesSchrijf(jdbcConnectionString);
		this.dbQuizDeelnameLeesSchrijf = new DBQuizDeelnameLeesSchrijf(jdbcConnectionString);
		this.dbQuizLeesSchrijf = new DBQuizLeesSchrijf(jdbcConnectionString);
		this.dbQuizOpdrachtLeesSchrijf = new DBQuizOpdrachtLeesSchrijf(jdbcConnectionString);
		this.dbReproductieLeesSchrijf = new DBReproductieLeesSchrijf(jdbcConnectionString);
	}

	@Override
	public ArrayList<Opdracht> leesOpdrachten() throws IOException {		
			ArrayList<Object> opdrachtObjecten = new ArrayList<Object>();
			opdrachtObjecten.addAll(dbKlassiekeOpdrachtLeesSchrijf.lees());
			opdrachtObjecten.addAll(dbReproductieLeesSchrijf.lees());
			opdrachtObjecten.addAll(dbMeerkeuzeLeesSchrijf.lees());
			opdrachtObjecten.addAll(dbOpsommingLeesSchrijf.lees());
			ArrayList<Opdracht> opdrachten = new ArrayList<Opdracht>();
			for (Object opdracht : opdrachtObjecten) {
				opdrachten.add((Opdracht) opdracht);
			}
			return opdrachten;	
	}

	@Override
	public ArrayList<Leerling> leesLeerlingen() throws IOException {
			return dbLeerlingLeesSchrijf.lees();
	}

	@Override
	public ArrayList<Quiz> leesQuizzen() throws IOException {
			return dbQuizLeesSchrijf.lees();
	}

	@Override
	public ArrayList<PseudoQuizDeelname> leesQuizDeelnames() throws IOException {
			return dbQuizDeelnameLeesSchrijf.lees();	
	}

	@Override
	public ArrayList<PseudoQuizOpdracht> leesQuizOpdrachten() throws IOException {
			return dbQuizOpdrachtLeesSchrijf.lees();
	}

	@Override
	public ArrayList<PseudoOpdrachtAntwoord> leesOpdrachtAntwoorden() throws IOException {
			return dbOpdrachtAntwoordLeesSchrijf.lees();
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
			dbKlassiekeOpdrachtLeesSchrijf.schrijf(klassiekeopdrachten);
			dbMeerkeuzeLeesSchrijf.schrijf(meerkeuzeopdrachten);
			dbOpsommingLeesSchrijf.schrijf(opsommingsopdrachten);
			dbReproductieLeesSchrijf.schrijf(reproductieopdrachten);
	}

	@Override
	public void schrijfLeerlingen(ArrayList<Leerling> leerlingen) throws IOException {
			dbLeerlingLeesSchrijf.schrijf(leerlingen);
	}

	@Override
	public void schrijfQuizzen(ArrayList<Quiz> quizzen) throws IOException {
			dbQuizLeesSchrijf.schrijf(quizzen);
	}

	@Override
	public void schrijfQuizDeelnames(ArrayList<QuizDeelname> quizDeelnames) throws IOException {
			dbQuizDeelnameLeesSchrijf.schrijf(quizDeelnames);
	}

	@Override
	public void schrijfQuizOpdrachten(ArrayList<QuizOpdracht> quizOpdrachten) throws IOException {
			dbQuizOpdrachtLeesSchrijf.schrijf(quizOpdrachten);
	}

	@Override
	public void schrijfOpdrachtAntwoorden(
			ArrayList<OpdrachtAntwoord> opdrachtAntwoorden) throws IOException {
			dbOpdrachtAntwoordLeesSchrijf.schrijf(opdrachtAntwoorden);
	}

}
