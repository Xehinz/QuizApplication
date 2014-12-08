package persistency;

import java.time.LocalTime;

import util.datumWrapper.Datum;

class PseudoQuizDeelname {
	private int quizID, leerlingID;
	private Datum deelnameDatum;
	private String quizDeelnameID;

	public PseudoQuizDeelname(String quizDeelnameID, int quizID, int leerlingID, Datum deelnameDatum) {
		this.quizDeelnameID = quizDeelnameID;
		this.quizID = quizID;
		this.leerlingID = leerlingID;
		this.deelnameDatum = deelnameDatum;
	}

	public String getQuizDeelnameID() {
		return quizDeelnameID;
	}

	public int getLeerlingID() {
		return leerlingID;
	}

	public int getQuizID() {
		return quizID;
	}
	
	public Datum getDeelnameDatum() {
		return deelnameDatum;
	}
}
