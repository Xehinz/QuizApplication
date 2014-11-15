package persistency;

import model.OpdrachtAntwoord;

public class PseudoOpdrachtAntwoord {

	private int quizDeelnameID, quizOpdrachtID;
	private int aantalPogingen, antwoordTijd;
	private String laatsteAntwoord;	

	public PseudoOpdrachtAntwoord(int quizDeelnameID, int quizOpdrachtID, int aantalPogingen, int antwoordTijd,
			String laatsteAntwoord) {
		this.quizDeelnameID = quizDeelnameID;
		this.quizOpdrachtID = quizOpdrachtID;
		this.aantalPogingen = aantalPogingen;
		this.antwoordTijd = antwoordTijd;
		this.laatsteAntwoord = laatsteAntwoord;		
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
}
