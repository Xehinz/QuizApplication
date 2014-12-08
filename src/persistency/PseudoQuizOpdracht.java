package persistency;

class PseudoQuizOpdracht {
	
	private int maxScore, quizID, opdrachtID;	
	private String ID;
	
	public PseudoQuizOpdracht(String ID, int quizID, int opdrachtID, int maxScore) {
		this.ID = ID;
		this.maxScore = maxScore;
		this.quizID = quizID;
		this.opdrachtID = opdrachtID;
	}

	public String getID() {
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
