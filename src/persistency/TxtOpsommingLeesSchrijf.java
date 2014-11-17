package persistency;

import java.io.IOException;

import util.datumWrapper.Datum;
import model.OpdrachtCategorie;
import model.Opsomming;
import model.Opdracht;
import model.Leraar;

/**
 * Klasse om Opsomming objecten weg te schrijven of in te lezen in tekstformaat
 *
 * @author Adriaan Kuipers
 * @version 15/11/2014
 *
 */

class TxtOpsommingLeesSchrijf extends TxtOpdrachtLeesSchrijf {

	public TxtOpsommingLeesSchrijf(boolean useCSV) {
		super(useCSV);
	}
	
	@Override
	protected String getBestandsnaam() {
		return useCSV ? "resources/opsommingOpdrachten.csv" : "resources/opsommingOpdrachten.txt";
	}

	@Override
	protected Opdracht maakObject(String[] fields) throws IOException {
		try {
			Opsomming opdracht = new Opsomming(Integer.parseInt(fields[0]), new Datum(fields[1]), fields[2], fields[8], Integer.parseInt(fields[3]), Integer.parseInt(fields[4]), OpdrachtCategorie.valueOf(fields[5]), Leraar.valueOf(fields[6]), Boolean.parseBoolean(fields[9]));
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
		Opsomming opsomming = null;
		if (object instanceof Opsomming) {
			opsomming = (Opsomming) object;
		} else {
			throw new IOException("Het object om weg te schrijven is geen Opsomming");
		}
		return String.format("%s\t%s\t%s", this.maakBasisOpdrachtString(opsomming), opsomming.getJuisteAntwoord(), opsomming.getInJuisteVolgorde());		
	}

	@Override
	protected String getHeaderCSV() {
		return (super.getHeaderCSV() + "\tAntwoorden\tIn Volgorde");
	}

}
