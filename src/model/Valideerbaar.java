package model;

/**
 * Opdrachten kunnen deze interface implementeren om aan te geven dat het
 * antwoord naar formaat gevalideerd kan worden voordat het op correctheid
 * ge&euml;valueerd wordt. Om Valideerbaar te implementeren moet de Opdracht
 * twee methods implementeren:
 * 
 * <ul>
 * <li>boolean isValide(String antwoord)</li>
 * <li>String getValideerTekst()</li>
 * </ul>
 */
public interface Valideerbaar {
	/**
	 * Controleert of het antwoord op de vraag van de opdracht geldig is.
	 * 
	 * @param antwoord
	 *            de String met het te testen antwoord
	 * @return true als het antwoord geldig is
	 */
	public boolean isValide(String antwoord);

	/**
	 * Omschrijft de voorwaarden waaraan een antwoord op deze opdracht moet
	 * voldoen.
	 * 
	 * @return de String met de voorwaarden waaraan een antwoord op deze
	 *         opdracht moet voldoen.
	 */
	public String getValideerTekst();
}
