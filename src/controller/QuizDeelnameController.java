package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
		quizDeelnameView.setVisible(false);
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
			keerTerugNaarKiezenQuiz();
		}
	}

	private void runOpdracht() {

		huidigeOpdrachtView = null;
		aantalPogingen = 0;
		verstrekenTijd = 0;
		laatsteAntwoord = "";
		huidigeHintIndex = 0;

		// Bepalen wat voor view er nodig is voor de volgende opdracht
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

		// View voorzien van informatie over huidige opdracht
		huidigeOpdrachtView.setQuizOnderwerp(quiz.getOnderwerp());
		huidigeOpdrachtView.setVraag(huidigeOpdracht.getVraag());
		huidigeOpdrachtView.setOpdrachtCategorie(huidigeOpdracht
				.getOpdrachtCategorie().toString());
		huidigeOpdrachtView.setVraagCounter(huidigeOpdrachtIndex);
		huidigeOpdrachtView.setVisible(true);
		huidigeOpdrachtView.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		// Als de opdrachtview gesloten wordt, moet er terug naar het quiz menu gegaan worden
		huidigeOpdrachtView.addClosedListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				  if (JOptionPane.showConfirmDialog(huidigeOpdrachtView, 
				            "Wil je deze quiz verlaten?", "Quiz beëindigen?", 
				            JOptionPane.YES_NO_OPTION,
				            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
				            keerTerugNaarKiezenQuiz();
				        } 
			}
		});
		
		// Een opdracht overslaan
		huidigeOpdrachtView.addVolgendeVraagKnopListener(ae -> {slaVraagOver();});

		// Tijd bijhouden in controller en om de 100 ms tijd aanpassen in view
		timer = new Timer(100, ae -> {
			verstrekenTijd += 100;
			huidigeOpdrachtView.setTijdOver(huidigeOpdracht
					.getMaxAntwoordTijd() * 1000 - verstrekenTijd);
		});
		
		// Als de opdracht op tijd is
		if (huidigeOpdracht.heeftTijdsbeperking()) {
			huidigeOpdrachtView
					.setMaxTijd(huidigeOpdracht.getMaxAntwoordTijd() * 1000);
			timer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {						
						if (verstrekenTijd >= huidigeOpdracht.getMaxAntwoordTijd() * 1000) {
							OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(
									quizDeelname, huidigeQuizOpdracht,
									aantalPogingen, verstrekenTijd / 1000,
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

		// Hintknop zichtbaar maken als er hints zijn en eventhandler aan de knop koppelen
		if (huidigeOpdracht.getHints().size() > 0) {
			huidigeOpdrachtView.useHint(true);
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
		}

		// Logica voor maximum aantal pogingen
		if (huidigeOpdracht.heeftPogingBeperking()) {
			huidigeOpdrachtView.setPogingen(huidigeOpdracht
					.getMaxAantalPogingen());
		} else {
			huidigeOpdrachtView.beperktePogingen(false);
		}		
		
		// Antwoordknop event handler
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
					huidigeQuizOpdracht, aantalPogingen, verstrekenTijd / 1000,
					huidigeOpdrachtView.getAntwoord());
			timer.stop();
			huidigeOpdrachtView.toonInformationDialog("Je antwoord was correct", "Juist!");
			volgendeOpdracht();
		} else {
			if (huidigeOpdracht.heeftPogingBeperking()
					&& aantalPogingen == huidigeOpdracht.getMaxAantalPogingen()) {
				OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(
						quizDeelname, huidigeQuizOpdracht, aantalPogingen,
						verstrekenTijd / 1000, huidigeOpdrachtView.getAntwoord());
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
	
	private void keerTerugNaarKiezenQuiz() {
		timer.stop();
		huidigeOpdrachtView.dispose();
		huidigeOpdrachtIndex = 0;
		quizDeelnameView.setVisible(true);
		quizDeelnameView.setQuizzen(dbHandler.getQuizCatalogus().getMogelijkeQuizzenVoor(leerling));
	}
	
	private void slaVraagOver() {
		OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(
				quizDeelname, huidigeQuizOpdracht, aantalPogingen,
				verstrekenTijd / 1000, huidigeOpdrachtView.getAntwoord());
		timer.stop();
		volgendeOpdracht();
	}

}
