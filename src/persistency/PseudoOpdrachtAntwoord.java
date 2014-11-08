package persistency;

public class PseudoOpdrachtAntwoord {

	private int quizDeelnameID, quizOpdrachtID;
	private int aantalPogingen, antwoordTijd;
	private String laatsteAntwoord;
	private double behaaldeScore;

	public PseudoOpdrachtAntwoord(int quizDeelnameID, int quizOpdrachtID, int aantalPogingen, int antwoordTijd,
			String laatsteAntwoord, double behaaldeScore) {
		this.quizDeelnameID = quizDeelnameID;
		this.quizOpdrachtID = quizOpdrachtID;
		this.aantalPogingen = aantalPogingen;
		this.antwoordTijd = antwoordTijd;
		this.laatsteAntwoord = laatsteAntwoord;
		this.behaaldeScore = behaaldeScore;
	}

	public int getQuizDeelnameID() {
		return quizDeelnameID;
	}

	public int getQuizOpdrachtID() {
		return quizOpdrachtID;
	}

	public int getAantalPogingen() {
		return aantalPogingen;
	}

	public int getAntwoordTijd() {
		return antwoordTijd;
	}

	public String getLaatsteAntwoord() {
		return laatsteAntwoord;
	}

	public double getBehaaldeScore() {
		return behaaldeScore;
	}
}
