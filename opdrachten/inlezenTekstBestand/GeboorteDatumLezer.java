package inlezenTekstBestand;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import util.datumWrapper.Datum;

public class GeboorteDatumLezer {

	public static void main(String[] args) {

		File inputFile = new File("resources/personen.txt");
		ArrayList<Persoon> personen = new ArrayList<Persoon>();

		try {
			Scanner scanner = new Scanner(inputFile);
			String temp;

			while (scanner.hasNextLine()) {
				temp = scanner.nextLine();
				String[] naamEnGeboorteDatum = temp.split("\t");
				if (naamEnGeboorteDatum.length > 1) {
					personen.add(new Persoon(naamEnGeboorteDatum[1], naamEnGeboorteDatum[0]));
				}
			}
			scanner.close();
		} catch (FileNotFoundException fnfEx) {
			System.out.println("File not found");
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		Persoon oudste = new Persoon(new Datum(), "oudste");
		Persoon jongste = new Persoon(new Datum(1, 1, 200), "jongste");

		for (Persoon persoon : personen) {
			if (persoon.getGeboorteDatum() != null) {
				if (persoon.getGeboorteDatum().compareTo(oudste.getGeboorteDatum()) < 0) {
					oudste = persoon;
				}
				if (persoon.getGeboorteDatum().compareTo(jongste.getGeboorteDatum()) > 0) {
					jongste = persoon;
				}
			}
		}

		System.out.println("De jongste persoon: " + jongste);
		System.out.println("De oudste persoon: " + oudste);
		System.out.printf("De jongste persoon is %d dagen jonger dan de oudste\n",
				jongste.getGeboorteDatum().verschilInDagen(oudste.getGeboorteDatum()));
		System.out.printf("De jongste persoon is %d jaren jonger dan de oudste",
				jongste.getGeboorteDatum().verschilInJaren(oudste.getGeboorteDatum()));

		System.out.println("\n\nVolgende personen hadden een ongeldige geboortedatum:\n");

		for (Persoon persoon : personen) {
			if (persoon.getGeboorteDatum() == null) {
				System.out.println(persoon);
			}
		}

	}

}
