package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.AbstractTableModel;

import persistency.DBHandler;
import view.viewInterfaces.IOpdrachtDeelnameView;
import view.viewInterfaces.IOpdrachtDeelnameViewFactory;
import view.viewInterfaces.IQuizDeelnameView;
import model.Leerling;
import model.Meerkeuze;
import model.Opdracht;
import model.OpdrachtAntwoord;
import model.Quiz;
import model.QuizDeelname;
import model.QuizOpdracht;
import model.Valideerbaar;


/**
 * 
 * @author Ben Vandenberk
 *
 */public class QuizDeelnameController {

	private DBHandler dbHandler;
	private final Leerling leerling;
	private IQuizDeelnameView quizDeelnameView;
	private IOpdrachtDeelnameViewFactory opdrachtDeelnameViewFactory;
	
	private Quiz quiz;
	private QuizTableModel quizTableModel;

	private Opdracht huidigeOpdracht;
	private QuizOpdracht huidigeQuizOpdracht;
	private IOpdrachtDeelnameView huidigeOpdrachtView;
	private QuizDeelname quizDeelname;
	private int huidigeOpdrachtIndex, huidigeHintIndex;
	private int aantalPogingen;
	private int verstrekenTijd;
	private String laatsteAntwoord;

	private Timer timer;

	public QuizDeelnameController(DBHandler dbHandler, Leerling leerling,
			IQuizDeelnameView quizDeelnameView, IOpdrachtDeelnameViewFactory opdrachtDeelnameViewFactory) {
		
		this.dbHandler = dbHandler;
		this.leerling = leerling;
		this.quizDeelnameView = quizDeelnameView;
		this.opdrachtDeelnameViewFactory = opdrachtDeelnameViewFactory;

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

		quizTableModel = new QuizTableModel(dbHandler.getQuizCatalogus().getMogelijkeQuizzenVoor(leerling));
		quizDeelnameView.setTableModel(quizTableModel);
		quizDeelnameView.setLeerling(leerling.getNaam());
		quizDeelnameView.addDeelneemKnopListener(new DeelneemKnopListener());
		
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

	    huidigeOpdrachtView = opdrachtDeelnameViewFactory.maakOpdrachtDeelnameView(huidigeOpdracht.getClass().getSimpleName());

		// View voorzien van informatie over huidige opdracht
		huidigeOpdrachtView.setQuizOnderwerp(quiz.getOnderwerp());
		huidigeOpdrachtView.setVraag(huidigeOpdracht.getVraag());
		huidigeOpdrachtView.setOpdrachtCategorie(huidigeOpdracht
				.getOpdrachtCategorie().toString());
		huidigeOpdrachtView.setVraagCounter(huidigeOpdrachtIndex);	
		
		// Alleen een Meerkeuze opdracht heeft opties nodig
		if (huidigeOpdracht instanceof Meerkeuze) {
			huidigeOpdrachtView.setAntwoordKeuzes(((Meerkeuze)huidigeOpdracht).getOpties().toArray(new String[0]));
		}
		
		// Als de opdrachtview gesloten wordt, moet er terug naar het quiz menu gegaan worden
		huidigeOpdrachtView.addClosedListener(new OpdrachtSchermpjeSluitHandler());
		
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
			timer.addActionListener(new TimerTickListener());
			}
		else {
			huidigeOpdrachtView.opTijd(false);
		}		

		// Hintknop zichtbaar maken als er hints zijn en eventhandler aan de knop koppelen
		if (huidigeOpdracht.getHints().size() > 0) {
			huidigeOpdrachtView.useHint(true);
			huidigeOpdrachtView.addHintKnopListener(new HintKnopListener());
		} else {
			huidigeOpdrachtView.disableHint();
		}

		// Logica voor maximum aantal pogingen
		if (huidigeOpdracht.heeftPogingBeperking()) {
			huidigeOpdrachtView.setPogingen(huidigeOpdracht
					.getMaxAantalPogingen());
		} else {
			huidigeOpdrachtView.beperktePogingen(false);
		}		
		
		// Antwoordknop event handler
		huidigeOpdrachtView.addAntwoordKnopListener(new AntwoordKnopListener());	
		
		huidigeOpdrachtView.setVisible(true);
		huidigeOpdrachtView.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		timer.start();
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
		quizTableModel = new QuizTableModel(dbHandler.getQuizCatalogus().getMogelijkeQuizzenVoor(leerling));
		quizDeelnameView.setTableModel(quizTableModel);		
	}
	
	private void slaVraagOver() {
		OpdrachtAntwoord.koppelQuizDeelnameAanQuizOpdracht(
				quizDeelname, huidigeQuizOpdracht, aantalPogingen,
				verstrekenTijd / 1000, huidigeOpdrachtView.getAntwoord());
		timer.stop();
		volgendeOpdracht();
	}
	
	class DeelneemKnopListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent event) {
			quiz = quizTableModel.getQuiz(quizDeelnameView.getGeselecteerdeRij());
			if (quiz == null) {
				quizDeelnameView.toonInformationDialog(
						"Selecteer een quiz om er aan deel te nemen",
						"Deelnemen");
				return;
			}
			neemDeel();
		}
		
	}
	
	class AntwoordKnopListener implements ActionListener {
		
		@Override
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
		
	}
	
	class HintKnopListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent event) {
			if (huidigeHintIndex < huidigeOpdracht.getHints().size()) {
				huidigeOpdrachtView.toonInformationDialog(huidigeOpdracht
						.getHints().get(huidigeHintIndex++), "Hint");
				if (huidigeHintIndex == huidigeOpdracht.getHints().size()) {
					huidigeOpdrachtView.disableHint();
				}
			}
		}
		
	}
	
	class TimerTickListener implements ActionListener {
		
		@Override
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
		
	}
	
	class OpdrachtSchermpjeSluitHandler extends WindowAdapter {
		
		@Override
		public void windowClosing(WindowEvent event) {
			  if (JOptionPane.showConfirmDialog(null, 
			            "Wil je deze quiz verlaten?", "Quiz beëindigen?", 
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
			            keerTerugNaarKiezenQuiz();
			        } 
		}
		
	}
	
	@SuppressWarnings("serial")
	class QuizTableModel extends AbstractTableModel {

		private String[] headers;
		private ArrayList<Quiz> quizzen;
		
		public QuizTableModel() {
			super();
			headers = new String[] {"Onderwerp", "Leraar", "Aantal deelnames", "Test?"};
			quizzen = new ArrayList<Quiz>();
		}
		
		public QuizTableModel(Collection<Quiz> quizzen) {
			this();
			this.quizzen = new ArrayList<Quiz>(quizzen);			
		}
		
		public Quiz getQuiz(int row) {
			if (row < quizzen.size() && row >= 0) {
				return quizzen.get(row);
			}
			return null;
		}
		
		public void setQuizzen(Collection<Quiz> quizzen) {
			this.quizzen = new ArrayList<Quiz>(quizzen);
		}
		
		@Override   
		public String getColumnName(int col) {
		        return headers[col];
		    }
		
		@Override
		public int getColumnCount() {
			return 4;
		}

		@Override
		public int getRowCount() {
			return quizzen.size();
		}
		
	    @Override
	    public Class<?> getColumnClass(int columnIndex) {
	        if (quizzen.isEmpty()) {
	            return Object.class;
	        }
	        return getValueAt(0, columnIndex).getClass();
	    }

		@Override
		public Object getValueAt(int row, int col) {
			Quiz quiz = quizzen.get(row);
			
			switch (col) {
				case 0: 
					return quiz.getOnderwerp();
				case 1:
					return quiz.getAuteur().toString();
				case 2:
					return quiz.getIsUniekeDeelname() ? "1" : "Meerdere deelnames";
				case 3:
					return quiz.getIsTest() ? "Test" : "Geen test";
			}
			
			return "";
		}		
	}

}
