package model;

public class Opdracht {

	private String vraag;
	private String juisteAntwoord;
	private int maxAantalPogingen;
	private String[] antwoordHints;
	private int maxAntwoordTijd;
	
	public Opdracht() {
		
	}	
	
	public String getVraag() {
		return vraag;
	}

	public void setVraag(String vraag) {
		this.vraag = vraag;
	}

	public String getJuisteAntwoord() {
		return juisteAntwoord;
	}

	public void setJuisteAntwoord(String juisteAntwoord) {
		this.juisteAntwoord = juisteAntwoord;
	}

	public int getMaxAantalPogingen() {
		return maxAantalPogingen;
	}

	public void setMaxAantalPogingen(int maxAantalPogingen) {
		this.maxAantalPogingen = maxAantalPogingen;
	}

	public String[] getAntwoordHints() {
		return antwoordHints;
	}

	public void setAntwoordHints(String[] antwoordHints) {
		this.antwoordHints = antwoordHints;
	}

	public int getMaxAntwoordTijd() {
		return maxAntwoordTijd;
	}

	public void setMaxAntwoordTijd(int maxAntwoordTijd) {
		this.maxAntwoordTijd = maxAntwoordTijd;
	}

	public boolean isJuisteAntwoord(String antwoord) {
		return true;
	}
}
