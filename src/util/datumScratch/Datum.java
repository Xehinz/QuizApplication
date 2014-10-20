package util.datumScratch;

//Datum Klasse 'from scratch'
//Authors: Tim Cool & Jef Bellens
//Versie 1.2 : 11/10/2014 'Added Equals & CompareTo Method'

import java.util.Calendar;

import javax.naming.directory.InvalidAttributesException;

public class Datum implements Comparable<Datum> {
	private int dag, maand, jaar;
	private int[] aantalDagenPerMaand = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	public Datum() {
		this(Calendar.getInstance().get(Calendar.DATE), Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(
				Calendar.YEAR));
	}

	public Datum(int dag, int maand, int jaar) {
		if (validate(dag, maand, jaar)) {
			this.dag = dag;
			this.maand = maand;
			this.jaar = jaar;
		} else {
			throw new IllegalArgumentException("De ingegeven waarde is geen geldige datum!");
		}
	}

	public Datum(String datum) throws IllegalArgumentException {
		try {
			String[] parts = datum.split("/|-");
			String tempDag = parts[0];
			String tempMaand = parts[1];
			String tempJaar = parts[2];
			if(validate(Integer.parseInt(tempDag), Integer.parseInt(tempMaand), Integer.parseInt(tempJaar)))
			{
				this.dag = Integer.parseInt(tempDag);
				this.maand = Integer.parseInt(tempMaand);
				this.jaar = Integer.parseInt(tempJaar);
			}
			else
			{
				throw new IllegalArgumentException("Ingegeven waarde was incorrect!");
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Ingegeven waarde was incorrect!");
		}
	}

	public Datum(Datum datum){
		this(datum.dag, datum.maand, datum.jaar);
	}

	public int getDag() {
		return dag;
	}

	public int getMaand() {
		return maand;
	}

	public int getJaar() {
		return jaar;
	}
	
	public boolean setDatum(int dag, int maand, int jaar) {
		if (validate(dag, maand, jaar)) {
			this.dag = dag;
			this.maand = maand;
			this.jaar = jaar;
			return true;
		}
		return false;
	}

	public boolean kleinerDan(Datum datum) {
		if (this.jaar < datum.jaar) {
			return false;
		} else if (this.jaar <= datum.jaar && this.maand < datum.maand) {
			return false;
		} else if (this.jaar <= datum.jaar && this.maand <= datum.maand && this.dag < datum.dag) {
			return false;
		}
		return true;
	}

	public Datum veranderdeDatum(int aantalDagen) {
		int dagen = convertDatumNaarDagen(this);

		dagen += aantalDagen;

		return convertDagenNaarDatum(dagen);
	}
	
	public void veranderDatum(int aantalDagen) {
		int dagen = convertDatumNaarDagen(this);
		dagen += aantalDagen;
		Datum tempDatum = convertDagenNaarDatum(dagen);
		if(!validate(tempDatum.dag, tempDatum.maand, tempDatum.jaar))
			throw new IllegalArgumentException();
		else
		{
		this.dag = tempDatum.dag;
		this.maand = tempDatum.maand;
		this.jaar = tempDatum.jaar;
		}
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
		for (int i = 0; i < this.maand-1; i++) {
			dagen += aantalDagenPerMaand[i];
		}
		dagen += this.dag - 1;
		return dagen;
	}

	private Datum convertDagenNaarDatum(int dagen) {
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
	}

	public boolean schrikkelJaar(int jaartal) // VERDER AFWERKEN!!!!!s
	{
		if (jaartal % 4 == 0 && jaartal % 100 != 0 || jaartal % 400 == 0) {
			return true;
		}

		return false;
	}

	private boolean validate(int dag, int maand, int jaar) {
		
		try {
			if (dag > 0 && dag <= 31 && aantalDagenPerMaand[maand-1] > 0 && maand <= 12 && jaar > 0 && jaar <= 9999) {
				if (maand == 2 && dag == 29 && !schrikkelJaar(jaar)) {
					return false;
				}
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
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
		} else if (this.kleinerDan(arg0)) {
			return 1;
		}
		return -1;
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

			datum = new Datum(31, 12, 9999);
			
			System.out.print(datum);
			datum.veranderDatum(1);
			
			System.out.print(datum);
			
		}
		catch (Exception e)
		{
			System.out.print(e.getMessage());
		}
		
	}
}
