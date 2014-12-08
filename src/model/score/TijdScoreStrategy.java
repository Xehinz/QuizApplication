package model.score;

import model.Opdracht;
import model.OpdrachtAntwoord;

/**
 * Deze scoreberekeningsstrategie houdt rekening met de antwoordtijd.</br></br>
 * 
 * Als het antwoord fout is, krijgt de leerling 0 </br>
 * Als het antwoord juist is en er geldt geen tijdsbeperking voor de opdracht, krijgt de leerling de
 * maximumscore</br>
 * Als het antwoord juist is en er geldt een tijdsbeperking, maar
 * de leerling heeft minder dan 30% van de voorziene tijd gebruikt, krijgt de
 * leerling de maximumscore</br>
 * Als het antwoord juist is, er geldt een
 * tijdsbeperking en de leerling heeft meer dan 30% van de voorziene tijd
 * gebruikt, wordt de score bepaald door de volgende formule:</br></br>
 * 
 * Neem maxScore = maximum te behalen score, GT = gebruikte tijd en MT =
 * maximaal toegelaten antwoordtijd, dan</br></br>
 * 
 * score = maxScore - maxScore * (GT - 0,3MT) / (0,7MT) * 0,5</br></br>
 * 
 * Voorbeeld: voor maxScore = 10, MT = 10 en GT = 9 levert dit een score op van
 * 5,71
 * 
 * @author Ben Vandenberk
 *
 */
public class TijdScoreStrategy implements ScoreStrategy {

	@Override
	public double berekenScore(OpdrachtAntwoord opdrachtAntwoord) {

		Opdracht opdracht = opdrachtAntwoord.getOpdracht();
		String laatsteAntwoord = opdrachtAntwoord.getLaatsteAntwoord();
		int maxScore = opdrachtAntwoord.getQuizOpdracht().getMaxScore();
		int gebruikteTijd = opdrachtAntwoord.getAntwoordTijd();
		int maxTijd = opdracht.getMaxAntwoordTijd();

		if (!opdracht.isJuisteAntwoord(laatsteAntwoord)) {
			return 0.0;
		}

		if (!opdracht.heeftTijdsbeperking()) {
			return maxScore;
		} else {
			if (gebruikteTijd > maxTijd) {
				return 0.0;
			}
		}

		if (opdracht.heeftPogingBeperking()
				&& opdrachtAntwoord.getAantalPogingen() > opdracht
						.getMaxAantalPogingen()) {
			return 0.0;
		}

		if (gebruikteTijd < 0.3 * maxTijd) {
			return maxScore;
		}

		return maxScore - maxScore * (gebruikteTijd - 0.3 * maxTijd)
				/ (0.7 * maxTijd) * 0.5;
	}

}
