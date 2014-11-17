package persistency;

import java.io.IOException;

import util.datumWrapper.Datum;
import model.KlassiekeOpdracht;
import model.Leraar;
import model.Opdracht;
import model.OpdrachtCategorie;

/**
 * Klasse om KlassiekeOpdracht objecten weg te schrijven of in te lezen in tekstformaat
 *
 * @author Adriaan Kuipers
 * @version 15/11/2014
 *
 */

class TxtKlassiekeOpdrachtLeesSchrijf extends TxtOpdrachtLeesSchrijf {
	
	public TxtKlassiekeOpdrachtLeesSchrijf(boolean useCSV) {
		super(useCSV);
	}
	
	@Override
	protected String getBestandsnaam() {
		return useCSV ? "resources/klassiekeOpdrachten.csv" : "resources/klassiekeOpdrachten.txt";
	}

	@Override
	protected Opdracht maakObject(String[] fields) throws IOException {
		try {
			KlassiekeOpdracht opdracht = new KlassiekeOpdracht(Integer.parseInt(fields[0]), new Datum(fields[1]), fields[2], fields[8], Integer.parseInt(fields[3]), Integer.parseInt(fields[4]), OpdrachtCategorie.valueOf(fields[5]), Leraar.valueOf(fields[6]));
			voegHintsToe(opdracht, fields[7]);
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
		KlassiekeOpdracht klassiekeOpdracht = null;
		if (object instanceof KlassiekeOpdracht) {
			klassiekeOpdracht = (KlassiekeOpdracht) object;
		} else {
			throw new IOException("Het object om weg te schrijven is geen KlassiekeOpdracht");
		}
		return String.format("%s\t%s", this.maakBasisOpdrachtString(klassiekeOpdracht), klassiekeOpdracht.getJuisteAntwoord());		
	}

	@Override
	protected String getHeaderCSV() {
		return (super.getHeaderCSV() + "\tAntwoord");
	}

}
