package model;

public class KlassiekeOpdracht extends Opdracht {

	private String juisteAntwoord;

	public KlassiekeOpdracht(OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(opdrachtCategorie, auteur);
		this.juisteAntwoord = "";
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

	public KlassiekeOpdracht(int ID, String vraag, String juisteAntwoord, int maxAantalPogingen, int maxAntwoordTijd,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(ID, vraag, maxAantalPogingen, maxAntwoordTijd, opdrachtCategorie, auteur);
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
		this.juisteAntwoord = juisteAntwoord.trim();
	}

	@Override
	public boolean isJuisteAntwoord(String antwoord) {
		return antwoord.trim().equalsIgnoreCase(juisteAntwoord);
	}

	@Override
	public String toString() {
		return "Klassieke " + super.toString() + juisteAntwoord;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		KlassiekeOpdracht other = (KlassiekeOpdracht) obj;
		if (this.juisteAntwoord != other.juisteAntwoord) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		long hash = super.hashCode();
		hash = 41 * hash + juisteAntwoord.hashCode();
		hash %= Integer.MAX_VALUE;
		return (int) hash;
	}

	@Override
	public KlassiekeOpdracht clone() {
		return (KlassiekeOpdracht) super.clone();
	}
}
