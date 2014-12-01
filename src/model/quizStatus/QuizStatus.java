package model.quizStatus;

/**
 * 
 * Interface voor het modelleren van de status van een Quiz. De verschillende
 * statussen zijn 'In constructie', 'Afgewerkt', 'Opengesteld', 'Laatste kans'
 * en 'Afgesloten'.
 * 
 */
public interface QuizStatus {

	/**
	 * Geeft aan of de Quiz al dan niet aanpasbaar is
	 * 
	 * @return true als de Quiz aanpasbaar is
	 */
	public abstract boolean isAanpasbaar();

	/**
	 * Geeft aan of de Quiz al dan niet verwijderbaar is
	 * 
	 * @return true als de Quiz verwijderbaar is
	 */
	public abstract boolean isVerwijderbaar();

	/**
	 * Geeft aan of aan de Quiz al dan niet kan worden deelgenomen
	 * 
	 * @return true als aan de Quiz deelgenomen kan worden
	 */
	public abstract boolean isDeelnameMogelijk();

	/**
	 * Maakt een QuizStatus object op basis van een String met de omschrijving.
	 * De omschrijvingen zijn 'In constructie', 'Afgewerkt', 'Opengesteld',
	 * 'Laatste kans' en 'Afgesloten'
	 * 
	 * @param status
	 *            de String met de omschrijving van de status
	 * @return de gewenste QuizStatus
	 */
	public static QuizStatus getInstance(String status) {
		switch (status) {
		case "Afgesloten":
			return new Afgesloten();
		case "Afgewerkt":
			return new Afgewerkt();
		case "In constructie":
			return new InConstructie();
		case "Laatste kans":
			return new LaatsteKans();
		case "Opengesteld":
			return new Opengesteld();
		}
		return null;
	}

}
