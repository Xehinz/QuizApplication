package model;

public class Meerkeuze extends Opdracht implements Valideerbaar {

	public Meerkeuze(OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(opdrachtCategorie, auteur);
	}

	public Meerkeuze(String vraag, String juisteAntwoord, OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(vraag, opdrachtCategorie, auteur);
		// Juiste antwoord nog toewijzen
	}

	public Meerkeuze(String vraag, int maxAantalPogingen, String juisteAntwoord, OpdrachtCategorie opdrachtCategorie,
			Leraar auteur) {
		super(vraag, maxAantalPogingen, 0, opdrachtCategorie, auteur);
		// Juiste antwoord nog toewijzen
	}

	public Meerkeuze(String vraag, String juisteAntwoord, int maxAntwoordTijd, OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(vraag, maxAntwoordTijd, opdrachtCategorie, auteur);
		// Juiste antwoord nog toewijzen
	}

	public Meerkeuze(String vraag, String juisteAntwoord, int maxAantalPogingen, int maxAntwoordTijd,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(vraag, maxAantalPogingen, maxAntwoordTijd, opdrachtCategorie, auteur);
		// Juiste antwoord nog toewijzen
	}

	@Override
	public boolean isValide(String antwoord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getValideerTekst() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isJuisteAntwoord(String antwoord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toString() {
		return super.toString(); // Nog aanvullen
	}
}
