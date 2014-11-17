package persistency;

import java.io.IOException;
import java.util.ArrayList;

import model.Leerling;
import model.Opdracht;
import model.OpdrachtAntwoord;
import model.Quiz;
import model.QuizDeelname;
import model.QuizOpdracht;

interface DBStrategy {
	public ArrayList<Opdracht> leesOpdrachten() throws IOException;

	public ArrayList<Leerling> leesLeerlingen() throws IOException;

	public ArrayList<Quiz> leesQuizzen() throws IOException;

	public ArrayList<PseudoQuizDeelname> leesQuizDeelnames() throws IOException;

	public ArrayList<PseudoQuizOpdracht> leesQuizOpdrachten() throws IOException;

	public ArrayList<PseudoOpdrachtAntwoord> leesOpdrachtAntwoorden() throws IOException;

	public void schrijfOpdrachten(ArrayList<Opdracht> opdrachten) throws IOException;

	public void schrijfLeerlingen(ArrayList<Leerling> leerlingen) throws IOException;

	public void schrijfQuizzen(ArrayList<Quiz> quizzen) throws IOException;

	public void schrijfQuizDeelnames(ArrayList<QuizDeelname> quizDeelnames) throws IOException;

	public void schrijfQuizOpdrachten(ArrayList<QuizOpdracht> quizOpdrachten) throws IOException;

	public void schrijfOpdrachtAntwoorden(ArrayList<OpdrachtAntwoord> opdrachtAntwoorden) throws IOException;
}
