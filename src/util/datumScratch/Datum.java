package util.datumScratch;

//Datum Klasse 'from scratch'
//Authors: Tim Cool & Jef Bellens
//Versie 1.2 : 11/10/2014 'Added Equals & CompareTo Method'

import java.util.Calendar;

import javax.naming.directory.InvalidAttributesException;

public class Datum implements Comparable<Datum> {
	private int dag, maand, jaar;
	private int[] aantalDagenPerMaand = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	public Datum() throws InvalidAttributesException {
		this(Calendar.getInstance().get(Calendar.DATE), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(
				Calendar.YEAR) + 2);
	}

	public Datum(int dag, int maand, int jaar) throws InvalidAttributesException {
		if (validate(dag, maand, jaar)) {
			this.dag = dag;
			this.maand = maand;
			this.jaar = jaar;
		} else {
			throw new InvalidAttributesException("De ingegeven waarde is geen geldige datum!");
		}
	}

	public Datum(String datum) throws InvalidAttributesException {
		super();
		try {
			this.dag = Integer.parseInt(datum.substring(0, 2));
			this.maand = Integer.parseInt(datum.substring(3, 5));
			this.jaar = Integer.parseInt(datum.substring(6, 10));
		} catch (NumberFormatException ex) {
			throw new InvalidAttributesException("Foutive invoer formaat!");
		}
	}

	public Datum(Datum datum) throws InvalidAttributesException {
		super();
		this.dag = datum.dag;
		this.maand = datum.maand;
		this.jaar = datum.jaar;
	}

	public void setDatum(int dag, int maand, int jaar) throws InvalidAttributesException {
		if (validate(dag, maand, jaar)) {
			this.dag = dag;
			this.maand = maand;
			this.jaar = jaar;
		} else {
			throw new InvalidAttributesException("De ingegeven waarde is geen geldige datum!");
		}
	}

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

	public Datum veranderDatum(int aantalDagen) throws InvalidAttributesException {
		int dagen = convertDatumNaarDagen(this);

		dagen += aantalDagen;

		return convertDagenNaarDatum(dagen);
	}

	public int verschilInDagen(Datum d) {
		return this.convertDatumNaarDagen(this) - d.convertDatumNaarDagen(d);

	}

	public int verschilInMaanden(Datum d) {
		if (d.maand <= 2 && this.maand >= 2 && verschilInDagen(d) >= 12) {
			return (int) Math.floor(((this.convertDatumNaarDagen(this) - d.convertDatumNaarDagen(d)) / 30.39));
		} else {
			return (int) Math.floor(((this.convertDatumNaarDagen(this) - d.convertDatumNaarDagen(d)) / 30.39) + 0.03);
		}
	}

	public int verschilInJaren(Datum d) {
		return (int) ((this.convertDatumNaarDagen(this) - d.convertDatumNaarDagen(d)) / 365.2425);

	}

	private int convertDatumNaarDagen(Datum datum) {
		int dagen = 0;
		for (int i = 0; i < this.jaar; i++) {
			if (schrikkelJaar(i)) {
				dagen += 366;
			} else {
				dagen += 365;
			}
		}
		for (int i = 0; i < this.maand - 1; i++) {
			dagen += aantalDagenPerMaand[i];
		}
		dagen += this.dag - 1;
		return dagen;
	}

	private Datum convertDagenNaarDatum(int dagen) {
		try {
			int jaar = 0;
			for (int i = dagen; i >= 365; i -= 365) {
				if (schrikkelJaar(jaar)) {
					i -= 1;
					dagen -= 1;
				}
				jaar++;
				dagen -= 365;
			}
			int maand = 1;
			for (int i = 0; i < aantalDagenPerMaand.length; i++) {
				if (dagen >= aantalDagenPerMaand[i]) {
					maand++;
					dagen -= aantalDagenPerMaand[i];
				}
			}
			int dag = dagen + 1;
			Datum tempDatum;

			tempDatum = new Datum(dag, maand, jaar);
			return tempDatum;
		} catch (InvalidAttributesException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean schrikkelJaar(int jaartal) // VERDER AFWERKEN!!!!!s
	{
		if (jaartal % 4 == 0 && jaartal % 100 != 0 || jaartal % 400 == 0) {
			return true;
		}

		return false;
	}

	private boolean validate(int dag, int maand, int jaar) {
		if (dag > 0 && dag <= 31 && maand > 0 && maand <= 12 && jaar > 0 && jaar <= 9999) {
			return true;
		}
		return false;
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

	public String getDatumInAmerikaansFormaat() {
		return jaar + "/" + maand + "/" + dag;
	}

	public String getDatumInEuropeesFormaat() {
		return dag + "/" + maand + "/" + jaar;
	}

	@Override
	public String toString() {
		return dag + " " + convertMonthToString(this.maand) + " " + jaar;
	}

	@Override
	public int compareTo(Datum arg0) {
		if (this.equals(arg0)) {
			return 0;
		} else if (arg0.kleinerDan(this)) {
			return -1;
		}
		return 1;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Datum) {
			if (this.toString().equals(((Datum) obj).toString())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		try {
			// Testcode
			Datum datum;
			Datum datum2;

			datum = new Datum("03/02/1991");
			datum2 = new Datum("03/02/1991");

			// datum2 = new Datum("29/01/1991");
			System.out.print(datum.equals(datum2) + " " + datum.compareTo(datum2) + "\n");
			System.out.print(datum + " is een schrikkeljaar? " + datum.schrikkelJaar(datum.jaar)
					+ datum.convertDatumNaarDagen(datum) + "\n");
			System.out.print(datum2 + " is een schrikkeljaar? " + datum2.schrikkelJaar(datum.jaar)
					+ datum2.convertDatumNaarDagen(datum2) + "\n");
			System.out.print("Amerikaans Formaat:\t" + datum.getDatumInAmerikaansFormaat() + "\n");
			System.out.print("Europees Formaat:\t" + datum.getDatumInEuropeesFormaat() + "\n");
			System.out.print("Dagen:\t " + datum.verschilInDagen(datum2) + "\n");
			System.out.print("Maanden: " + datum.verschilInMaanden(datum2) + "\n");
			System.out.print("Jaren:\t " + datum.verschilInJaren(datum2));
		} catch (InvalidAttributesException e) {
			System.out.print(e.getMessage());
			// e.printStackTrace();
		}
	}
}
