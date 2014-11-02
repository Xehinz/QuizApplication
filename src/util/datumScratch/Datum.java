package util.datumScratch;

import java.util.Calendar;

/**
 * Datum klasse opgebouwd 'from scratch', met eigen logica
 *
 * @author Tim Cool, Jef Bellens, Ben Vandenberk
 * @version 1.3 2/11/2014 - Alle tests laten slagen
 *
 */
public class Datum implements Comparable<Datum> {
	private int dag, maand, jaar;
	private int[] aantalDagenPerMaand = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	private static final Datum MAXDATUM = new Datum(31, 12, 9999);
	private static final Datum MINDATUM = new Datum(1, 1, 1);

	/**
	 * Maakt een datum object aan met als waarde de dag van vandaag
	 */
	public Datum() {
		this(Calendar.getInstance().get(Calendar.DATE), Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance()
				.get(Calendar.YEAR));
	}

	/**
	 * Maakt een datum object aan
	 *
	 * @param dag
	 *            de dag van de maand
	 * @param maand
	 *            de maand
	 * @param jaar
	 *            het jaar
	 *
	 * @throws IllegalArgumentException
	 *             als een van de argumenten of de combinatie van argumenten ongeldig is
	 */
	public Datum(int dag, int maand, int jaar) {
		if (isGeldigeDatum(dag, maand, jaar)) {
			this.dag = dag;
			this.maand = maand;
			this.jaar = jaar;
		} else {
			throw new IllegalArgumentException("De ingegeven waarden vormen geen geldige datum");
		}
	}

