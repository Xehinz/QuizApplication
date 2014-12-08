package persistency;

class PseudoOpdrachtAntwoord {

	private String quizDeelnameID, quizOpdrachtID;
	private int aantalPogingen, antwoordTijd;
	private String laatsteAntwoord;	

	public PseudoOpdrachtAntwoord(String quizDeelnameID, String quizOpdrachtID, int aantalPogingen, int antwoordTijd,
			String laatsteAntwoord) {
		this.quizDeelnameID = quizDeelnameID;
		this.quizOpdrachtID = quizOpdrachtID;
		this.aantalPogingen = aantalPogingen;
		this.antwoordTijd = antwoordTijd;
		this.laatsteAntwoord = laatsteAntwoord;	

	}

	public String getQuizDeelnameID() {
		return quizDeelnameID;
	}

	public String getQuizOpdrachtID() {
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
}
