package model.quizStatus;

public interface QuizStatus {
	
	public abstract boolean isAanpasbaar();
	
	public abstract boolean isVerwijderbaar();
	
	public abstract boolean isDeelnameMogelijk();
	
	public static QuizStatus getInstance(String status) {
		switch (status) {
		case "Afgesloten":
			return new Afgesloten();
		case "Afgewerkt":
			return new Afgewerkt();
		case "In constructie":
			return new InConstructie();
		case "Laatste kans":
			return new LaatsteKans();
		case "Opengesteld":
			return new Opengesteld();
		}
		return null;
	}

}
