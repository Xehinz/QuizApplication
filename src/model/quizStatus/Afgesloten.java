package model.quizStatus;

/**
 * 
 * Modelleert de 'Afgesloten' status van een Quiz. Implementeert de QuizStatus
 * interface
 *
 */
public class Afgesloten implements QuizStatus {

	public Afgesloten() {

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
		return false;
	}

	@Override
	public String toString() {
		return "Afgesloten";
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Afgesloten;
	}

}
