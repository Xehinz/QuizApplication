package model;

/**
 * Klasse die een QuizDeelname aan een QuizOpdracht verbindt. Eenmaal er een instantie van OpdrachtAntwoord is
 * aangemaakt, is het antwoord van de Leerling op de Opdracht binnen de QuizDeelname definitief. Het OpdrachtAntwoord
 * object houdt gegevens bij over het antwoord op één vraag binnen één quizdeelname. Volgende zaken worden bijgehouden:
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
 * @version 29/10/2014
 *
 */
public class OpdrachtAntwoord implements Comparable<OpdrachtAntwoord>, Cloneable{
	private final QuizDeelname quizDeelname;
	private final QuizOpdracht quizOpdracht;
	private final String laatsteAntwoord;
	private final int aantalPogingen;
	private final int antwoordTijd;
	private final double behaaldeScore;

	/**
	 * Haalt de QuizDeelname gelinkt aan dit object op
	 * 
	 * @return de QuizDeelname gelinkt aan dit object
	 */
	public QuizDeelname getQuizDeelname() {
		return quizDeelname;
	}

	/**
	 * Haalt de QuizOpdracht gelinkt aan dit object op
	 * 
	 * @return de QuizOpdracht gelinkt aan dit object
	 */
	public QuizOpdracht getQuizOpdracht() {
		return quizOpdracht;
	}

	/**
	 * Haalt het definitieve (laatste) antwoord op
	 * 
	 * @return de String met het definitieve antwoord
	 */
	public String getLaatsteAntwoord() {
		return laatsteAntwoord;
	}

	/**
	 * Haalt het benutte aantal pogingen op
	 * 
	 * @return het benutte aantal pogingen
	 */
	public int getAantalPogingen() {
		return aantalPogingen;
	}

	/**
	 * Haalt de gebruikte antwoordtijd in seconden op
	 * 
	 * @return de gebruikte antwoordtijd in seconden
	 */
	public int getAntwoordTijd() {
		return antwoordTijd;
	}

	/**
	 * Haalt de behaalde score op (decimaal getal)
	 * 
	 * @return de double die de behaalde score voorstelt
	 */
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

	/**
	 * Uitwerking van het scoreberekeningsalgoritme op pagina 5 van de opgave
	 */
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

	/**
	 * Legt de relatie tussen een QuizDeelname en een QuizOpdracht. Eenmaal dat deze method gecalled is, is het antwoord
	 * van de Leerling op de Opdracht binnen de QuizDeelname definitief
	 * 
	 * @param quizDeelname
	 *            de QuizDeelname die de Leerling met de Quiz die hij aan het maken is verbindt
	 * @param quizOpdracht
	 *            de QuizOpdracht waarop de Leerling antwoord
	 * @param aantalPogingen
	 *            het aantalPogingen dat de Leerling nodig heeft gehad
	 * @param antwoordTijd
	 *            de tijd in seconden die de Leerling nodig heeft gehad
	 * @param laatsteAntwoord
	 *            de String met het definitieve antwoord van de Leerling
	 */
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
		return result % Integer.MAX_VALUE;
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

	@Override
	public String toString() {
		String result = "";
		result += String
				.format("OpdrachtAntwoord dat\n\t%s\nen\n\t%s\nmet elkaar verbindt", this.quizDeelname, this.quizOpdracht);
		result += "\n\nDefinitieve antwoord: " + laatsteAntwoord;
		result += "\nAantal pogingen: " + aantalPogingen;
		result += "\nGebruikte tijd (s): " + antwoordTijd;
		result += "\nBehaalde score: " + behaaldeScore;
		return result;
	}
	
	/**
	 * Vergelijkt dit OpdrachtAntwoord met een ander OpdrachtAntwoord, eerst op QuizDeelname, dan op QuizOpdracht 
	 * 
	 * @param opdrachtAntwoord het OpdrachtAntwoord om mee te vergelijken
	 * @return
	 */
	public int compareTo(OpdrachtAntwoord opdrachtAntwoord) {
		if (this.quizDeelname.compareTo(opdrachtAntwoord.quizDeelname) == 0) {
			return this.quizOpdracht.compareTo(opdrachtAntwoord.quizOpdracht);
		}
		else {
			return this.quizDeelname.compareTo(opdrachtAntwoord.quizDeelname);
		}
	}
	
	public OpdrachtAntwoord clone() {
		OpdrachtAntwoord clone = null;
		try {
			clone = (OpdrachtAntwoord)super.clone();			
		} catch (CloneNotSupportedException ex) {
			ex.printStackTrace();
		}
		return clone;
	}

}
