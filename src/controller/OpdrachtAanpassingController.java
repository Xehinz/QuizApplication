package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.AbstractListModel;

import model.KlassiekeOpdracht;
import model.Leraar;
import model.Meerkeuze;
import model.Opdracht;
import model.OpdrachtCategorie;
import model.Opsomming;
import model.Reproductie;
import persistency.DBHandler;
import view.OpdrachtMeerkeuzeBeheerView;
import view.OpdrachtOpsommingBeheerView;
import view.OpdrachtReproductieBeheerView;
import view.ViewFactory;
import view.ViewType;
import view.viewInterfaces.IOpdrachtAanpassingView;
import view.viewInterfaces.IOpdrachtMeerkeuzeBeheerView;
import view.viewInterfaces.IOpdrachtOpsommingBeheerView;
import view.viewInterfaces.IOpdrachtReproductieBeheerView;

public class OpdrachtAanpassingController {
	
	private IOpdrachtAanpassingView view;
	private DBHandler dbHandler;
	private Opdracht opdracht;
	private OpdrachtBeheerController opdrachtBeheerController;
	
	private HintListModel hintListModel;

	public OpdrachtAanpassingController(Opdracht opdracht, Leraar leraar,
			DBHandler dbHandler,
			OpdrachtBeheerController opdrachtBeheerController, ViewFactory viewFactory) {
		this.opdracht = opdracht;
		this.dbHandler = dbHandler;
		this.opdrachtBeheerController = opdrachtBeheerController;
		this.hintListModel = new HintListModel(opdracht.getHints());		
		
		if (opdracht instanceof KlassiekeOpdracht) {
			view = (IOpdrachtAanpassingView)viewFactory.maakView(ViewType.OpdrachtAanpassingView);
			setAlgemeneComponenten(view);
		}

		if (opdracht instanceof Meerkeuze) {
			view = (IOpdrachtMeerkeuzeBeheerView)viewFactory.maakView(ViewType.OpdrachtMeerkeuzeBeheerView);
			setAlgemeneComponenten(view);
			((OpdrachtMeerkeuzeBeheerView) view)
					.setMogelijkeAntwoordenMeerkeuze(((Meerkeuze) opdracht)
							.getOptiesString());
		}

		if (opdracht instanceof Opsomming) {
			view = (IOpdrachtOpsommingBeheerView)viewFactory.maakView(ViewType.OpdrachtOpsommingBeheerView);
			setAlgemeneComponenten(view);
			((OpdrachtOpsommingBeheerView) view)
					.setInJuisteVolgorde(((Opsomming) opdracht)
							.getInJuisteVolgorde());
		}
		if (opdracht instanceof Reproductie) {
			view = (IOpdrachtReproductieBeheerView)viewFactory.maakView(ViewType.OpdrachtReproductieBeheerView);
			setAlgemeneComponenten(view);
			((OpdrachtReproductieBeheerView) view)
					.setMinimumAantalTrefwoorden(((Reproductie) opdracht)
							.getMinimumAantalTrefwoorden());
		}
		
		view.setVisible(true);
	}

	public Opdracht getOpdracht() {
		return opdracht;
	}

	public IOpdrachtAanpassingView getView() {
		return this.view;
	}
	
	protected void setAlgemeneComponenten(IOpdrachtAanpassingView view) {
		view.setOpdrachtCategorie(getOpdracht().getOpdrachtCategorie());
		view.setAuteur(getOpdracht().getAuteur());
		view.setVraag(getOpdracht().getVraag());
		view.setJuisteAntwoord(getOpdracht().getJuisteAntwoord());
		view.setHints(getOpdracht().getHints());
		view.setMaxAantalPogingen(Integer.toString(getOpdracht()
				.getMaxAantalPogingen()));
		view.setMaxAntwoordTijd(Integer.toString(getOpdracht()
				.getMaxAntwoordTijd()));
		view.setHintListModel(hintListModel);

		view.NieuweHintKnopActionListener(new NieuweHintKnopListener());
		view.OpslaanKnopActionListener(new OpslaanKnopListener());
		view.VerwijderHintKnopActionListener(new VerwijderHintKnopListener());

		view.setVisible(true);
	}

