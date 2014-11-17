package persistency;

class PseudoQuizOpdracht {
	
	private int ID, maxScore, quizID, opdrachtID;	
	
	public PseudoQuizOpdracht(int ID, int quizID, int opdrachtID, int maxScore) {
		this.ID = ID;
		this.maxScore = maxScore;
		this.quizID = quizID;
		this.opdrachtID = opdrachtID;
	}

	public int getID() {
		return ID;
	}

	public int getMaxScore() {
		return maxScore;
	}

	public int getQuizID() {
		return quizID;
	}

	public int getOpdrachtID() {
		return opdrachtID;
	}
}
