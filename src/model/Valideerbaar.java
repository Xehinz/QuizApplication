package model;

public interface Valideerbaar {
	/**
	 * Controleert of het antwoord op de vraag van de opdracht geldig is.
	 * 
	 * @param antwoord de String met het te testen antwoord
	 * @return true als het antwoord geldig is
	 */
	public boolean isValide(String antwoord);

	/**
	 * Omschrijft de voorwaarden waaraan een antwoord op deze opdracht moet voldoen.
	 * 
	 * @return de String met de voorwaarden waaraan een antwoord op deze opdracht moet voldoen.
	 */
	public String getValideerTekst();
}
