package controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
		view.setOpdrachtCategorie();

		view.setListCellRenderer(new OpdrachtListCellRenderer());
		view.addNieuweKlassiekeKnopActionListener(new NieuweKlassiekeKnopListener());
		view.addNieuweMeerkeuzeKnopActionListener(new NieuweMeerkeuzeKnopListener());
		view.addNieuweOpsommingKnopActionListener(new NieuweOpsommingKnopListener());
		view.addNieuweReproductieKnopActionListener(new NieuweReproductieKnopListener());
		view.addPasOpdrachtAanKnopActionListener(new PasOpdrachtAanKnopListener());
		view.addVerwijderOpdrachtKnopActionListener(new VerwijderOpdrachtKnopListener());
		view.addBekijkDetailsKnopActionListener(new BekijkDetailsKnopListener());
		view.addSelecteerCategorieActionlistener(new SelecteerCategorieListener());
		view.addListSelectionListener(new OpdrachtGeselecteerd());
		view.setVisible(true);
	}

	public DBHandler getDBHandler() {
		return dbHandler;
	}

	private void setDBHandler(DBHandler dbHandler) {
		this.dbHandler = dbHandler;
	}

	public OpdrachtBeheerView getView() {
		return this.view;
	}

	private void openOpdrachtAanpassing(Opdracht opdracht, Leraar leraar) {
		new OpdrachtAanpassingController(opdracht, leraar, dbHandler, this);
	}

	private void openOpdrachtBekijken(Opdracht opdracht, Leraar leraar) {
		OpdrachtAanpassingController OAC = new OpdrachtAanpassingController(
				opdracht, leraar, dbHandler, this);
		OAC.getView().disableAanpassen();
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
			if (opdracht.equals(null)) {
				return;
			}
			if (!opdracht.isVerwijderbaar()) {
				return;
			} else {
				dbHandler.getOpdrachtCatalogus().removeOpdracht(opdracht);
				view.setOpdrachten(dbHandler.getOpdrachtCatalogus()
						.getOpdrachten());
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
			openOpdrachtBekijken(opdracht, opdracht.getAuteur());
		}
	}

	class SelecteerCategorieListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			String opdrachtCategorieString = view.getOpdrachtCategorie();
			if (opdrachtCategorieString.equals("Alle categorieŽn")) {
				view.setOpdrachten(dbHandler.getOpdrachtCatalogus()
						.getOpdrachten());
			} else {
				OpdrachtCategorie OC = OpdrachtCategorie
						.valueOf(opdrachtCategorieString.toUpperCase());
				view.setOpdrachten(dbHandler.getOpdrachtCatalogus()
						.getOpdrachten(OC));
			}
		}
	}

	class OpdrachtGeselecteerd implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			Opdracht O = view.getGeselecteerdeOpdracht();
			//opdracht = view.getGeselecteerdeOpdracht();
			//view.disableAanpassen(O.isAanpasbaar());
		}
	}

	class OpdrachtListCellRenderer extends JLabel implements
			ListCellRenderer<Opdracht> {

		public OpdrachtListCellRenderer() {
			setOpaque(true);
		}

		@Override
		public Component getListCellRendererComponent(
				JList<? extends Opdracht> list, Opdracht value, int index,
				boolean isSelected, boolean cellHasFocus) {
			Color background;
			Color foreground;

			Opdracht opdracht = null;
			if (value instanceof Opdracht) {
				opdracht = (Opdracht) value;
			}
			this.setText(opdracht.toStringVoorLijst());

			UIDefaults defaults = UIManager.getDefaults();

			if (isSelected) {
				background = defaults.getColor("List.selectionBackground");
				foreground = defaults.getColor("List.selectionForeground");
			} else {
				background = Color.WHITE;
				foreground = Color.BLACK;
			}

			setBackground(background);
			setForeground(foreground);
			return this;
		}
	}
}
