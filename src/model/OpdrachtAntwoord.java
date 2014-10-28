package model;

/**
 * Klasse die een QuizDeelname aan een QuizOpdracht verbindt. Het OpdrachtAntwoord object houdt gegevens bij over het
 * antwoord op één vraag binnen één quizdeelname. Volgende zaken worden bijgehouden:
 * <ul>
 * <li>Het definitieve (laatste) antwoord (String)</li>
 * <li>Het aantal pogingen om tot dit antwoord te komen (int)</li>
 * <li>De gebruikte tijd om tot dit antwoord te komen in seconden (int)</li>
 * <li>De behaalde score</li>
 * </ul>
 *
 * Het algoritme om tot dit antwoord te komen is voorlopig hard-coded opgenomen in deze klasse. Wanneer we het Strategy
 * pattern gaan implementeren, is het de bedoeling om de scoreberekening met behulp van klasses flexibeler te maken.
 *
 * @author Tim Cool, Ben Vandenberk
 * @version 28/10/2014
 *
 */
public class OpdrachtAntwoord {
	private final QuizDeelname quizDeelname;
	private final QuizOpdracht quizOpdracht;
	private final String laatsteAntwoord;
	private final int aantalPogingen;
	private final int antwoordTijd;
	private final double behaaldeScore;

	public QuizDeelname getQuizDeelname() {
		return quizDeelname;
	}

	public QuizOpdracht getQuizOpdracht() {
		return quizOpdracht;
	}

	public String getLaatsteAntwoord() {
		return laatsteAntwoord;
	}

	public int getAantalPogingen() {
		return aantalPogingen;
	}

	public int getAntwoordTijd() {
		return antwoordTijd;
	}

	public double getBehaaldeScore() {
		return behaaldeScore;
	}

	private OpdrachtAntwoord(QuizDeelname quizDeelname, QuizOpdracht quizOpdracht, int aantalPogingen, int antwoordTijd,
			String laatsteAntwoord) {
		this.quizDeelname = quizDeelname;
		this.quizOpdracht = quizOpdracht;
		this.aantalPogingen = aantalPogingen;
		this.antwoordTijd = antwoordTijd;
		this.laatsteAntwoord = laatsteAntwoord;
		behaaldeScore = berekenScore();
	}

	private double berekenScore() {
		if (!quizOpdracht.getOpdracht().isJuisteAntwoord(laatsteAntwoord)) {
			return 0.0;
		} else {
			if (quizOpdracht.getOpdracht().heeftTijdsbeperking()
					&& antwoordTijd > quizOpdracht.getOpdracht().getMaxAntwoordTijd()) {
				return 0.0;
			} else {
				if (aantalPogingen == 1) {
					return quizOpdracht.getMaxScore();
				} else {
					return quizOpdracht.getMaxScore() / 2.0;
				}
			}
		}
	}

	public static void koppelQuizDeelnameAanQuizOpdracht(QuizDeelname quizDeelname, QuizOpdracht quizOpdracht,
			int aantalPogingen, int antwoordTijd, String laatsteAntwoord) {
		OpdrachtAntwoord opdrachtAntwoord = new OpdrachtAntwoord(quizDeelname, quizOpdracht, aantalPogingen, antwoordTijd,
				laatsteAntwoord);
		quizDeelname.addOpdrachtAntwoord(opdrachtAntwoord);
		quizOpdracht.addOpdrachtAntwoord(opdrachtAntwoord);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + aantalPogingen;
		result = prime * result + antwoordTijd;
		result = prime * result + ((laatsteAntwoord == null) ? 0 : laatsteAntwoord.hashCode());
		result = prime * result + ((quizDeelname == null) ? 0 : quizDeelname.hashCode());
		result = prime * result + ((quizOpdracht == null) ? 0 : quizOpdracht.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		OpdrachtAntwoord other = (OpdrachtAntwoord) obj;
		if (aantalPogingen != other.aantalPogingen) {
			return false;
		}
		if (antwoordTijd != other.antwoordTijd) {
			return false;
		}
		if (laatsteAntwoord == null) {
			if (other.laatsteAntwoord != null) {
				return false;
			}
		} else if (!laatsteAntwoord.equals(other.laatsteAntwoord)) {
			return false;
		}
		if (quizDeelname == null) {
			if (other.quizDeelname != null) {
				return false;
			}
		} else if (!quizDeelname.equals(other.quizDeelname)) {
			return false;
		}
		if (quizOpdracht == null) {
			if (other.quizOpdracht != null) {
				return false;
			}
		} else if (!quizOpdracht.equals(other.quizOpdracht)) {
			return false;
		}
		return true;
	}

}
