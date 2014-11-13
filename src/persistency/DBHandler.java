package persistency;

import java.io.IOException;
import java.util.ArrayList;

import model.Leerling;
import model.LeerlingContainer;
import model.Opdracht;
import model.OpdrachtAntwoord;
import model.OpdrachtCatalogus;
import model.Quiz;
import model.QuizCatalogus;
import model.QuizDeelname;
import model.QuizOpdracht;

public class DBHandler {

	private DBStrategy dbStrategy;
	private OpdrachtCatalogus opdrachtCatalogus;
	private LeerlingContainer leerlingContainer;
	private QuizCatalogus quizCatalogus;
	
	public DBHandler() {
		opdrachtCatalogus = new OpdrachtCatalogus();
		leerlingContainer = new LeerlingContainer();
		quizCatalogus = new QuizCatalogus();
		dbStrategy = new TxtDB();
	}
	
	public DBHandler(OpdrachtCatalogus opdrachtCatalogus, LeerlingContainer leerlingContainer, QuizCatalogus quizCatalogus) {		
		this.opdrachtCatalogus = opdrachtCatalogus;
		this.leerlingContainer = leerlingContainer;
		this.quizCatalogus = quizCatalogus;
		dbStrategy = new TxtDB();
	}	
	
	public void vulCatalogi() throws IOException {
		opdrachtCatalogus = new OpdrachtCatalogus(dbStrategy.leesOpdrachten());
		leerlingContainer = new LeerlingContainer(dbStrategy.leesLeerlingen());
		quizCatalogus = new QuizCatalogus(dbStrategy.leesQuizzen());
		koppelQuizzenAanOpdrachten();
		koppelLeerlingenAanQuizzen();
		koppelQuizDeelnamesAanQuizOpdrachten();
	}
	
	public void saveCatalogi() {
		dbStrategy.schrijfOpdrachten(opdrachtCatalogus.getOpdrachten());
		dbStrategy.schrijfLeerlingen(leerlingContainer.getLeerlingen());
		dbStrategy.schrijfQuizzen(quizCatalogus.getQuizzen());
		dbStrategy.schrijfQuizOpdrachten(quizOpdrachten());
		dbStrategy.schrijfQuizDeelnames(quizDeelnames());
		dbStrategy.schrijfOpdrachtAntwoorden(opdrachtAntwoorden());
	}
	
	public void setDBStrategy(StorageStrategy storageStrategy) {
		switch (storageStrategy) {
		case TEKST:
			dbStrategy = new TxtDB();
			break;
		case DATABASE:
			dbStrategy = new MySQLDB();
			break;
		}
	}
	
	public OpdrachtCatalogus getOpdrachtCatalogus() {
		return opdrachtCatalogus;
	}
	
	public QuizCatalogus getQuizCatalogus() {
		return quizCatalogus;
	}
	
	public LeerlingContainer getLeerlingContainer() {
		return leerlingContainer;
	}
	
	private void koppelQuizzenAanOpdrachten() {
		
	}
	
	private void koppelLeerlingenAanQuizzen() {
		
	}

	/**
	 * Deze method werkt enkel als ze pas wordt aangeroepen nadat de Catalogus
	 * en Container klasses zijn opgevuld én nadat ook alle QuizDeelnames en
	 * QuizOpdrachten zijn ingelezen.
	 */
	private void koppelQuizDeelnamesAanQuizOpdrachten() throws IOException {
		ArrayList<PseudoOpdrachtAntwoord> opdrachtAntwoorden = dbStrategy
				.leesOpdrachtAntwoorden();

		QuizOpdracht huidigeQuizOpdracht = null;
		QuizDeelname huidigeQuizDeelname = null;

		ArrayList<Opdracht> opdrachten = opdrachtCatalogus.getOpdrachten();
		ArrayList<Leerling> leerlingen = leerlingContainer.getLeerlingen();

		for (PseudoOpdrachtAntwoord opdrachtAntwoord : opdrachtAntwoorden) {
			for (int i = 0; i < opdrachten.size()
					&& huidigeQuizOpdracht == null; i++) {
				huidigeQuizOpdracht = opdrachten.get(i).getQuizOpdracht(
						opdrachtAntwoord.getQuizOpdrachtID());
			}
			for (int i = 0; i < leerlingen.size()
					&& huidigeQuizDeelname == null; i++) {
				huidigeQuizDeelname = leerlingen.get(i).getQuizDeelname(
						opdrachtAntwoord.getQuizDeelnameID());
			}
			if (huidigeQuizDeelname == null || huidigeQuizOpdracht == null) {
				throw new IOException(
						"De opgeslagen QuizDeelnameID en/of QuizOpdrachtID werd(en) niet gevonden");
			}
			OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(
					huidigeQuizDeelname, huidigeQuizOpdracht,
					opdrachtAntwoord.getAantalPogingen(),
					opdrachtAntwoord.getAntwoordTijd(),
					opdrachtAntwoord.getLaatsteAntwoord());
			huidigeQuizDeelname = null;
			huidigeQuizOpdracht = null;
		}
	}
	
	private ArrayList<QuizOpdracht> quizOpdrachten() {
		ArrayList<QuizOpdracht> quizOpdrachten = new ArrayList<QuizOpdracht>();
		for (Opdracht opdracht : opdrachtCatalogus) {
			quizOpdrachten.addAll(opdracht.getQuizOpdrachten());
		}
		return quizOpdrachten;
	}
	
	private ArrayList<QuizDeelname> quizDeelnames() {
		ArrayList<QuizDeelname> quizDeelnames = new ArrayList<QuizDeelname>();
		for (Quiz quiz : quizCatalogus) {
			quizDeelnames.addAll(quiz.getQuizDeelnames());
		}
		return quizDeelnames;
	}
	
	private ArrayList<OpdrachtAntwoord> opdrachtAntwoorden() {
		ArrayList<OpdrachtAntwoord> opdrachtAntwoorden = new ArrayList<OpdrachtAntwoord>();
		ArrayList<QuizDeelname> quizDeelnames = quizDeelnames();
		for (QuizDeelname quizDeelname : quizDeelnames) {
			opdrachtAntwoorden.addAll(quizDeelname.getOpdrachtAntwoorden());
		}
		return opdrachtAntwoorden;
	}

}
