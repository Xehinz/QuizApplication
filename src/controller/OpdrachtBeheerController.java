package controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import view.ViewFactory;
import view.ViewType;
import view.viewInterfaces.IOpdrachtBeheerView;

public class OpdrachtBeheerController {

	private DBHandler dbHandler;
	private ViewFactory viewFactory;
	private IOpdrachtBeheerView view;
	private Opdracht opdracht;
	private Leraar leraar;

	public OpdrachtBeheerController(DBHandler dbHandler, Leraar leraar,
			ViewFactory viewFactory) {
		this.setDBHandler(dbHandler);
		this.leraar = leraar;
		this.viewFactory = viewFactory;
		this.view = (IOpdrachtBeheerView)viewFactory.maakView(ViewType.OpdrachtBeheerView);
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

	public IOpdrachtBeheerView getView() {
		return this.view;
	}

	private void openOpdrachtAanpassing(Opdracht opdracht, Leraar leraar) {
		new OpdrachtAanpassingController(opdracht, leraar, dbHandler, this, viewFactory);
	}

	private void openOpdrachtBekijken(Opdracht opdracht, Leraar leraar) {
		OpdrachtAanpassingController OAC = new OpdrachtAanpassingController(
				opdracht, leraar, dbHandler, this, viewFactory);
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
			try {
				opdracht = view.getGeselecteerdeOpdracht();
				openOpdrachtAanpassing(opdracht, opdracht.getAuteur());
			} catch (Exception e) {
				view.toonErrorMessage(String.format(
						"Fout bij het aanpassen van de opdracht:\n%s",
						e.getMessage()), "Fout bij aanpassen");
			}
		}
	}

	class VerwijderOpdrachtKnopListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				opdracht = view.getGeselecteerdeOpdracht();
				dbHandler.getOpdrachtCatalogus().removeOpdracht(opdracht);
				view.setOpdrachten(dbHandler.getOpdrachtCatalogus()
						.getOpdrachten());
			} catch (Exception e) {
				view.toonErrorMessage(String.format(
						"Fout bij het verwijderen van de opdracht:\n%s",
						e.getMessage()), "Fout bij verwijderen");
			}
		}
	}

	class BekijkDetailsKnopListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				opdracht = view.getGeselecteerdeOpdracht();
				openOpdrachtBekijken(opdracht, opdracht.getAuteur());
			} catch (Exception e) {
				view.toonErrorMessage(
						String.format("Fout bij het details bekijken:\n%s",
								e.getMessage()), "Fout bij details bekijken");
			}
		}
	}

	class SelecteerCategorieListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			String opdrachtCategorieString = view.getOpdrachtCategorie();
			if (opdrachtCategorieString.equals("Alle categorieën")) {
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
			if (view.getGeselecteerdeOpdracht() != null) {
				opdracht = view.getGeselecteerdeOpdracht();
				view.disableAanpassen(opdracht.isAanpasbaar());
			}
		}
	}

	@SuppressWarnings("serial")
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
