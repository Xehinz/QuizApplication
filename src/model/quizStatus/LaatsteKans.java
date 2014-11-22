package model.quizStatus;

public class LaatsteKans implements QuizStatus {
	
	public LaatsteKans() {
		
	}
	
	@Override
	public boolean isAanpasbaar() {
		return false;
	}

	@Override
	public boolean isVerwijderbaar() {
		return false;
	}

	@Override
	public boolean isDeelnameMogelijk() {
		return true;
	}
	
	@Override
	public String toString() {
		return "Laatste kans";
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof LaatsteKans;
	}

}
