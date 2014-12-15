package model.score;

public class OpdrachtScoreRegelsFactory {
	
	private static OpdrachtScoreRegelsFactory enigeInstantie;
	private ScoreStrategyType scoreStrategyType;
	
	private OpdrachtScoreRegelsFactory() {
		this.scoreStrategyType = ScoreStrategyType.BASIS;
	}
	
	public static OpdrachtScoreRegelsFactory getEnigeInstantie() {
		if (enigeInstantie == null) {
			enigeInstantie = new OpdrachtScoreRegelsFactory();
		}
		return enigeInstantie;
	}
	
	public void setScoreStrategyType(ScoreStrategyType scoreStrategyType) {
		this.scoreStrategyType = scoreStrategyType;
	}
	
	public ScoreStrategy maakScoreStrategy() {
		switch (scoreStrategyType) {
		case BASIS:
			return new BasisScoreStrategy();
		case TIJD:
			return new TijdScoreStrategy();
		case POGINGEN:
			return new PogingScoreStrategy();
		default:
			return null;
		}
	}
}
