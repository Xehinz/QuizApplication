package model.quizStatus;

/**
 * 
 * Modelleert de 'Afgewerkt' status van een Quiz. Implementeert de QuizStatus
 * interface
 *
 */
public class Afgewerkt implements QuizStatus {

	public Afgewerkt() {
		
	}
	
	@Override
	public boolean isAanpasbaar() {
		return false;
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
		return "Afgewerkt";
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Afgewerkt;
	}

}
