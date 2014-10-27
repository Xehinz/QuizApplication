package model;

/**
 * Tijdelijke enum. Wanneer we het State pattern implementeren kan deze weg (denk ik)
 *
 * @author Ben Vandenberk
 *
 */
public enum QuizStatus {
	IN_CONSTRUCTIE(false, "In constructie"), AFGEWERKT(false, "Afgewerkt"), OPENGESTELD(true, "Opengesteld"), LAATSTE_KANS(true,
			"Laatste kans"), AFGESLOTEN(false, "Afgesloten");

	private boolean deelnameMogelijk;
	private String statusString;

	private QuizStatus(boolean deelnameMogelijk, String statusString) {
		this.deelnameMogelijk = deelnameMogelijk;
		this.statusString = statusString;
	}

	public boolean isDeelnameMogelijk() {
		return this.deelnameMogelijk;
	}

	@Override
	public String toString() {
		return statusString;
	}
}
