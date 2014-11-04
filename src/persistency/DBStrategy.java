package persistency;

import java.util.ArrayList;

import model.LeerlingContainer;
import model.OpdrachtAntwoord;
import model.OpdrachtCatalogus;
import model.QuizCatalogus;
import model.QuizDeelname;
import model.QuizOpdracht;

public interface DBStrategy {
	public OpdrachtCatalogus leesOpdrachten();

	public LeerlingContainer leesLeerlingen();

	public QuizCatalogus leesQuizzen();

	public ArrayList<QuizDeelname> leesQuizDeelnames();

	public ArrayList<QuizOpdracht> leesQuizOpdrachten();

	public ArrayList<OpdrachtAntwoord> leesOpdrachtAntwoorden();
}
