package model;

import java.util.ArrayList;

/**
 *
 * Leerling modelleert de leerling die kan deelnemen aan de quizzen. De leerling
 * heeft een voornaam en familienaam en zit in een van de zes leerjaren
 *
 * @author Ben Vandenberk
 * @version 25-10-2014
 *
 */
public class Leerling implements Comparable<Leerling>, Cloneable {

	private String leerlingVoornaam, leerlingFamilienaam;
	private int leerjaar, ID;
	private ArrayList<QuizDeelname> quizDeelnames;

	/**
	 * Maakt een Leerling object aan op basis van de voornaam, familienaam en
	 * het leerjaar van de leerling
	 *
	 * @param voornaam
	 *            de String die de voornaam voorstelt
	 * @param familienaam
	 *            de String die de familienaam voorstelt
	 * @param leerjaar
	 *            een integer van 1 tot en met 6 dat het leerjaar voorstelt
	 */
	public Leerling(String voornaam, String familienaam, int leerjaar) {
		setLeerlingVoornaam(voornaam);
		setLeerlingFamilienaam(familienaam);
		setLeerjaar(leerjaar);
		quizDeelnames = new ArrayList<QuizDeelname>();		
	}

	/**
	 * Maakt een Leerling object aan dat al een ID toegewezen had. Gebruik deze
	 * constructor alleen om een Leerling te maken uit opslag (tekst / DB)
	 * 
	 * @param ID
	 *            de ID van de Leerling
	 * @param voornaam
	 *            de String die de voornaam voorstelt
	 * @param familienaam
	 *            de String die de familienaam voorstelt
	 * @param leerjaar
	 *            een integer van 1 tot en met 6 dat het leerjaar voorstelt
	 */
	public Leerling(int ID, String voornaam, String familienaam, int leerjaar) {
		this(voornaam, familienaam, leerjaar);
		this.ID = ID;
	}

	/**
	 * Lege constructor die gebruikt wordt in de beheer-leerling-controller om een nieuwe leerling aan te maken
	 * @author Johan Boogers
	 */
	public Leerling() {
		setID(0);
		quizDeelnames = new ArrayList<QuizDeelname>();
	}

	/**
	 * Haalt de waarde van het voornaam field van dit Leerling object op
	 *
	 * @return de String dit de voornaam van dit Leerling object voorstelt
	 */
	public String getLeerlingVoornaam() {
		return leerlingVoornaam;
	}

	/**
	 * Haalt de waarde van het familienaam field van dit Leerling object op
	 *
	 * @return de String dit de familienaam van dit Leerling object voorstelt
	 */
	public String getLeerlingFamilienaam() {
		return leerlingFamilienaam;
	}

	/**
	 * Haalt de waarde van het leerjaar field van dit Leerling object op
	 *
	 * @return een integer die het leerjaar waarin de leerling zit voorstelt
	 */
	public int getLeerjaar() {
		return leerjaar;
	}

	/**
	 * Geeft de volledige naam van dit Leerling object terug
	 *
	 * @return een String die de volledige naam van dit Leerling object
	 *         voorstelt
	 */
	public String getNaam() {
		return leerlingVoornaam + " " + leerlingFamilienaam;
	}

	/**
	 * Haalt de ID van de Leerling op
	 * 
	 * @return de ID van de Leerling
	 */
	public int getID() {
		return ID;
	}
	
	/**
	 * Method die door de container klasse gebruikt kan worden om de ID in te stellen
	 * 
	 * @param ID de gewenste ID
	 */
	protected void setID(int ID) {
		this.ID = ID;
	}

	/**
	 * Zet het leerjaar field van dit Leerling object. Accepteert de integers 1
	 * tot en met 6
	 *
	 * @param leerjaar
	 *            een integer van 1 tot en met 6 dat het leerjaar voorstelt
	 */
	public void setLeerjaar(int leerjaar) {
		if (leerjaar < 1 || leerjaar > 6) {
			throw new IllegalArgumentException(
					"Leerjaar moet een waarde aannemen van 1 tot en met 6");
		}
		this.leerjaar = leerjaar;
	}

	/**
	 * Zet het voornaam field van dit Leerling object
	 *
	 * @param voornaam
	 *            de String die de voornaam voorstelt
	 */
	public void setLeerlingVoornaam(String voornaam) {
		this.leerlingVoornaam = voornaam.trim();
	}

	/**
	 * Zet het familienaam field van dit Leerling object
	 *
	 * @param familienaam
	 *            de String die de familienaam voorstelt
	 */
	public void setLeerlingFamilienaam(String familienaam) {
		this.leerlingFamilienaam = familienaam.trim();
	}

	/**
	 * Registreren van een nieuwe Quizdeelname voor dit Leerling object
	 *
	 * @param quizDeelname
	 *            de QuizDeelname om toe te voegen
	 */
	protected void addQuizDeelname(QuizDeelname quizDeelname) {
		this.quizDeelnames.add(quizDeelname);
	}

	/**
	 * Verwijderen van een QuizDeelname gelinkt aan deze Leerling
	 *
	 * @param quizDeelname
	 *            de te verwijderen QuizDeelname
	 */
	protected void removeQuizDeelname(QuizDeelname quizDeelname) {
		this.quizDeelnames.remove(quizDeelname);
	}

