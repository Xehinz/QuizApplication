package model.score;

import model.OpdrachtAntwoord;

public interface ScoreStrategy {
	
	/**
	 * Berekent de behaalde score voor het gegeven antwoord
	 * 
	 * @param opdrachtAntwoord het OpdrachtAntwoord
	 * @return de behaalde score (double)
	 */
	public double berekenScore(OpdrachtAntwoord opdrachtAntwoord);
	
}
