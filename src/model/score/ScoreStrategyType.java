package model.score;

public enum ScoreStrategyType {
	BASIS, TIJD;
	
	public String toString() {
		switch(this) {
		case BASIS:
			return "Basis";
		case TIJD:
			return "Tijd";
		default:
			return "";
		}
	}
}
