package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class OpdrachtAntwoordTest {

	private Quiz quiz;
	private Opdracht opdracht, opdrachtOpTijd;
	private Leerling leerling;
	private QuizDeelname quizDeelname;
	private QuizOpdracht quizOpdracht, quizOpdrachtOpTijd;
	private OpdrachtAntwoord opdrachtAntwoord, juistAntwoordEenPoging, juisteAntwoordTweePogingen, juisteAntwoordVeelPogingen,
	foutAntwoord, geenAntwoord, antwoordBuitenTijd;

	@Before
	public void setUp() {
		quiz = new Quiz(Leraar.CHARLOTTE_NEVEN, "De Hoofdsteden van Europa");
		quiz.setQuizStatus(QuizStatus.OPENGESTELD);
		opdracht = new Opdracht("Wat is de hoofdstad van Frankrijk?", 0, "Parijs", OpdrachtCategorie.AARDRIJKSKUNDE,
				Leraar.MARIA_AERTS);
		opdrachtOpTijd = new Opdracht("Wat is de hoofdstad van België?", "Brussel", 10, 20, OpdrachtCategorie.AARDRIJKSKUNDE,
				Leraar.JOS_VERBEEK);
		leerling = new Leerling("Karel", "Boghen", 3);

		QuizOpdracht.attachOpdrachtToQuiz(quiz, opdracht, 10);
		quizOpdracht = quiz.getQuizOpdracht(1);
		QuizOpdracht.attachOpdrachtToQuiz(quiz, opdrachtOpTijd, 10);
		quizOpdrachtOpTijd = quiz.getQuizOpdracht(2);

		QuizDeelname.koppelQuizAanLeerling(quiz, leerling);
		quizDeelname = quiz.getQuizDeelname(1);

		juistAntwoordEenPoging = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname, quizOpdracht, 1, 10, "Parijs");
		juisteAntwoordTweePogingen = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname, quizOpdracht, 2, 10,
				"Parijs");
		juisteAntwoordVeelPogingen = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname, quizOpdracht, 8, 10,
				"Parijs");

		foutAntwoord = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname, quizOpdracht, 1, 10, "Londen");
		geenAntwoord = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname, quizOpdracht, 1, 10, "");

		antwoordBuitenTijd = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname, quizOpdrachtOpTijd, 1, 30,
				"Brussel");
	}

	@Test
	public void testKoppelQuizDeelnameAanQuizOpdracht_CorrecteKoppeling() {
		opdrachtAntwoord = OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname, quizOpdracht, 1, 10, "Parijs");
		assertTrue("QuizDeelname bevat het OpdrachtAntwoord", quizDeelname.getOpdrachtAntwoorden().contains(opdrachtAntwoord));
		assertTrue("QuizOpdracht bevat het OpdrachtAntwoord", quizOpdracht.getOpdrachtAntwoorden().contains(opdrachtAntwoord));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testKoppelQuizDeelnameAanQuizOpdracht_AantalPogingen0_ThrowsIllegalArgumentException() {
		OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname, quizOpdracht, 0, 10, "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testKoppelQuizDeelnameAanQuizOpdracht_AantalPogingenNegatief_ThrowsIllegalArgumentException() {
		OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname, quizOpdracht, -10, 10, "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testKoppelQuizDeelnameAanQuizOpdracht_AntwoordTijdNegatief_ThrowsIllegalArgumentException() {
		OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname, quizOpdracht, 1, -10, "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testKoppelQuizDeelnameAanQuizOpdracht_QuizDeelnameNull_ThrowsIllegalArgumentException() {
		OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(null, quizOpdracht, 1, 10, "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testKoppelQuizDeelnameAanQuizOpdracht_QuizOpdrachtNull_ThrowsIllegalArgumentException() {
		OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname, null, 1, 10, "");
	}

	@Test
	public void testGetBehaaldeScore_JuistAntwoordEerstePoging_MaximumScore() {
		assertEquals("Juist antwoord in 1 poging geeft maximum score", quizOpdracht.getMaxScore(),
				juistAntwoordEenPoging.getBehaaldeScore(), 0.01);
	}

	@Test
	public void testGetBehaaldeScore_JuistAntwoordTweedePoging_HelftVanMaxScore() {
		assertEquals("Juist antwoord in 2 pogingen geeft helft van de punten", quizOpdracht.getMaxScore() / 2.0,
				juisteAntwoordTweePogingen.getBehaaldeScore(), 0.01);
	}

	@Test
	public void testGetBehaaldeScore_JuistAntwoordVeelPogingen_HelftVanMaxScore() {
		assertEquals("Juist antwoord in veel pogingen geeft helft van de punten", quizOpdracht.getMaxScore() / 2.0,
				juisteAntwoordVeelPogingen.getBehaaldeScore(), 0.01);
	}

	@Test
	public void testGetBehaaldeScore_FoutAntwoord_0Punten() {
		assertEquals("Fout antwoord levert geen punten op", 0, foutAntwoord.getBehaaldeScore(), 0.01);
	}

	@Test
	public void testGetBehaaldeScore_GeenAntwoord_0Punten() {
		assertEquals("Fout antwoord levert geen punten op", 0, geenAntwoord.getBehaaldeScore(), 0.01);
	}

	@Test
	public void testGetBehaaldeScore_BuitenTijd_0Punten() {
		assertEquals("Antwoord buiten de tijd levert geen punten op", 0, antwoordBuitenTijd.getBehaaldeScore(), 0.01);
	}
}