	/**
	 * Maakt een datum object aan op basis van een string. Format: DD/MM/YYYY of DD-MM-YYYY
	 *
	 * @param datum
	 *            de String met een datum waarde (DD/MM/YYYY of DD-MM-YYYY)
	 * @throws IllegalArgumentException
	 *             als de input String van een foutief formaat is of als de datumwaarde ongeldig is
	 */
	public Datum(String datum) throws IllegalArgumentException {
		try {
			String[] parts = datum.split("/|-");
			String tempDag = parts[0];
			String tempMaand = parts[1];
			String tempJaar = parts[2];
			if (isGeldigeDatum(Integer.parseInt(tempDag), Integer.parseInt(tempMaand), Integer.parseInt(tempJaar))) {
				this.dag = Integer.parseInt(tempDag);
				this.maand = Integer.parseInt(tempMaand);
				this.jaar = Integer.parseInt(tempJaar);
			} else {
				throw new IllegalArgumentException("De ingegeven datum is geen bestaande datum");
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Ingegeven waarde is van een fout formaat");
		}
	}

	/**
	 * Maakt een datum object aan op basis van een ander datum object
	 *
	 * @param datum
	 *            het datum object op basis waarvan de constructor een nieuw datum object aanmaakt
	 */
	public Datum(Datum datum) {
		this(datum.dag, datum.maand, datum.jaar);
	}

	/**
	 * Haalt het dag-gedeelte van het datum object op
	 *
	 * @return de dag
	 */
	public int getDag() {
		return dag;
	}

	/**
	 * Haalt het maand-gedeelte van het datum object op
	 *
	 * @return de maand
	 */
	public int getMaand() {
		return maand;
	}

	/**
	 * Haalt het jaar-gedeelte van het datum object op
	 *
	 * @return het jaar
	 */
	public int getJaar() {
		return jaar;
	}

	/**
	 * Zet het datum object op een geldige waarde
	 *
	 * @param dag
	 *            de dag van de maand
	 * @param maand
	 *            de maand
	 * @param jaar
	 *            het jaar
	 * @return true als het zetten van de datum gelukt is
	 */
	public boolean setDatum(int dag, int maand, int jaar) {
		boolean isGeldig = false;
		try {
			if (isGeldigeDatum(dag, maand, jaar)) {
				this.dag = dag;
				this.maand = maand;
				this.jaar = jaar;
				isGeldig = true;
			}
		} catch (IllegalArgumentException ex) {
			isGeldig = false;
		}
		return isGeldig;
	}

	/**
	 * Geeft true terug als het datum object zich chronologisch vóór het meegegeven datum object bevindt
	 *
	 * @param datum
	 *            het datum object waarmee vergeleken wordt
	 * @return true als het datum object zich chronologisch vóór het meegegeven datum object bevindt
	 */
	public boolean kleinerDan(Datum datum) {
		if (this.jaar < datum.jaar) {
			return true;
		} else if (this.jaar <= datum.jaar && this.maand < datum.maand) {
			return true;
		} else if (this.jaar <= datum.jaar && this.maand <= datum.maand && this.dag < datum.dag) {
			return true;
		}
		return false;
	}

	/**
	 * Geeft een datum object terug dat verschilt van het huidige met het meegegeven aantal dagen. Een positieve waarde
	 * geeft een latere datum terug, een negatieve een eerdere
	 *
	 * @param aantalDagen
	 *            het aantal dagen waarmee de teruggegeven datum verschilt van het datum object
	 * @return het datum object dat met het aantal dagen is veranderd
	 */
	public Datum getVeranderdeDatum(int aantalDagen) {
		Datum veranderdeDatum = new Datum(this);
		if (aantalDagen == 0) {
			return veranderdeDatum;
		}
		if (aantalDagen > 0) {
			for (int i = 0; i < aantalDagen; i++) {
				veranderdeDatum.addDag();
			}
		} else {
			for (int i = 0; i < -aantalDagen; i++) {
				veranderdeDatum.subtractDag();
			}
		}
		return veranderdeDatum;
	}

	/**
	 * Verandert de datum met het meegegeven aantal dagen. Een positief getal verhoogt de datum, een negatief getal
	 * verlaagt ze
	 *
	 * @param aantalDagen
	 *            het aantal dagen waarmee de datum aangepast wordt
	 */
	public void veranderDatum(int aantalDagen) {
		if (aantalDagen == 0) {
			return;
		}
		if (aantalDagen > 0) {
			for (int i = 0; i < aantalDagen; i++) {
				this.addDag();
			}
		} else {
			for (int i = 0; i < -aantalDagen; i++) {
				this.subtractDag();
			}
		}
	}

	/**
	 * Geeft het aantal gehele dagen terug dat zich bevindt tussen het datum object en het meegegeven datum object
	 *
	 * @param datum
	 *            het datum object waarmee vergeleken wordt
	 * @return het aantal gehele dagen dat zich bevindt tussen het datum object en het meegegeven datum object
	 */
	public int verschilInDagen(Datum datum) {
		if (this.equals(datum)) {
			return 0;
		}
		int dagen = 0;
		Datum kleinste = datum.kleinerDan(this) ? datum : new Datum(this);
		Datum grootste = datum.kleinerDan(this) ? new Datum(this) : datum;

		while (!kleinste.equals(grootste)) {
			kleinste.addDag();
			dagen++;
		}

		return dagen;
	}

	/**
	 * Geeft het aantal gehele maanden terug dat zich bevindt tussen het datum object en het meegegeven datum object
	 *
	 * @param datum
	 *            het datum object waarmee vergeleken wordt
	 * @return het aantal gehele maanden dat zich bevindt tussen het datum object en het meegegeven datum object
	 */
	public int verschilInMaanden(Datum datum) {
		if (this.jaar == datum.jaar && this.maand == datum.maand) {
			return 0;
		}

		int maanden = 0;
		Datum kleinste = datum.kleinerDan(this) ? datum : new Datum(this);
		Datum grootste = datum.kleinerDan(this) ? new Datum(this) : datum;

		while (kleinste.jaar < grootste.jaar || (kleinste.jaar == grootste.jaar && kleinste.maand < grootste.maand)) {
			if (kleinste.maand != 12) {
				kleinste.maand++;
				if (kleinste.dag > getAantalDagenInMaand(kleinste.maand, kleinste.jaar)) {
					kleinste.dag = getAantalDagenInMaand(kleinste.maand, kleinste.jaar);
				}
				maanden++;
			} else {
				kleinste.maand = 1;
				kleinste.jaar++;
				maanden++;
			}
		}

		if (kleinste.dag > grootste.dag) {
			maanden--;
		}

		return maanden;
	}

	/**
	 * Geeft het aantal gehele jaren terug dat zich bevindt tussen het datum object en het meegegeven datum object
	 *
	 * @param datum
	 *            het datum object waarmee vergeleken wordt
	 * @return het aantal gehele jaren dat zich bevindt tussen het datum object en het meegegeven datum object
	 */
	public int verschilInJaren(Datum datum) {
		if (this.jaar == datum.jaar) {
			return 0;
		}

		int jaren = 0;
		Datum kleinste = datum.kleinerDan(this) ? datum : new Datum(this);
		Datum grootste = datum.kleinerDan(this) ? new Datum(this) : datum;

		boolean kleinsteIsSchrikkelDag = kleinste.maand == 2 && kleinste.dag == 29;

		while (kleinste.jaar < grootste.jaar) {
			kleinste.jaar++;
			jaren++;
		}

		if (kleinsteIsSchrikkelDag) {
			kleinste.dag = getAantalDagenInMaand(2, kleinste.jaar);
		}

		if (kleinste.maand > grootste.maand || (kleinste.maand == grootste.maand && kleinste.dag > grootste.dag)) {
			if (!kleinsteIsSchrikkelDag && (grootste.maand == 2 && grootste.dag == 28)) {
				jaren--;
			}
		}

		return jaren;
	}

	/**
	 * Geeft een string terug in Amerikaans formaat (YYYY/MM/DD)
	 *
	 * @return een string in Amerikaans formaat (YYYY/MM/DD)
	 */
	public String getDatumInAmerikaansFormaat() {
		return jaar + "/" + maand + "/" + dag;
	}

	/**
	 * Geeft een string terug in Europees formaat (DD/MM/YYYY)
	 *
	 * @return een string in Europees formaat (DD/MM/YYYY)
	 */
	public String getDatumInEuropeesFormaat() {
		return dag + "/" + maand + "/" + jaar;
	}

	@Override
	public String toString() {
		return dag + " " + convertMonthToString(this.maand) + " " + jaar;
	}

	@Override
	public int compareTo(Datum datum) {
		if (this.equals(datum)) {
			return 0;
		} else if (this.kleinerDan(datum)) {
			return -1;
		}
		return 1;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Datum)) {
			return false;
		}
		Datum other = (Datum) obj;
		if (other.dag != this.dag) {
			return false;
		}
		if (other.maand != this.maand) {
			return false;
		}
		if (other.jaar != this.jaar) {
			return false;
		}
		return true;
	}

	private void addDag() {
		if (maand != 2) { // Niet februari
			if (dag != aantalDagenPerMaand[maand - 1]) {
				dag++;
			} else if (maand != 12) {
				dag = 1;
				maand++;
			} else {
				if (this.equals(MAXDATUM)) {
					throw new IllegalArgumentException("De maximum datum voor een Datum object is 31/12/9999");
				}
				dag = 1;
				maand = 1;
				jaar++;
			}
		} else { // februari
			if (dag < 28) {
				dag++;
			} else if (dag == 28) {
				if (isSchrikkeljaar(jaar)) {
					dag++;
				} else {
					dag = 1;
					maand++;
				}
			} else if (dag == 29) {
				dag = 1;
				maand++;
			}
		}
	}

	private void subtractDag() {
		if (maand != 3) { // Niet maart
			if (dag != 1) {
				dag--;
			} else if (maand != 1) {
				dag = aantalDagenPerMaand[maand - 2];
				maand--;
			} else {
				if (this.equals(MINDATUM)) {
					throw new IllegalArgumentException("De minimum datum voor een Datum object is 01/01/0001");
				}
				dag = 31;
				maand = 12;
				jaar--;
			}
		} else { // maart
			if (dag != 1) {
				dag--;
			} else {
				if (isSchrikkeljaar(jaar)) {
					dag = 29;
					maand = 2;
				} else {
					dag = 28;
					maand = 2;
				}
			}
		}
	}

	private static boolean isSchrikkeljaar(int jaartal) {
		if (jaartal % 4 == 0 && jaartal % 100 != 0 || jaartal % 400 == 0) {
			return true;
		}

		return false;
	}

	private int getAantalDagenInMaand(int maand, int jaar) {
		if (maand != 2) {
			return aantalDagenPerMaand[maand - 1];
		} else {
			if (isSchrikkeljaar(jaar)) {
				return 29;
			} else {
				return 28;
			}
		}
	}

	private boolean isGeldigeDatum(int dag, int maand, int jaar) {
		if (jaar < 1 || jaar > 9999) {
			throw new IllegalArgumentException("De Datum klasse aanvaardt enkel datums tussen 01/01/0001 en 31/12/9999");
		}
		if (maand < 1 || maand > 12) {
			throw new IllegalArgumentException("De maand moet een waarde van 1 tot en met 12 zijn");
		}
		if (dag > getAantalDagenInMaand(maand, jaar) || dag < 1) {
			throw new IllegalArgumentException("De waarde voor de dag is ongeldig voor de combinatie van maand en jaar");
		}
		return true;
	}

	private String convertMonthToString(int maand) {
		switch (maand) {
		case 1:
			return "januari";
		case 2:
			return "februari";
		case 3:
			return "maart";
		case 4:
			return "april";
		case 5:
			return "mei";
		case 6:
			return "juni";
		case 7:
			return "juli";
		case 8:
			return "augustus";
		case 9:
			return "september";
		case 10:
			return "oktober";
		case 11:
			return "november";
		case 12:
			return "december";
		}
		return "Faulty input";
	}
}
