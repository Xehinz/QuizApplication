package model.score;

public enum ScoreStrategyType {
	BASIS, TIJD, POGINGEN;
	
	public String toString() {
		switch(this) {
		case BASIS:
			return "Basis";
		case TIJD:
			return "Tijd";
		case POGINGEN:
			return "Pogingen";
		default:
			return "";
		}
	}
}
