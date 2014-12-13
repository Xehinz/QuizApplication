package persistency;

import java.sql.SQLException;
import java.util.ArrayList;

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

class MySQLDB implements DBStrategy {

	private DBKlassiekeOpdrachtLeesSchrijf dbKlassiekeOpdrachtLeesSchrijf;
	private DBLeerlingLeesSchrijf dbLeerlingLeesSchrijf;
	private DBMeerkeuzeLeesSchrijf dbMeerkeuzeLeesSchrijf;
	private DBOpdrachtAntwoordLeesSchrijf dbOpdrachtAntwoordLeesSchrijf;
	private DBOpsommingLeesSchrijf dbOpsommingLeesSchrijf;
	private DBQuizDeelnameLeesSchrijf dbQuizDeelnameLeesSchrijf;
	private DBQuizLeesSchrijf dbQuizLeesSchrijf;
	private DBQuizOpdrachtLeesSchrijf dbQuizOpdrachtLeesSchrijf;
	private DBReproductieLeesSchrijf dbReproductieLeesSchrijf;
	
	public MySQLDB(String jdbcConnectionString) {
		this.dbKlassiekeOpdrachtLeesSchrijf = new DBKlassiekeOpdrachtLeesSchrijf(jdbcConnectionString);
		this.dbLeerlingLeesSchrijf = new DBLeerlingLeesSchrijf(jdbcConnectionString);
		this.dbMeerkeuzeLeesSchrijf = new DBMeerkeuzeLeesSchrijf(jdbcConnectionString);
		this.dbOpdrachtAntwoordLeesSchrijf = new DBOpdrachtAntwoordLeesSchrijf(jdbcConnectionString);
		this.dbOpsommingLeesSchrijf = new DBOpsommingLeesSchrijf(jdbcConnectionString);
		this.dbQuizDeelnameLeesSchrijf = new DBQuizDeelnameLeesSchrijf(jdbcConnectionString);
		this.dbQuizLeesSchrijf = new DBQuizLeesSchrijf(jdbcConnectionString);
		this.dbQuizOpdrachtLeesSchrijf = new DBQuizOpdrachtLeesSchrijf(jdbcConnectionString);
		this.dbReproductieLeesSchrijf = new DBReproductieLeesSchrijf(jdbcConnectionString);
	}

	@Override
	public ArrayList<Opdracht> leesOpdrachten() {
		try {
			ArrayList<Object> opdrachtObjecten = new ArrayList<Object>();
			opdrachtObjecten.addAll(dbKlassiekeOpdrachtLeesSchrijf.lees());
			opdrachtObjecten.addAll(dbReproductieLeesSchrijf.lees());
			opdrachtObjecten.addAll(dbMeerkeuzeLeesSchrijf.lees());
			opdrachtObjecten.addAll(dbOpsommingLeesSchrijf.lees());
			ArrayList<Opdracht> opdrachten = new ArrayList<Opdracht>();
			for (Object opdracht : opdrachtObjecten) {
				opdrachten.add((Opdracht) opdracht);
			}
			return opdrachten;
		}
		catch (SQLException e) {
			e.printStackTrace();			
			return new ArrayList<Opdracht>();
		}
	}

	@Override
	public ArrayList<Leerling> leesLeerlingen() {
		try {
			return dbLeerlingLeesSchrijf.lees();
		} 
		catch (SQLException e) {
			e.printStackTrace();			
			return new ArrayList<Leerling>();
		}	
	}

	@Override
	public ArrayList<Quiz> leesQuizzen() {
		try {
			return dbQuizLeesSchrijf.lees();
		} 
		catch (SQLException e) {
			e.printStackTrace();			
			return new ArrayList<Quiz>();
		}	
	}

	@Override
	public ArrayList<PseudoQuizDeelname> leesQuizDeelnames() {
		try {
			return dbQuizDeelnameLeesSchrijf.lees();
		} 
		catch (SQLException e) {
			e.printStackTrace();			
			return new ArrayList<PseudoQuizDeelname>();
		}	
	}

	@Override
	public ArrayList<PseudoQuizOpdracht> leesQuizOpdrachten() {
		try {
			return dbQuizOpdrachtLeesSchrijf.lees();
		} 
		catch (SQLException e) {
			e.printStackTrace();			
			return new ArrayList<PseudoQuizOpdracht>();
		}	
	}

	@Override
	public ArrayList<PseudoOpdrachtAntwoord> leesOpdrachtAntwoorden() {
		try {
			return dbOpdrachtAntwoordLeesSchrijf.lees();
		} 
		catch (SQLException e) {
			e.printStackTrace();			
			return new ArrayList<PseudoOpdrachtAntwoord>();
		}	
	}

	@Override
	public void schrijfOpdrachten(ArrayList<Opdracht> opdrachten) {
		try {
			ArrayList<Opdracht> klassiekeopdrachten = new ArrayList<Opdracht>();
			ArrayList<Opdracht> meerkeuzeopdrachten = new ArrayList<Opdracht>();
			ArrayList<Opdracht> opsommingsopdrachten = new ArrayList<Opdracht>();
			ArrayList<Opdracht> reproductieopdrachten = new ArrayList<Opdracht>();
			for (Opdracht opdracht : opdrachten) {
				if (opdracht instanceof KlassiekeOpdracht) {
					klassiekeopdrachten.add(opdracht);
				}
				if (opdracht instanceof Meerkeuze) {
					meerkeuzeopdrachten.add(opdracht);
				}
				if (opdracht instanceof Opsomming) {
					opsommingsopdrachten.add(opdracht);
				}
				if (opdracht instanceof Reproductie) {
					reproductieopdrachten.add(opdracht);
				}
			}
			dbKlassiekeOpdrachtLeesSchrijf.schrijf(klassiekeopdrachten);
			dbMeerkeuzeLeesSchrijf.schrijf(meerkeuzeopdrachten);
			dbOpsommingLeesSchrijf.schrijf(opsommingsopdrachten);
			dbReproductieLeesSchrijf.schrijf(reproductieopdrachten);
		}
		catch (SQLException e) {
			e.printStackTrace();			
		}
	}

	@Override
	public void schrijfLeerlingen(ArrayList<Leerling> leerlingen) {
		try {
			dbLeerlingLeesSchrijf.schrijf(leerlingen);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void schrijfQuizzen(ArrayList<Quiz> quizzen) {
		try {
			dbQuizLeesSchrijf.schrijf(quizzen);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void schrijfQuizDeelnames(ArrayList<QuizDeelname> quizDeelnames) {
		try {
			dbQuizDeelnameLeesSchrijf.schrijf(quizDeelnames);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void schrijfQuizOpdrachten(ArrayList<QuizOpdracht> quizOpdrachten) {
		try {
			dbQuizOpdrachtLeesSchrijf.schrijf(quizOpdrachten);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void schrijfOpdrachtAntwoorden(
			ArrayList<OpdrachtAntwoord> opdrachtAntwoorden) {
		try {
			dbOpdrachtAntwoordLeesSchrijf.schrijf(opdrachtAntwoorden);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
