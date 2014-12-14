package controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

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
import view.viewInterfaces.IView;

public class OpdrachtBeheerController {

	private DBHandler dbHandler;
	private ViewFactory viewFactory;
	private IOpdrachtBeheerView view;
	private Opdracht opdracht;
	private Leraar leraar;
	
	private OpdrachtenListModel opdrachtenListModel;

	public OpdrachtBeheerController(DBHandler dbHandler, Leraar leraar,
			ViewFactory viewFactory) {
		this.dbHandler = dbHandler;
		this.leraar = leraar;
		this.viewFactory = viewFactory;
		this.view = (IOpdrachtBeheerView)viewFactory.maakView(ViewType.OpdrachtBeheerView);
		this.opdracht = null;
		this.opdrachtenListModel = new OpdrachtenListModel(dbHandler.getOpdrachtCatalogus().getOpdrachten());

		view.setListModel(opdrachtenListModel);
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

	public IView getView() {
		return this.view;
	}
	
	protected void refreshTabel() {
		opdrachtenListModel.refresh();
	}
	
	protected void addOpdrachtAanLijst(Opdracht opdracht) {
		opdrachtenListModel.addOpdracht(opdracht);
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
			if (view.getGeselecteerdeRij() > -1) {
				opdracht = opdrachtenListModel.getOpdracht(view.getGeselecteerdeRij());
			} else {
				view.toonErrorMessage("Selecteer een opdracht om ze aan te passen", "Geen opdracht geselecteerd");
			}
			
			try {				
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
			if (view.getGeselecteerdeRij() > -1) {
				opdracht = opdrachtenListModel.getOpdracht(view.getGeselecteerdeRij());
			} else {
				view.toonErrorMessage("Selecteer een opdracht om ze aan te verwijderen", "Geen opdracht geselecteerd");
			}
			
			try {
				dbHandler.getOpdrachtCatalogus().removeOpdracht(opdracht);
				opdrachtenListModel.removeOpdracht(opdracht);
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
			if (view.getGeselecteerdeRij() > -1) {
				opdracht = opdrachtenListModel.getOpdracht(view.getGeselecteerdeRij());
			} else {
				view.toonErrorMessage("Selecteer een opdracht om er de details van te bekijken", "Geen opdracht geselecteerd");
			}
			
			try {
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
				opdrachtenListModel.setFilterCategorie(null);
			} else {
				OpdrachtCategorie OC = OpdrachtCategorie
						.valueOf(opdrachtCategorieString.toUpperCase());
				opdrachtenListModel.setFilterCategorie(OC);
			}
		}
	}

	class OpdrachtGeselecteerd implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent event) {
			if (view.getGeselecteerdeRij() > -1) {
				opdracht = opdrachtenListModel.getOpdracht(view.getGeselecteerdeRij());
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
	
	@SuppressWarnings("serial")
	class OpdrachtenListModel extends AbstractListModel<Opdracht> {

		private ArrayList<Opdracht> opdrachten;
		private ArrayList<Opdracht> gefilterd;
		private OpdrachtCategorie filterCategorie;
		
		public OpdrachtenListModel(Collection<Opdracht> opdrachten) {
			this.opdrachten = new ArrayList<Opdracht>(opdrachten);
			filter();
		}
		
		@Override
		public Opdracht getElementAt(int index) {
			return gefilterd.get(index);
		}

		@Override
		public int getSize() {
			return gefilterd.size();
		}
		
		public Opdracht getOpdracht(int index) {
			return gefilterd.get(index);
		}
		
		public void setFilterCategorie(OpdrachtCategorie filterCategorie) {
			this.filterCategorie = filterCategorie;
			filter();
		}
		
		public void addOpdracht(Opdracht opdracht) {
			opdrachten.add(opdracht);
			filter();
			fireContentsChanged(this, 0, gefilterd.size() - 1);
		}
		
		public void removeOpdracht(Opdracht opdracht) {
			opdrachten.remove(opdracht);
			filter();
			fireContentsChanged(this, 0, gefilterd.size() - 1);
		}
		
		public void refresh() {
			filter();
		}
		
		private void filter() {
			gefilterd = new ArrayList<Opdracht>(opdrachten);
			if (filterCategorie != null) {
				for (Opdracht opdracht : opdrachten) {
					if (!opdracht.getOpdrachtCategorie().equals(filterCategorie)) {
						gefilterd.remove(opdracht);
					}
				}
			}
			fireContentsChanged(this, 0, gefilterd.size() - 1);
		}
		
	}
}
