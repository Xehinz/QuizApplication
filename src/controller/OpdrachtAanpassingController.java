package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;

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

	public OpdrachtAanpassingController(Opdracht opdracht, Leraar leraar,
			DBHandler dbHandler) {
		this.opdracht = opdracht;
		this.leraar = leraar;
		this.dbHandler = dbHandler;

		if (opdracht instanceof KlassiekeOpdracht) {
			view = new OpdrachtAanpassingView();
			setAlgemeneComponenten(view);
		}

		/*if (opdracht instanceof Meerkeuze) {
			view = new OpdrachtMeerkeuzeBeheerView();
			setAlgemeneComponenten(view);
			((OpdrachtMeerkeuzeBeheerView) view)
					.setMogelijkeAntwoordenMeerkeuze(((Meerkeuze) opdracht)
							.getOpties());
		}*/

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
		view.setVraag(getOpdracht().getVraag());
		view.setJuisteAntwoord(getOpdracht().getJuisteAntwoord());
		view.setHints(getOpdracht().getHints());
		view.setMaxAantalPogingen(Integer.toString(getOpdracht()
				.getMaxAantalPogingen()));
		view.setMaxAntwoordTijd(Integer.toString(getOpdracht()
				.getMaxAntwoordTijd()));

		view.NieuweHintKnopActionListener(new NieuweHintKnopListener());
		view.OpslaanKnopActionListener(new OpslaanKnopListener());

		//view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.setVisible(true);
	}

	public Opdracht getOpdracht() {
		return opdracht;
	}

	public void setOpdracht(OpdrachtCategorie oc, String vraag,
			String juisteAntwoord, ArrayList<String> hints,
			int maxAantalPogingen, int maxAntwoordTijd) {
		opdracht.setVraag(vraag);
		opdracht.setMaxAantalPogingen(maxAantalPogingen);
		opdracht.setMaxAntwoordTijd(maxAntwoordTijd);
		opdracht.setOpdrachtCategorie(oc);
		/*opdracht.setHints(hints);
		opdracht.setJuisteAntwoord(juisteAntwoord);*/
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
			setOpdracht(view.getOpdrachtCategorie(), view.getVraag(),
					view.getJuisteAntwoord(), view.getHints(),
					view.getMaxAantalPogingen(), view.getMaxAntwoordTijd());
		}
	}
}
