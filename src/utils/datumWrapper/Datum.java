package utils.datumWrapper;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.DateFormatSymbols;

import static java.lang.Math.*;

/**
 * 
 * @author Bert Neyt
 * @author Adriaan Kuipers
 * @author Ben Vandenberk
 * Custom Datum klasse die de java.util.GregorianCalendar klasse wrapt
 * Versie 4/10/2014
 * 
 */
public class Datum {

	private GregorianCalendar gregorianCalendarDatum;

	public Datum() {
		this.gregorianCalendarDatum = new GregorianCalendar();
	}

	public Datum(int dag, int maand, int jaar) {
		boolean b = this.setDatum(dag, maand, jaar);
		if (!b) {
			throw new IllegalArgumentException("Ongeldige datum!");
		}
	}

	public Datum(Datum datum) {
		this(datum.getDag(), datum.getMaand(), datum.getJaar());
	}

	public Datum(String datum) {
		String[] parts = datum.split("/");
		String dag = parts[0];
		String maand = parts[1];
		String jaar = parts[2];
		try {
			boolean b = this.setDatum(Integer.parseInt(dag), Integer.parseInt(maand), Integer.parseInt(jaar));
			if (!b) {
				throw new IllegalArgumentException("Ongeldige datum!");
			}
		} catch (NumberFormatException N) {
			throw new NumberFormatException(N.getMessage() + ": Foutieve input");
		}
	}

	public int getJaar() {
		return this.gregorianCalendarDatum.get(GregorianCalendar.YEAR);
	}

	public int getMaand() {
		return this.gregorianCalendarDatum.get(GregorianCalendar.MONTH) + 1;
	}

	public int getDag() {
		return this.gregorianCalendarDatum.get(GregorianCalendar.DAY_OF_MONTH);
	}

	public boolean setDatum(int dag, int maand, int jaar) {
		try {
			GregorianCalendar GC = new GregorianCalendar(jaar, maand - 1, dag);
			GC.setLenient(false);
			this.gregorianCalendarDatum = GC;
			GC.getTime();

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String getDatumInAmerikaansFormaat() {
		return this.getJaar() + "/" + this.getMaand() + "/" + this.getDag();
	}

	public String getDatumInEuropeesFormaat() {
		return this.getDag() + "/" + this.getMaand() + "/" + this.getJaar();
	}

	public String toString() {
		return this.getDag() + " " + new DateFormatSymbols().getMonths()[this.getMaand() - 1] + " " + this.getJaar();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Datum){
			return this.getDatumInAmerikaansFormaat().equals(((Datum)obj).getDatumInAmerikaansFormaat());
		}
		else {
			return false;
		}
	}
	
	public int compareTo(Datum datum) {
		GregorianCalendar inputDatum = new GregorianCalendar(datum.getJaar(), datum.getMaand() - 1, datum.getDag());
		return this.gregorianCalendarDatum.compareTo(inputDatum);
	}
	
	public boolean kleinerDan (Datum datum) {
		GregorianCalendar inputDatum = new GregorianCalendar(datum.getJaar(), datum.getMaand() - 1, datum.getDag());
		return inputDatum.before(this.gregorianCalendarDatum);
	}
	
	public int verschilInJaren (Datum datum) {
		GregorianCalendar inputDatum = new GregorianCalendar(datum.getJaar(), datum.getMaand() - 1, datum.getDag());
		if (this.equals(datum)) {
			return 0;
		}
		int verschil = -1;
		if (this.gregorianCalendarDatum.after(inputDatum)) {
			while (inputDatum.before(this.gregorianCalendarDatum)) {
			    inputDatum.add(Calendar.YEAR, 1);
			    verschil++;
			}
		}
		else {
			GregorianCalendar thisClone = new GregorianCalendar(this.getJaar(), this.getMaand() - 1, this.getDag());
			while (thisClone.before(inputDatum)) {
				thisClone.add(Calendar.YEAR, 1);
			    verschil++;
			}
		}
		return verschil;
	}
	
	public int verschilInMaanden (Datum datum) {
		GregorianCalendar inputDatum = new GregorianCalendar(datum.getJaar(), datum.getMaand() - 1, datum.getDag());
		if (this.equals(datum)) {
			return 0;
		}
		int verschil = -1;
		if (this.gregorianCalendarDatum.after(inputDatum)) {
			while (inputDatum.before(this.gregorianCalendarDatum)) {
			    inputDatum.add(Calendar.MONTH, 1);
			    verschil++;
			}
		}
		else {
			GregorianCalendar thisClone = new GregorianCalendar(this.getJaar(), this.getMaand() - 1, this.getDag());
			while (thisClone.before(inputDatum)) {
				thisClone.add(Calendar.MONTH, 1);
			    verschil++;
			}
		}
		return verschil;
	}
	

	public int verschilInDagen(Datum datum) {
		GregorianCalendar inputDatum = new GregorianCalendar(datum.getJaar(), datum.getMaand() - 1, datum.getDag());
		long verschil = abs(inputDatum.getTimeInMillis() - this.gregorianCalendarDatum.getTimeInMillis());
		return (int) (verschil / (1000 * 60 * 60 * 24));
	}

	public void veranderDatum(int aantalDagen) {
		this.gregorianCalendarDatum.add(Calendar.DAY_OF_YEAR, aantalDagen);
	}

	public Datum getVeranderdeDatum(int aantalDagen) {
		Datum resultaatDatum = new Datum(this);		
		resultaatDatum.veranderDatum(aantalDagen);		
		return resultaatDatum;
	}

	public static void main(String[] args) {
		Datum a = new Datum(12,07,2012);
		a.getDatumInEuropeesFormaat();
		System.out.println(a.getDatumInEuropeesFormaat());
		Datum b = new Datum(12,07,2012);
		System.out.println(b.getDatumInEuropeesFormaat());
		Datum c = new Datum(17,05,2015);
		System.out.println(c.getDatumInEuropeesFormaat());
		Datum d = new Datum(04,01,1999);
		System.out.println(d.getDatumInEuropeesFormaat());
		System.out.println();
		
		System.out.println(a.compareTo(b));
		System.out.println(a.compareTo(c));
		System.out.println(a.compareTo(d));
		System.out.println();
		
		System.out.println(a.equals(b));
		System.out.println(a.equals(c));
		System.out.println();
		
		System.out.println(a.kleinerDan(b));
		System.out.println(a.kleinerDan(c));
		System.out.println(a.kleinerDan(d));
		System.out.println();
		
		System.out.println(a.verschilInJaren(b));
		System.out.println(a.verschilInJaren(c));
		System.out.println(a.verschilInJaren(d));
		System.out.println();
		
		System.out.println(a.verschilInMaanden(b));
		System.out.println(a.verschilInMaanden(c));
		System.out.println(a.verschilInMaanden(d));
		System.out.println();
	}

}
