package model;

public class KlassiekeOpdracht extends Opdracht {

	private String juisteAntwoord;

	public KlassiekeOpdracht(OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(opdrachtCategorie, auteur);
	}

	public KlassiekeOpdracht(String vraag, String juisteAntwoord, OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(vraag, opdrachtCategorie, auteur);
		this.juisteAntwoord = juisteAntwoord;
	}

	public KlassiekeOpdracht(String vraag, int maxAantalPogingen, String juisteAntwoord, OpdrachtCategorie opdrachtCategorie,
			Leraar auteur) {
		super(vraag, maxAantalPogingen, 0, opdrachtCategorie, auteur);
		this.juisteAntwoord = juisteAntwoord;
	}

	public KlassiekeOpdracht(String vraag, String juisteAntwoord, int maxAntwoordTijd, OpdrachtCategorie opdrachtCategorie,
			Leraar auteur) {
		super(vraag, maxAntwoordTijd, opdrachtCategorie, auteur);
		this.juisteAntwoord = juisteAntwoord;
	}

	public KlassiekeOpdracht(String vraag, String juisteAntwoord, int maxAantalPogingen, int maxAntwoordTijd,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(vraag, maxAantalPogingen, maxAntwoordTijd, opdrachtCategorie, auteur);
		this.juisteAntwoord = juisteAntwoord;
	}

	public String getJuisteAntwoord() {
		return juisteAntwoord;
	}

	public void setJuisteAntwoord(String juisteAntwoord) throws UnsupportedOperationException {
		if (!isAanpasbaar()) {
			throw new UnsupportedOperationException(
					"Deze Opdracht is niet meer aanpasbaar. Er hebben reeds leerlingen deze opdracht opgelost in een quiz");
		}
		this.juisteAntwoord = juisteAntwoord;
	}

	@Override
	public boolean isJuisteAntwoord(String antwoord) {
		return antwoord.equals(juisteAntwoord);
	}

	@Override
	public String toString() {
		return super.toString() + juisteAntwoord;
	}
}
