package persistency;

import java.io.IOException;

import model.OpdrachtAntwoord;

class DBOpdrachtAntwoordLeesSchrijf extends DBTemplate {

	public DBOpdrachtAntwoordLeesSchrijf(String jdbcConnectionString) {
		super(jdbcConnectionString);
	}

	@Override
	protected String getLeesQuery() {
		return "SELECT * FROM opdracht_antwoorden";
	}

	@Override
	protected PseudoOpdrachtAntwoord maakObject(Object[] rij) {
		String quizDeelnameID = rij[0].toString();
		String quizOpdrachtID = rij[1].toString();
		int aantalPogingen = (Integer) rij[2];
		int antwoordTijd = (Integer) rij[3];
		String laatsteAntwoord = (String) rij[4];
		return new PseudoOpdrachtAntwoord(quizDeelnameID, quizOpdrachtID,
				aantalPogingen, antwoordTijd, laatsteAntwoord);
	}

	@Override
	protected String getSchrijfStatement(Object object) throws IOException {
		OpdrachtAntwoord opdrachtAntwoord = null;
		if (object instanceof OpdrachtAntwoord) {
			opdrachtAntwoord = (OpdrachtAntwoord) object;
		} else {
			throw new IOException(
					"Het object om weg te schrijven is geen OpdrachtAntwoord");
		}
		return String.format(
				"INSERT INTO opdracht_antwoorden VALUES('%s', '%s', %d, %d, '%s')",
				opdrachtAntwoord.getQuizDeelname().getID(), opdrachtAntwoord
						.getQuizOpdracht().getID(), opdrachtAntwoord
						.getAantalPogingen(), opdrachtAntwoord
						.getAntwoordTijd(), opdrachtAntwoord
						.getLaatsteAntwoord());
	}

	@Override
	protected String getDeleteStatement() {
		return "TRUNCATE opdracht_antwoorden";
	}

}
