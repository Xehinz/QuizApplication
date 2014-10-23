package model;

public class OpdrachtAntwoord {
	QuizDeelname quizDeelname;
	QuizOpdracht quizOpdracht;
	String laatsteAntwoord;
	int aantalPogingen;
	int antwoordTijd;
	
	public QuizDeelname getQuizDeelname() {
		return quizDeelname;
	}


	public QuizOpdracht getQuizOpdracht() {
		return quizOpdracht;
	}


	public String getLaatsteAntwoord() {
		return laatsteAntwoord;
	}


	public int getAantalPogingen() {
		return aantalPogingen;
	}


	public int getAntwoordTijd() {
		return antwoordTijd;
	}


	public OpdrachtAntwoord(QuizDeelname quizDeelname,
			QuizOpdracht quizOpdracht, int aantalPogingen, int antwoordTijd) {
		super();
		this.quizDeelname = quizDeelname;
		this.quizOpdracht = quizOpdracht;
		this.aantalPogingen = aantalPogingen;
		this.antwoordTijd = antwoordTijd;
	}
	
	public void giveAntwoord(String antwoord){
		this.laatsteAntwoord = antwoord;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + aantalPogingen;
		result = prime * result + antwoordTijd;
		result = prime * result
				+ ((laatsteAntwoord == null) ? 0 : laatsteAntwoord.hashCode());
		result = prime * result
				+ ((quizDeelname == null) ? 0 : quizDeelname.hashCode());
		result = prime * result
				+ ((quizOpdracht == null) ? 0 : quizOpdracht.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OpdrachtAntwoord other = (OpdrachtAntwoord) obj;
		if (aantalPogingen != other.aantalPogingen)
			return false;
		if (antwoordTijd != other.antwoordTijd)
			return false;
		if (laatsteAntwoord == null) {
			if (other.laatsteAntwoord != null)
				return false;
		} else if (!laatsteAntwoord.equals(other.laatsteAntwoord))
			return false;
		if (quizDeelname == null) {
			if (other.quizDeelname != null)
				return false;
		} else if (!quizDeelname.equals(other.quizDeelname))
			return false;
		if (quizOpdracht == null) {
			if (other.quizOpdracht != null)
				return false;
		} else if (!quizOpdracht.equals(other.quizOpdracht))
			return false;
		return true;
	}
	
	
	
}
