package persistency;

import java.io.IOException;

import model.OpdrachtAntwoord;

public class TxtOpdrachtAntwoordLeesSchrijf extends TxtTemplate {

	@Override
	protected String getBestandsnaam() {
		return useCSV ? "opdrachtAntwoorden.csv" : "opdrachtAntwoorden.txt";
	}

	@Override
	protected PseudoOpdrachtAntwoord maakObject(String[] fields) throws IOException {
		try {
			return new PseudoOpdrachtAntwoord(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]),
					Integer.parseInt(fields[2]), Integer.parseInt(fields[3]), fields[4]);
		} catch (NumberFormatException Nex) {
			throw new IOException("Fout bij het parsen vanuit tekstbestand", Nex);
		} catch (IndexOutOfBoundsException Iex) {
			throw new IOException("Een record bevatte te weinig velden om een OpdrachtAntwoord aan te maken", Iex);
		} catch (Exception ex) {
			throw new IOException("Fout: " + ex.getMessage(), ex);
		}
	}

	@Override
	protected String maakStringRecord(Object object) throws IOException {
		OpdrachtAntwoord opdrachtAntwoord = null;
		if (object instanceof OpdrachtAntwoord) {
			opdrachtAntwoord = (OpdrachtAntwoord) object;
		} else {
			throw new IOException("Het object om weg te schrijven is geen OpdrachtAntwoord");
		}
		return String.format("%s\t%s\t%s\t%s\t%s", opdrachtAntwoord.getQuizDeelname().getID(), opdrachtAntwoord.getQuizOpdracht()
				.getID(), opdrachtAntwoord.getAantalPogingen(), opdrachtAntwoord.getAntwoordTijd(), opdrachtAntwoord
				.getLaatsteAntwoord());
	}

	@Override
	protected String getHeaderCSV() {
		return "QuizDeelnameID\tQuizOpdrachtID\tAantalPogingen\tAntwoordTijd\tLaatsteAntwoord";
	}

}
