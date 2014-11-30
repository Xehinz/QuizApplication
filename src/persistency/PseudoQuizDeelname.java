package persistency;

import java.time.LocalTime;

import util.datumWrapper.Datum;

class PseudoQuizDeelname {
	private int quizDeelnameID, quizID, leerlingID;
	private Datum deelnameDatum;
	private LocalTime tijdstipDeelname;

	public PseudoQuizDeelname(int quizDeelnameID, int quizID, int leerlingID, Datum deelnameDatum, LocalTime tijdstipDeelname) {
		this.quizDeelnameID = quizDeelnameID;
		this.quizID = quizID;
		this.leerlingID = leerlingID;
		this.deelnameDatum = deelnameDatum;
		this.tijdstipDeelname = tijdstipDeelname;
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
	
	public LocalTime getTijdstipDeelname() {
		return tijdstipDeelname;
	}
}
