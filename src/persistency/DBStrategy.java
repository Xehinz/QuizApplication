package persistency;

import java.util.ArrayList;

import model.Leerling;
import model.Opdracht;
import model.OpdrachtAntwoord;
import model.Quiz;
import model.QuizDeelname;
import model.QuizOpdracht;

public interface DBStrategy {
	public ArrayList<Opdracht> leesOpdrachten();

	public ArrayList<Leerling> leesLeerlingen();

	public ArrayList<Quiz> leesQuizzen();

	public ArrayList<PseudoQuizDeelname> leesQuizDeelnames();

	public ArrayList<PseudoQuizOpdracht> leesQuizOpdrachten();

	public ArrayList<PseudoOpdrachtAntwoord> leesOpdrachtAntwoorden();

	public void schrijfOpdrachten(ArrayList<Opdracht> opdrachten);

	public void schrijfLeerlingen(ArrayList<Leerling> leerlingen);

	public void schrijfQuizzen(ArrayList<Quiz> quizzen);

	public void schrijfQuizDeelnames(ArrayList<QuizDeelname> quizDeelnames);

	public void schrijfQuizOpdrachten(ArrayList<QuizOpdracht> quizOpdrachten);

	public void schrijfOpdrachtAntwoorden(ArrayList<OpdrachtAntwoord> opdrachtAntwoorden);
}
