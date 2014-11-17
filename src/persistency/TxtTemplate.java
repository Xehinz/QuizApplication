package persistency;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Template class voor het inlezen en wegschrijven van records in tekstformaat.
 * Geef true mee aan de constructor om in .csv formaat te werken.
 *
 * @author Ben Vandenberk
 * @version 04/11/2014
 *
 */
abstract class TxtTemplate {

	protected boolean useCSV = false;

	protected abstract String getBestandsnaam();

	protected abstract <T> T maakObject(String[] fields) throws IOException;

	protected abstract <T> String maakStringRecord(T object) throws IOException;

	protected abstract String getHeaderCSV();

	public TxtTemplate(boolean useCSV) {
		this.useCSV = useCSV;
	}

	public <T> ArrayList<T> lees() throws IOException {
		File inputFile = new File(getBestandsnaam());
		ArrayList<T> objecten = new ArrayList<T>();

		try {
			Scanner scanner = new Scanner(inputFile);
			String record;

			// Header van .CSV overslaan
			if (useCSV) {
				scanner.nextLine();
			}

			while (scanner.hasNextLine()) {
				record = scanner.nextLine();
				String[] fields = record.split("\t");
				objecten.add((T) maakObject(fields));
			}
			scanner.close();
		} catch (FileNotFoundException Fex) {
			throw new IOException(String.format("Bestand [%s] niet gevonden", getBestandsnaam()), Fex);
		} catch (Exception ex) {
			throw new IOException("Leesfout: " + ex.getMessage(), ex);
		}

		return objecten;
	}

	public <T> void schrijf(ArrayList<T> objecten) throws IOException {
		File outputFile = new File(getBestandsnaam());

		try {
			Writer writer = new FileWriter(outputFile);

			if (useCSV) {
				writer.write(getHeaderCSV() + "\n");
			}

			for (T object : objecten) {
				writer.write(maakStringRecord(object) + "\n");
			}

			writer.close();
		} catch (FileNotFoundException Fex) {
			throw new IOException(String.format("Bestand [%s] niet gevonden", getBestandsnaam()), Fex);
		} catch (Exception ex) {
			throw new IOException("Schrijffout: " + ex.getMessage(), ex);
		}
	}
}
