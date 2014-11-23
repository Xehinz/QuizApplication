package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

import persistency.DBHandler;
import view.DeelnameKlassiekOpsommingView;
import view.DeelnameMeerkeuzeView;
import view.DeelnameReproductieView;
import view.QuizDeelnameView;
import model.KlassiekeOpdracht;
import model.Leerling;
import model.Meerkeuze;
import model.Opdracht;
import model.OpdrachtAntwoord;
import model.Opsomming;
import model.Quiz;
import model.QuizDeelname;
import model.QuizOpdracht;
import model.Reproductie;
import model.Valideerbaar;

public class QuizDeelnameController {

	private DBHandler dbHandler;
	
	private Quiz quiz;
	private final Leerling leerling;
	private QuizDeelnameView quizDeelnameView;

	private Opdracht huidigeOpdracht;
	private QuizOpdracht huidigeQuizOpdracht;
	private DeelnameKlassiekOpsommingView huidigeOpdrachtView;
	private QuizDeelname quizDeelname;
	private int huidigeOpdrachtIndex, huidigeHintIndex;
	private int aantalPogingen;
	private int verstrekenTijd;
	private String laatsteAntwoord;

	private Timer timer;

	public QuizDeelnameController(DBHandler dbHandler, Leerling leerling,
			QuizDeelnameView quizDeelnameView) {
		
		this.dbHandler = dbHandler;
		this.leerling = leerling;
		this.quizDeelnameView = quizDeelnameView;

		huidigeOpdrachtIndex = 0;
		aantalPogingen = 0;
		verstrekenTijd = 0;
		huidigeHintIndex = 0;
		huidigeOpdracht = null;
		huidigeOpdrachtView = null;
		quiz = null;
		quizDeelname = null;
		huidigeQuizOpdracht = null;
		laatsteAntwoord = "";
		timer = null;

		quizDeelnameView.setQuizzen(dbHandler.getQuizCatalogus().getMogelijkeQuizzenVoor(leerling));
		quizDeelnameView.setLeerling(leerling);
		quizDeelnameView.addDeelneemKnopListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				quiz = quizDeelnameView.getGeselecteerdeQuiz();
				if (quiz == null) {
					quizDeelnameView.toonInformationDialog(
							"Selecteer een quiz om er aan deel te nemen",
							"Deelnemen");
					return;
				}
				neemDeel();
			}
		});

		quizDeelnameView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		quizDeelnameView.setVisible(true);
	}

	private void neemDeel() {
		quizDeelnameView.dispose();

		quizDeelname = QuizDeelname.koppelQuizAanLeerling(quiz, leerling);

		volgendeOpdracht();
	}

	private void volgendeOpdracht() {		
		if (huidigeOpdrachtView != null) {
			huidigeOpdrachtView.dispose();			
		}
		if (huidigeOpdrachtIndex < quiz.getOpdrachten().size()) {
			huidigeOpdracht = quiz.getOpdrachten().get(huidigeOpdrachtIndex++);
			huidigeQuizOpdracht = quiz.getQuizOpdracht(huidigeOpdracht);
			runOpdracht();
		} else {
			System.exit(0);
		}
	}

	private void runOpdracht() {

		huidigeOpdrachtView = null;
		aantalPogingen = 0;
		verstrekenTijd = 0;
		laatsteAntwoord = "";
		huidigeHintIndex = 0;

		if (huidigeOpdracht instanceof KlassiekeOpdracht) {
			huidigeOpdrachtView = new DeelnameKlassiekOpsommingView();
		} else if (huidigeOpdracht instanceof Opsomming) {
			huidigeOpdrachtView = new DeelnameKlassiekOpsommingView();
			huidigeOpdrachtView
					.setAntwoordVeldToolTip("Scheid je antwoorden met ;");
		} else if (huidigeOpdracht instanceof Meerkeuze) {
			Meerkeuze meerkeuze = (Meerkeuze) huidigeOpdracht;
			huidigeOpdrachtView = new DeelnameMeerkeuzeView(meerkeuze
					.getOpties().toArray(new String[0]));
		} else if (huidigeOpdracht instanceof Reproductie) {
			huidigeOpdrachtView = new DeelnameReproductieView();
		}

		huidigeOpdrachtView.setQuizOnderwerp(quiz.getOnderwerp());
		huidigeOpdrachtView.setVraag(huidigeOpdracht.getVraag());
		huidigeOpdrachtView.setOpdrachtCategorie(huidigeOpdracht
				.getOpdrachtCategorie().toString());
		huidigeOpdrachtView.setVraagCounter(huidigeOpdrachtIndex);
		huidigeOpdrachtView.setVisible(true);

		timer = new Timer(100, ae -> {
			verstrekenTijd += 100;
			huidigeOpdrachtView.setTijdOver(huidigeOpdracht
					.getMaxAntwoordTijd() * 1000 - verstrekenTijd);
		});
		
		if (huidigeOpdracht.heeftTijdsbeperking()) {
			huidigeOpdrachtView
					.setMaxTijd(huidigeOpdracht.getMaxAntwoordTijd() * 1000);
			timer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
						verstrekenTijd += 100;
						if (verstrekenTijd >= huidigeOpdracht.getMaxAntwoordTijd() * 1000) {
							OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(
									quizDeelname, huidigeQuizOpdracht,
									aantalPogingen, verstrekenTijd,
									laatsteAntwoord);
							huidigeOpdrachtView.toonInformationDialog(
									"Je tijd is verstreken", "Tijd op");
							timer.stop();
							volgendeOpdracht();
						}
					}
				});
			}
		else {
			huidigeOpdrachtView.opTijd(false);
		}
		
		timer.start();

		huidigeOpdrachtView.useHint(huidigeOpdracht.getHints().size() > 0);
		huidigeOpdrachtView.addHintKnopListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (huidigeHintIndex < huidigeOpdracht.getHints().size()) {
					huidigeOpdrachtView.toonInformationDialog(huidigeOpdracht
							.getHints().get(huidigeHintIndex++), "Hint");
					if (huidigeHintIndex == huidigeOpdracht.getHints().size()) {
						huidigeOpdrachtView.disableHint();
					}
				}
			}
		});

		if (huidigeOpdracht.heeftPogingBeperking()) {
			huidigeOpdrachtView.setPogingen(huidigeOpdracht
					.getMaxAantalPogingen());
		} else {
			huidigeOpdrachtView.beperktePogingen(false);
		}		
		
		huidigeOpdrachtView.addAntwoordKnopListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (valideerAntwoord()) {
					antwoord();
				} else {
					Valideerbaar valideerbareOpdracht = (Valideerbaar) huidigeOpdracht;
					huidigeOpdrachtView.toonInformationDialog(
							"Antwoord is niet van juiste formaat:\n"
									+ valideerbareOpdracht.getValideerTekst(),
							"Opgelet");
				}
			}
		});

	}

	private boolean valideerAntwoord() {
		if (huidigeOpdracht instanceof Valideerbaar) {
			Valideerbaar valideerbareOpdracht = (Valideerbaar) huidigeOpdracht;
			return valideerbareOpdracht.isValide(huidigeOpdrachtView
					.getAntwoord());
		} else {
			return true;
		}
	}

	private void antwoord() {
		aantalPogingen++;
		if (huidigeOpdracht.heeftPogingBeperking()) {
			huidigeOpdrachtView.setPogingen(huidigeOpdracht
					.getMaxAantalPogingen() - aantalPogingen);
		}

		if (huidigeOpdracht.isJuisteAntwoord(huidigeOpdrachtView.getAntwoord())) {
			OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(quizDeelname,
					huidigeQuizOpdracht, aantalPogingen, verstrekenTijd,
					huidigeOpdrachtView.getAntwoord());
			timer.stop();
			huidigeOpdrachtView.toonInformationDialog("Je antwoord was correct", "Juist!");
			volgendeOpdracht();
		} else {
			if (huidigeOpdracht.heeftPogingBeperking()
					&& aantalPogingen == huidigeOpdracht.getMaxAantalPogingen()) {
				OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(
						quizDeelname, huidigeQuizOpdracht, aantalPogingen,
						verstrekenTijd, huidigeOpdrachtView.getAntwoord());
				timer.stop();
				huidigeOpdrachtView
						.toonInformationDialog(
								"Spijtig, dat was niet het juiste antwoord. Ga verder met de volgende vraag.",
								"Pogingen op");				
				volgendeOpdracht();
			} else {
				laatsteAntwoord = huidigeOpdrachtView.getAntwoord();
				huidigeOpdrachtView.toonInformationDialog(
						"Fout antwoord, probeer nog eens", "Oeps");
			}
		}
	}

}
