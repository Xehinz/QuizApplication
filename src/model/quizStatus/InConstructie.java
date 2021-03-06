package model.quizStatus;

/**
 * 
 * Modelleert de 'In constructie' status van een Quiz. Implementeert de QuizStatus
 * interface
 *
 */
public class InConstructie implements QuizStatus {

	public InConstructie() {
		
	}
	
	@Override
	public boolean isAanpasbaar() {
		return true;
	}

	@Override
	public boolean isVerwijderbaar() {
		return true;
	}

	@Override
	public boolean isDeelnameMogelijk() {
		return false;
	}
	
	@Override
	public String toString() {
		return "In constructie";
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof InConstructie;
	}

}
