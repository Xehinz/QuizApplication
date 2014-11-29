package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.*;

import model.KlassiekeOpdracht;
import model.Leraar;
import model.Meerkeuze;
import model.Opdracht;
import model.OpdrachtCategorie;
import model.Opsomming;
import model.Reproductie;
import persistency.DBHandler;
import view.OpdrachtAanpassingView;
import view.OpdrachtBeheerView;

public class OpdrachtBeheerController {

	private DBHandler dbHandler;
	private OpdrachtBeheerView view;
	private Opdracht opdracht;
	private Leraar leraar;

	public OpdrachtBeheerController(DBHandler dbHandler, Leraar leraar,
			OpdrachtBeheerView view) {
		this.setDBHandler(dbHandler);
		this.leraar = leraar;
		this.view = view;
		this.opdracht = null;

		view.setOpdrachten(dbHandler.getOpdrachtCatalogus().getOpdrachten());

		view.addNieuweKlassiekeKnopActionListener(new NieuweKlassiekeKnopListener());
		view.addNieuweMeerkeuzeKnopActionListener(new NieuweMeerkeuzeKnopListener());
		view.addNieuweOpsommingKnopActionListener(new NieuweOpsommingKnopListener());
		view.addNieuweReproductieKnopActionListener(new NieuweReproductieKnopListener());
		view.addPasOpdrachtAanKnopActionListener(new PasOpdrachtAanKnopListener());
		view.addVerwijderOpdrachtKnopActionListener(new VerwijderOpdrachtKnopListener());
		view.addBekijkDetailsKnopActionListener(new BekijkDetailsKnopListener());
		view.addSelecteerCategorieActionlistener(new SelecteerCategorieListener());

		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.setVisible(true);
	}

	public DBHandler getDBHandler() {
		return dbHandler;
	}

	private void setDBHandler(DBHandler dbHandler) {
		this.dbHandler = dbHandler;
	}

	private void openOpdrachtAanpassing(Opdracht opdracht, Leraar leraar) {
		new OpdrachtAanpassingController(opdracht, leraar, dbHandler);
	}

	class NieuweKlassiekeKnopListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			opdracht = new KlassiekeOpdracht(null, leraar);
			openOpdrachtAanpassing(opdracht, leraar);
		}
	}

	class NieuweMeerkeuzeKnopListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			opdracht = new Meerkeuze(null, leraar);
			openOpdrachtAanpassing(opdracht, leraar);
		}
	}

	class NieuweOpsommingKnopListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			opdracht = new Opsomming(null, leraar);
			openOpdrachtAanpassing(opdracht, leraar);
		}
	}

	class NieuweReproductieKnopListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			opdracht = new Reproductie(null, leraar);
			openOpdrachtAanpassing(opdracht, leraar);
		}
	}

	class PasOpdrachtAanKnopListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			opdracht = view.getGeselecteerdeOpdracht();
			if (opdracht == null) {
				return;
			}
			if (!opdracht.isAanpasbaar()) {
				return;
			} else {
				openOpdrachtAanpassing(opdracht, opdracht.getAuteur());
			}
		}
	}

	class VerwijderOpdrachtKnopListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			opdracht = view.getGeselecteerdeOpdracht();
			if (opdracht == null) {
				return;
			}
			if (!opdracht.isVerwijderbaar()) {
				return;
			} else {
				dbHandler.getOpdrachtCatalogus().removeOpdracht(opdracht);
				view.setOpdrachten(dbHandler.getOpdrachtCatalogus().getOpdrachten());
			}
		}
	}

	class BekijkDetailsKnopListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			opdracht = view.getGeselecteerdeOpdracht();
			if (opdracht == null) {
				return;
			}
			openOpdrachtAanpassing(opdracht, opdracht.getAuteur());
		}
	}
	
	class SelecteerCategorieListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event){
			OpdrachtCategorie OC = view.getOpdrachtCategorie();
			view.setOpdrachten(dbHandler.getOpdrachtCatalogus().getOpdrachten(OC));
		}
	}

	public static void main(String[] args) {
		OpdrachtBeheerView OBV = new OpdrachtBeheerView();
		DBHandler dbHandler = new DBHandler();
		Leraar leraar = Leraar.MIEKE_WITTEMANS;
		OpdrachtBeheerController OBC = new OpdrachtBeheerController(dbHandler,
				leraar, OBV);
		OBV.setVisible(true);
	}
}