	private void setOpdracht(OpdrachtCategorie oc, String vraag,
			String juisteAntwoord, ArrayList<String> hints,
			int maxAantalPogingen, int maxAntwoordTijd) {
		opdracht.setJuisteAntwoord(juisteAntwoord);
		opdracht.setVraag(vraag);
		opdracht.setMaxAantalPogingen(maxAantalPogingen);
		opdracht.setMaxAntwoordTijd(maxAntwoordTijd);
		opdracht.setOpdrachtCategorie(oc);
		opdracht.setHints(hints);
	}

	private void setMeerkeuze(String mogelijkeAntwoorden) {
		((Meerkeuze) opdracht).setOpties(mogelijkeAntwoorden);
	}

	private void setOpsomming(boolean inJuisteVolgorde) {
		((Opsomming) opdracht).setInJuisteVolgorde(inJuisteVolgorde);
	}

	private void setReproductie(int minAantalTrefwoorden) {
		((Reproductie) opdracht)
				.setMinimumAantalTrefwoorden(minAantalTrefwoorden);
	}

	class NieuweHintKnopListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			opdracht.addHint(view.getHint());
			hintListModel.addHint(view.getHint());
		}
	}
	
	class VerwijderHintKnopListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {			
			if (view.getGeselecteerdeHintIndex() > -1) {
				String hint = hintListModel.getElementAt(view.getGeselecteerdeHintIndex());
				opdracht.removeHint(hint);
				hintListModel.removeHint(hint);
			} else {
				view.toonErrorMessage("Selecteer een hint om te verwijderen", "Geen hint geselecteerd");
			}
			
		}		
	}

	class OpslaanKnopListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				setOpdracht(view.getOpdrachtCategorie(), view.getVraag(),
						view.getJuisteAntwoord(), view.getHints(),
						view.getMaxAantalPogingen(), view.getMaxAntwoordTijd());
				if (opdracht instanceof Meerkeuze) {
					setMeerkeuze(((OpdrachtMeerkeuzeBeheerView) view)
							.getMogelijkeAntwoordenMeerkeuze());
				}
				if (opdracht instanceof Opsomming) {

					setOpsomming(((OpdrachtOpsommingBeheerView) view)
							.getInJuisteVolgorde());

				}
				if (opdracht instanceof Reproductie) {

					setReproductie(((OpdrachtReproductieBeheerView) view)
							.getMinimumAantalTrefwoorden());

				}
				if (opdracht.getID() == 0) {
					dbHandler.getOpdrachtCatalogus().addOpdracht(opdracht);
					opdrachtBeheerController.addOpdrachtAanLijst(opdracht);
				} else {
					opdrachtBeheerController.refreshTabel();
				}
				view.dispose();
			} catch (Exception e) {
				view.toonErrorMessage(String.format(
						"Fout bij het opslaan van de opdracht:\n%s",
						e.getMessage()), "Fout bij Opslaan");
			}
		}
	}
	
	@SuppressWarnings("serial")
	class HintListModel extends AbstractListModel<String> {

		private ArrayList<String> hints;
		
		public HintListModel(Collection<String> hints) {
			this.hints = new ArrayList<String>(hints);
		}
		
		@Override
		public String getElementAt(int index) {
			return hints.get(index);
		}

		@Override
		public int getSize() {
			return hints.size();
		}		
		
		public void addHint(String hint) {
			hints.add(hint);
			fireContentsChanged(this, 0, hints.size() - 1);
		}
		
		public void removeHint(String hint) {
			hints.remove(hint);
			fireContentsChanged(this, 0, hints.size() - 1);
		}
		
	}
}
