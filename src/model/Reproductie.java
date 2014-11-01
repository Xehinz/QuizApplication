package model;

public class Reproductie extends Opdracht {

	public Reproductie(OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(opdrachtCategorie, auteur);
	}

	public Reproductie(String vraag, String juisteAntwoord, OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(vraag, opdrachtCategorie, auteur);
		// Juiste antwoord nog toewijzen
	}

	public Reproductie(String vraag, int maxAantalPogingen, String juisteAntwoord, OpdrachtCategorie opdrachtCategorie,
			Leraar auteur) {
		super(vraag, maxAantalPogingen, 0, opdrachtCategorie, auteur);
		// Juiste antwoord nog toewijzen
	}

	public Reproductie(String vraag, String juisteAntwoord, int maxAntwoordTijd, OpdrachtCategorie opdrachtCategorie,
			Leraar auteur) {
		super(vraag, maxAntwoordTijd, opdrachtCategorie, auteur);
		// Juiste antwoord nog toewijzen
	}

	public Reproductie(String vraag, String juisteAntwoord, int maxAantalPogingen, int maxAntwoordTijd,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(vraag, maxAantalPogingen, maxAntwoordTijd, opdrachtCategorie, auteur);
		// Juiste antwoord nog toewijzen
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