	/**
	 * Geeft een kopie van de lijst van QuizDeelnames van deze leerling terug.
	 * De QuizDeelnames zijn geen clones omdat aan een QuizDeelname toch niets
	 * gewijzigd kan worden
	 *
	 * @return een ArrayList&lt;QuizDeelname&gt; dat een kopie is van de interne
	 *         lijst met QuizDeelnames geassocieerd met deze Leerling
	 */
	public ArrayList<QuizDeelname> getQuizDeelnames() {
		ArrayList<QuizDeelname> quizDeelnames = new ArrayList<QuizDeelname>();
		for (QuizDeelname quizDeelname : this.quizDeelnames) {
			quizDeelnames.add(quizDeelname);
		}
		return quizDeelnames;
	}

	/**
	 * Geeft een lijstje terug met kopies van de quizzen waaraan de Leerling
	 * reeds deelnam
	 *
	 * @return een ArrayList&lt;Quiz&gt; met kopies van de quizzen waaraan de
	 *         Leerling reeds deelnam
	 */
	public ArrayList<Quiz> getDeelgenomenQuizzen() {
		ArrayList<Quiz> deelgenomenAan = new ArrayList<Quiz>();
		for (QuizDeelname quizDeelname : this.quizDeelnames) {
			deelgenomenAan.add(quizDeelname.getQuiz().clone());
		}
		return deelgenomenAan;
	}

	/**
	 * Zoekt naar een QuizDeelname met een bepaalde ID. Geeft null terug als die
	 * voor deze Leerling niet bestaat.
	 * 
	 * @param quizDeelnameID
	 *            de gewenste ID
	 * @return de QuizDeelname met matchende ID. Null als die niet gevonden
	 *         wordt
	 */
	public QuizDeelname getQuizDeelname(String quizDeelnameID) {
		QuizDeelname toReturn = null;
		for (QuizDeelname quizDeelname : quizDeelnames) {
			if (quizDeelname.getID().equals(quizDeelnameID)) {
				toReturn = quizDeelname;
			}
		}
		return toReturn;
	}	
	
	/**
	 * Haalt een lijstje op van alle OpdrachtAntwoorden van deze Leerling
	 * 
	 * @return een ArrayList&lt;OpdrachtAntwoord&gt; van alle OpdrachtAntwoorden van deze Leerling
	 */
	public ArrayList<OpdrachtAntwoord> getAlleOpdrachtAntwoorden() {
		ArrayList<OpdrachtAntwoord> alleOpdrachtAntwoorden = new ArrayList<OpdrachtAntwoord>();
		for (QuizDeelname quizDeelname : quizDeelnames) {
			alleOpdrachtAntwoorden.addAll(quizDeelname.getOpdrachtAntwoorden());
		}
		return alleOpdrachtAntwoorden;
	}

	/**
	 * Geeft een String representatie terug van dit Leerling object van de vorm
	 * "Leerling [voornaam] [familienaam]"
	 */
	@Override
	public String toString() {
		return "Leerling [ID=" + ID + "] " + getNaam() + " uit leerjaar " + getLeerjaar();
	}

	/**
	 * Vergelijkt twee leerlingen op basis van hun familienaam. Hoofdletters of
	 * kleine letters spelen hierbij geen rol
	 *
	 * @param leerling
	 *            de Leerling waarmee dit Leerling object vergeleken wordt
	 */
	@Override
	public int compareTo(Leerling leerling) {
		return this.leerlingFamilienaam
				.compareToIgnoreCase(leerling.leerlingFamilienaam);
	}

	/**
	 * Controleert dit Leerling object met het meegegeven Object op gelijkheid.
	 * Twee leerling zijn gelijk wanneer ze een identieke naam hebben en in
	 * hetzelfde leerjaar zitten
	 *
	 * @param obj
	 *            het Object waarmee dit Leerling object vergeleken wordt
	 *
	 * @return true als dit Leerling object gelijk is aan het meegegeven Object
	 */
	@Override
	public boolean equals(Object obj) {

		if (obj == null) {
			return false;
		}
		if (obj.getClass() != Leerling.class) {
			return false;
		}

		Leerling leerling = (Leerling) obj;
		return this.getNaam().equals(leerling.getNaam())
				&& this.leerjaar == leerling.leerjaar;
	}

	@Override
	public int hashCode() {
		long hash = 1;
		hash = hash * 13 + leerjaar;
		hash = hash * 19 + getNaam().hashCode();
		hash %= Integer.MAX_VALUE;
		return (int) hash;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Leerling clone() {
		Leerling clone = null;
		try {
			clone = (Leerling) super.clone();
			clone.quizDeelnames = (ArrayList<QuizDeelname>) this.quizDeelnames
					.clone();
		} catch (CloneNotSupportedException ex) {
			// Leerling implementeert Cloneable, kan geen
			// CloneNotSupportedException throwen
			ex.printStackTrace();
		}
		return clone;
	}

	/**
	 * Controle of deze leerling verwijderd kan worden (enkel indien er geen deelnames zijn aan quizen)
	 * @author Johan Boogers
	 * @return ja, indien de leerling verwijderd kan worden (en geen deelnames meer heeft met een quiz)
	 */
	public boolean isVerwijderbaar() {
		try {
			if (this.getDeelgenomenQuizzen().size() == 0) {return true;}
			else { return false; }
		}
		catch (NullPointerException e) {
			e.printStackTrace();
			return true;
		}
	}

}
