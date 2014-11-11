package persistency;

public class PseudoQuizDeelname {
	private int quizDeelnameID, quizID, leerlingID;

	public PseudoQuizDeelname(int quizDeelnameID, int quizID, int leerlingID) {
		this.quizDeelnameID = quizDeelnameID;
		this.quizID = quizID;
		this.leerlingID = leerlingID;
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

}
