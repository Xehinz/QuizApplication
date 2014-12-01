package model.quizStatus;

/**
 * 
 * Modelleert de 'Opengesteld' status van een Quiz. Implementeert de QuizStatus
 * interface
 *
 */
public class Opengesteld implements QuizStatus {

	public Opengesteld() {
		
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
		return "Opengesteld";
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Opengesteld;
	}

}
