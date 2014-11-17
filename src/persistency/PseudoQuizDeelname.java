package persistency;

import util.datumWrapper.Datum;

public class PseudoQuizDeelname {
	private int quizDeelnameID, quizID, leerlingID;
	private Datum deelnameDatum;

	public PseudoQuizDeelname(int quizDeelnameID, int quizID, int leerlingID, Datum deelnameDatum) {
		this.quizDeelnameID = quizDeelnameID;
		this.quizID = quizID;
		this.leerlingID = leerlingID;
		this.deelnameDatum = deelnameDatum;
	}

	public int getQuizDeelnameID() {
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
