package model.score;

import model.Opdracht;
import model.OpdrachtAntwoord;

/**
 * Deze scoreberekeningsstrategie houdt rekening met het aantal
 * antwoordpogingen.</br></br>
 * 
 * Als het antwoord fout is, krijgt de leerling 0 <br/>
 * Als het antwoord juist is, en de leerling heeft maar 1 keer geprobeerd,
 * krijgt de leerling de maximum score <br/>
 * Als het antwoord juist is, maar de leerling heeft meerdere keren geprobeerd,
 * wordt er per extra poging een malus toegekend van 10% van de maximum score.
 * De score kan niet onder 0 komen.
 * 
 * @author Ben Vandenberk
 *
 */
public class PogingScoreStrategy implements ScoreStrategy {

	@Override
	public double berekenScore(OpdrachtAntwoord opdrachtAntwoord) {

		Opdracht opdracht = opdrachtAntwoord.getOpdracht();
		String laatsteAntwoord = opdrachtAntwoord.getLaatsteAntwoord();
		int maxScore = opdrachtAntwoord.getQuizOpdracht().getMaxScore();
		int aantalGebruiktePogingen = opdrachtAntwoord.getAantalPogingen();

		if (!opdracht.isJuisteAntwoord(laatsteAntwoord)) {
			return 0.0;
		}

		if (aantalGebruiktePogingen == 1) {
			return maxScore;
		}

		return maxScore - ((aantalGebruiktePogingen - 1) * 0.10 * maxScore) >= 0.0 ? 
				maxScore - ((aantalGebruiktePogingen - 1) * 0.10 * maxScore)
				: 0.0;
	}

}
