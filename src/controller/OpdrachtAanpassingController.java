package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controller.OpdrachtBeheerController.BekijkDetailsKnopListener;
import controller.OpdrachtBeheerController.NieuweKlassiekeKnopListener;
import controller.OpdrachtBeheerController.PasOpdrachtAanKnopListener;
import controller.OpdrachtBeheerController.VerwijderOpdrachtKnopListener;
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
import view.OpdrachtMeerkeuzeBeheerView;
import view.OpdrachtOpsommingBeheerView;
import view.OpdrachtReproductieBeheerView;
import view.QuizAanpassingView;

public class OpdrachtAanpassingController {
	private OpdrachtAanpassingView view;
	private DBHandler dbHandler;
	private Opdracht opdracht;
	private Leraar leraar;
	private OpdrachtBeheerController opdrachtBeheerController;

	public OpdrachtAanpassingController(Opdracht opdracht, Leraar leraar,
			DBHandler dbHandler,
			OpdrachtBeheerController opdrachtBeheerController) {
		this.opdracht = opdracht;
		this.leraar = leraar;
		this.dbHandler = dbHandler;
		this.opdrachtBeheerController = opdrachtBeheerController;

		if (opdracht instanceof KlassiekeOpdracht) {
			view = new OpdrachtAanpassingView();
			setAlgemeneComponenten(view);
		}

		if (opdracht instanceof Meerkeuze) {
			view = new OpdrachtMeerkeuzeBeheerView();
			setAlgemeneComponenten(view);
			((OpdrachtMeerkeuzeBeheerView) view)
					.setMogelijkeAntwoordenMeerkeuze(((Meerkeuze) opdracht)
							.getOptiesString());
		}

		if (opdracht instanceof Opsomming) {
			view = new OpdrachtOpsommingBeheerView();
			setAlgemeneComponenten(view);
			((OpdrachtOpsommingBeheerView) view)
					.setInJuisteVolgorde(((Opsomming) opdracht)
							.getInJuisteVolgorde());
		}
		if (opdracht instanceof Reproductie) {
			view = new OpdrachtReproductieBeheerView();
			setAlgemeneComponenten(view);
			((OpdrachtReproductieBeheerView) view)
					.setMinimumAantalTrefwoorden(((Reproductie) opdracht)
							.getMinimumAantalTrefwoorden());
		}
	}

	public void setAlgemeneComponenten(OpdrachtAanpassingView view) {
		view.setOpdrachtCategorie(getOpdracht().getOpdrachtCategorie());
		view.setAuteur(getOpdracht().getAuteur());
		view.setVraag(getOpdracht().getVraag());
		view.setJuisteAntwoord(getOpdracht().getJuisteAntwoord());
		view.setHints(getOpdracht().getHints());
		view.setMaxAantalPogingen(Integer.toString(getOpdracht()
				.getMaxAantalPogingen()));
		view.setMaxAntwoordTijd(Integer.toString(getOpdracht()
				.getMaxAntwoordTijd()));

		view.NieuweHintKnopActionListener(new NieuweHintKnopListener());
		view.OpslaanKnopActionListener(new OpslaanKnopListener());

		view.setVisible(true);
	}

	public Opdracht getOpdracht() {
		return opdracht;
	}

	public OpdrachtAanpassingView getView() {
		return this.view;
	}

	public void setOpdracht(OpdrachtCategorie oc, String vraag,
			String juisteAntwoord, ArrayList<String> hints,
			int maxAantalPogingen, int maxAntwoordTijd) {
		opdracht.setJuisteAntwoord(juisteAntwoord);
		opdracht.setVraag(vraag);
		opdracht.setMaxAantalPogingen(maxAantalPogingen);
		opdracht.setMaxAntwoordTijd(maxAntwoordTijd);
		opdracht.setOpdrachtCategorie(oc);
		opdracht.setHints(hints);
	}

	public void setMeerkeuze(String mogelijkeAntwoorden) {
		((Meerkeuze) opdracht).setOpties(mogelijkeAntwoorden);
	}

	public void setOpsomming(boolean inJuisteVolgorde) {
		((Opsomming) opdracht).setInJuisteVolgorde(inJuisteVolgorde);
	}

	public void setReproductie(int minAantalTrefwoorden) {
		((Reproductie) opdracht)
				.setMinimumAantalTrefwoorden(minAantalTrefwoorden);
	}

	class NieuweHintKnopListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			getOpdracht().addHint(view.getHint());
			view.setHints(getOpdracht().getHints());
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
				}
				view.dispose();
				opdrachtBeheerController.getView().setOpdrachten(
						dbHandler.getOpdrachtCatalogus().getOpdrachten());
			} catch (Exception e) {
				view.toonErrorMessage(String.format(
						"Fout bij het opslaan van de opdracht:\n%s",
						e.getMessage()), "Fout bij Opslaan");
			}
		}
	}
}
