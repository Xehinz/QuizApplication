package persistency;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Template class voor het inlezen en wegschrijven van records in tekstformaat. De default implementatie gebruikt
 * bestanden van het .txt formaat. Als useCSV op true gezet wordt gebruikt de klasse bestanden van het .csv formaat
 *
 * @author Ben Vandenberk
 * @version 04/11/2014
 *
 */
public abstract class TxtTemplate {

	protected boolean useCSV = false;

	protected abstract String getBestandsnaam();

	protected abstract <T> T maakObject(String[] fields) throws IOException;

	protected abstract <T> String maakStringRecord(T object) throws IOException;

	protected abstract String getHeaderCSV();

	public TxtTemplate(boolean useCSV) {
		this.useCSV = useCSV;
	}
	
	public <T> ArrayList<T> lees() {
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
		} catch (FileNotFoundException ex) {
			System.err.println("Bestand niet gevonden");
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}

		return objecten;
	}

	public <T> void schrijf(ArrayList<T> objecten) {
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
		} catch (FileNotFoundException ex) {
			System.err.println("Bestand niet gevonden");
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
	}
}
