package model;

import java.util.ArrayList;
import java.util.Arrays;

public class Opsomming extends Opdracht implements Valideerbaar {

	private ArrayList<String> opsommingJuisteAntwoord;
	private boolean inJuisteVolgorde;
	private int aantalAntwoordenInOpsomming;

	public Opsomming(OpdrachtCategorie opdrachtCategorie, Leraar auteur) {
		super(opdrachtCategorie, auteur);
	}

	public Opsomming(String vraag, ArrayList<String> juisteAntwoord,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur,
			boolean injuistevolgorde) {
		super(vraag, opdrachtCategorie, auteur);
		this.opsommingJuisteAntwoord = juisteAntwoord;
		this.inJuisteVolgorde = injuistevolgorde;
	}

	public Opsomming(String vraag, int maxAantalPogingen,
			ArrayList<String> juisteAntwoord,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur,
			boolean injuistevolgorde) {
		super(vraag, maxAantalPogingen, 0, opdrachtCategorie, auteur);
		this.opsommingJuisteAntwoord = juisteAntwoord;
		this.inJuisteVolgorde = injuistevolgorde;
	}

	public Opsomming(String vraag, ArrayList<String> juisteAntwoord,
			int maxAntwoordTijd, OpdrachtCategorie opdrachtCategorie,
			Leraar auteur, boolean injuistevolgorde) {
		super(vraag, maxAntwoordTijd, opdrachtCategorie, auteur);
		this.opsommingJuisteAntwoord = juisteAntwoord;
		this.inJuisteVolgorde = injuistevolgorde;
	}

	public Opsomming(String vraag, ArrayList<String> juisteAntwoord,
			int maxAantalPogingen, int maxAntwoordTijd,
			OpdrachtCategorie opdrachtCategorie, Leraar auteur,
			boolean injuistevolgorde) {
		super(vraag, maxAantalPogingen, maxAntwoordTijd, opdrachtCategorie,
				auteur);
		this.opsommingJuisteAntwoord = juisteAntwoord;
		this.inJuisteVolgorde = injuistevolgorde;
	}

	@Override
	public boolean isValide(String antwoord) {
		ArrayList<String> antwoorden = new ArrayList<String>(
				Arrays.asList(antwoord.split(";")));
		if (antwoorden.size() == this.aantalAntwoordenInOpsomming)
		return true;
		else return false;
	}

	@Override
	public String getValideerTekst() {
		return "Typ je antwoorden achter elkaar gescheiden door ;";
	}

	@Override
	public boolean isJuisteAntwoord(String antwoord) {
		ArrayList<String> antwoorden = new ArrayList<String>(
				Arrays.asList(antwoord.split(";")));
		if (this.inJuisteVolgorde) {
			if (this.opsommingJuisteAntwoord.equals(antwoorden))
				return true;
			else
				return false;
		} else {
			if (this.opsommingJuisteAntwoord.containsAll(antwoorden)
					&& antwoorden.containsAll(this.opsommingJuisteAntwoord))
				return true;
			else
				return false;
		}
	}

	@Override
	public String toString() {
		return super.toString(); // Nog aanvullen
	}
}
