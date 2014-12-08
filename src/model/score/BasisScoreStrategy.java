package model.score;

import model.OpdrachtAntwoord;
import model.QuizOpdracht;

/**
 * Uitwerking van het scoreberekeningsalgoritme op pagina 5 van de opgave
 * 
 * @author Ben Vandenberk
 */
public class BasisScoreStrategy implements ScoreStrategy {

	@Override
	public double berekenScore(OpdrachtAntwoord opdrachtAntwoord) {
		
		QuizOpdracht quizOpdracht = opdrachtAntwoord.getQuizOpdracht();
		String laatsteAntwoord = opdrachtAntwoord.getLaatsteAntwoord();
		int antwoordTijd = opdrachtAntwoord.getAntwoordTijd();
		int aantalPogingen = opdrachtAntwoord.getAantalPogingen();

		if (!quizOpdracht.getOpdracht().isJuisteAntwoord(laatsteAntwoord)) { // FOUT
																				// ANTWOORD
			return 0.0;
		} else { // JUIST ANTWOORD
			if (quizOpdracht.getOpdracht().heeftTijdsbeperking()
					&& antwoordTijd > quizOpdracht.getOpdracht()
							.getMaxAntwoordTijd()) { // TE VEEL TIJD GEBRUIKT
				return 0.0;
			} else { // TIJD OKE
				if (aantalPogingen == 1) {
					return quizOpdracht.getMaxScore();
				} else {
					if (!quizOpdracht.getOpdracht().heeftPogingBeperking()) { // ONBEPERKT
																				// AANTAL
																				// POGINGEN
						return quizOpdracht.getMaxScore() / 2.0;
					} else { // BEPERKT AANTAL POGINGEN
						return aantalPogingen <= quizOpdracht.getOpdracht()
								.getMaxAantalPogingen() ? quizOpdracht
								.getMaxScore() / 2.0 : 0.0;
					}
				}
			}
		}
	}

}
