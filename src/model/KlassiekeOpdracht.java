package model;

import util.datumWrapper.Datum;

/**
 * De KlassiekeOpdracht klasse modelleert een simpele vraag-antwoord opdracht.
 * Ze erft over van de abstracte klase Opdracht. Bovenop de fields van Opdracht,
 * specifieert KlassiekeOpdracht een juiste antwoord (String).
 * 
 * @author Ben Vandenberk
 *
 */
public class KlassiekeOpdracht extends Opdracht {

	private String juisteAntwoord;

	/**
	 * Maakt een KlassiekeOpdracht object aan met een opdrachtcategorie en een
	 * auteur.<br/>
	 * <br/>
	 *
	 * De antwoordtijd is ongelimiteerd. Het maximum aantal antwoordpogingen is
	 * 1 (Default waarden)
	 *
	 * @param opdrachtCategorie
	 *            de OpdrachtCategorie
	 * @param auteur
	 *            de Leraar die de Opdracht gecre&euml;erd heeft
	 */
	public KlassiekeOpdracht(OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(opdrachtCategorie, auteur);
		this.juisteAntwoord = "";
	}

	/**
	 * Maakt een KlassiekeOpdracht object aan met een vraag, een antwoord, een
	 * opdrachtcategorie en een auteur.<br/>
	 * <br/>
	 *
	 * De antwoordtijd is ongelimiteerd. Het maximum aantal antwoordpogingen is
	 * 1 (Default waarden)
	 *
	 * @param vraag
	 *            de String die de vraag bevat
	 * @param juisteAntwoord
	 *            de String die het juiste antwoord bevat
	 * @param opdrachtCategorie
	 *            de OpdrachtCategorie
	 * @param auteur
	 *            de Leraar die de Opdracht gecre&euml;erd heeft
	 */
	public KlassiekeOpdracht(String vraag, String juisteAntwoord,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(vraag, opdrachtCategorie, auteur);
		this.juisteAntwoord = juisteAntwoord;
	}

	/**
	 * Maakt een KlassiekeOpdracht object aan met een vraag, een maximum aantal
	 * pogingen, een antwoord, een opdrachtcategorie en een auteur.<br/>
	 * <br/>
	 *
	 * De antwoordtijd is ongelimiteerd (default)
	 *
	 * @param vraag
	 *            de String die de vraag bevat
	 * @param maxAantalPogingen
	 *            het maximaal aantal toegelaten antwoordpogingen
	 * @param juisteAntwoord
	 *            de String die het juiste antwoord bevat
	 * @param opdrachtCategorie
	 *            de OpdrachtCategorie
	 * @param auteur
	 *            de Leraar die de Opdracht gecre&euml;erd heeft
	 */
	public KlassiekeOpdracht(String vraag, int maxAantalPogingen,
			String juisteAntwoord, OpdrachtCategorie opdrachtCategorie,
			Leraar auteur) {
		super(vraag, maxAantalPogingen, 0, opdrachtCategorie, auteur);
		this.juisteAntwoord = juisteAntwoord;
	}

	/**
	 * Maakt een KlassiekeOpdracht object aan met een vraag, een antwoord, een
	 * maximale antwoordtijd, een opdrachtcategorie en een auteur.<br/>
	 * <br/>
	 *
	 * Het maximaal aantal antwoordpogingen is 1 (default)
	 *
	 * @param vraag
	 *            de String die de vraag bevat
	 * @param juisteAntwoord
	 *            de String die het juiste antwoord bevat
	 * @param maxAntwoordTijd
	 *            de maximaal toegelaten antwoordtijd
	 * @param opdrachtCategorie
	 *            de OpdrachtCategorie
	 * @param auteur
	 *            de Leraar die de Opdracht gecre&euml;erd heeft
	 */
	public KlassiekeOpdracht(String vraag, String juisteAntwoord,
			int maxAntwoordTijd, OpdrachtCategorie opdrachtCategorie,
			Leraar auteur) {
		super(vraag, maxAntwoordTijd, opdrachtCategorie, auteur);
		this.juisteAntwoord = juisteAntwoord;
	}

	/**
	 * Maakt een KlassiekeOpdracht object aan met een vraag, een antwoord, een maximum aantal
	 * pogingen, een maximale antwoordtijd, een opdrachtcategorie en een auteur.
	 *
	 * @param vraag
	 *            de String die de vraag bevat
	 * @param juisteAntwoord
	 *            de String die het juiste antwoord bevat
	 * @param maxAantalPogingen
	 *            het maximaal aantal toegelaten antwoordpogingen
	 * @param maxAntwoordTijd
	 *            de maximaal toegelaten antwoordtijd
	 * @param opdrachtCategorie
	 *            de OpdrachtCategorie
	 * @param auteur
	 *            de Leraar die de Opdracht gecre&euml;erd heeft
	 */
	public KlassiekeOpdracht(String vraag, String juisteAntwoord,
			int maxAantalPogingen, int maxAntwoordTijd,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(vraag, maxAantalPogingen, maxAntwoordTijd, opdrachtCategorie,
				auteur);
		this.juisteAntwoord = juisteAntwoord;
	}

	/**
	 * Maakt een KlassiekeOpdracht object aan dat al een ID en een datum van
	 * aanmaak toegewezen had. Gebruik deze constructor alleen om een Opdracht
	 * te maken uit opslag (tekst / DB)
	 *
	 * @param ID
	 *            de ID van de Opdracht
	 * @param aanmaakDatum
	 *            de Datum waarop de Opdracht is aangemaakt
	 * @param vraag
	 *            de String die de vraag bevat
	 * @param juisteAntwoord
	 *            de String die het juiste antwoord bevat
	 * @param maxAantalPogingen
	 *            het maximaal aantal toegelaten antwoordpogingen
	 * @param maxAntwoordTijd
	 *            de maximaal toegelaten antwoordtijd
	 * @param opdrachtCategorie
	 *            de OpdrachtCategorie
	 * @param auteur
	 *            de Leraar die de Opdracht gecre&euml;erd heeft
	 */
	public KlassiekeOpdracht(int ID, Datum aanmaakDatum, String vraag,
			String juisteAntwoord, int maxAantalPogingen, int maxAntwoordTijd,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(ID, aanmaakDatum, vraag, maxAantalPogingen, maxAntwoordTijd,
				opdrachtCategorie, auteur);
		this.juisteAntwoord = juisteAntwoord;
	}

	/**
	 * Haalt het juiste antwoord op
	 * 
	 * @return de String met het juiste antwoord
	 */
	public String getJuisteAntwoord() {
		return juisteAntwoord;
	}

	/**
	 * Stelt het juiste antwoord op de vraag in
	 * 
	 * @param juisteAntwoord
	 *            de String met het gewenste antwoord
	 * @throws IllegalStateException
	 *             wanneer de opdracht niet meer aanpasbaar is omdat er al
	 *             leerlingen ze hebben opgelost
	 */
	public void setJuisteAntwoord(String juisteAntwoord)
			throws IllegalStateException {
		if (!isAanpasbaar()) {
			throw new IllegalStateException(
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
