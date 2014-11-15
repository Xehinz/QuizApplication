package persistency;

import java.io.IOException;

import util.datumWrapper.Datum;
import model.Leraar;
import model.Meerkeuze;
import model.Opdracht;
import model.OpdrachtCategorie;

/**
 * Klasse om Meerkeuze objecten weg te schrijven of in te lezen in tekstformaat
 *
 * @author Adriaan Kuipers
 * @version 14/11/2014
 *
 */

public class TxtMeerkeuzeLeesSchrijf extends TxtOpdrachtLeesSchrijf {

	@Override
	protected String getBestandsnaam() {
		return useCSV ? "resources/meerkeuzeOpdrachten.csv" : "resources/meerkeuzeOpdrachten.txt";
	}

	@Override
	protected Opdracht maakObject(String[] fields) throws IOException {
		try {
			Meerkeuze opdracht = new Meerkeuze(Integer.parseInt(fields[0]), new Datum(fields[1]), fields[2], fields[8], Integer.parseInt(fields[3]), Integer.parseInt(fields[4]), OpdrachtCategorie.valueOf(fields[5]), Leraar.valueOf(fields[6]));
			voegHintsToe(opdracht, fields[7]);
			opdracht.setOpties(fields[9]);
			return opdracht;
		} catch (NumberFormatException Nex) {
			throw new IOException("Fout bij het parsen van het ID / Max pogingen of Max tijd", Nex);
		} catch (IndexOutOfBoundsException Iex) {
			throw new IOException("Een record bevatte te weinig velden om een Opdracht aan te maken", Iex);
		} catch (Exception ex) {
			throw new IOException("Fout: " + ex.getMessage(), ex);
		}
	}

	@Override
	protected String maakStringRecord(Object object) throws IOException {
		Meerkeuze meerkeuze = null;
		if (object instanceof Meerkeuze) {
			meerkeuze = (Meerkeuze) object;
		} else {
			throw new IOException("Het object om weg te schrijven is geen Meerkeuze");
		}
		String opties = new String();
		for (String s : meerkeuze.getOpties()) {
			opties += String.format("%s;", s);
		}
		return String.format("%s\t%s\t%s", super.maakStringRecord(object), opties, meerkeuze.getJuisteAntwoord());
	}

	@Override
	protected String getHeaderCSV() {
		return (super.getHeaderCSV() + "\tOpties\tJuiste Optie");
	}

}
