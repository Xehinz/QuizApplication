package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import controller.OpdrachtBeheerController.BekijkDetailsKnopListener;
import controller.OpdrachtBeheerController.NieuweOpdrachtKnopListener;
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
		}
		if (opdracht instanceof Meerkeuze) {
			view = new OpdrachtMeerkeuzeBeheerView();
		}
		if (opdracht instanceof Opsomming) {
			view = new OpdrachtOpsommingBeheerView();
		}
		if (opdracht instanceof Reproductie) {
			view = new OpdrachtReproductieBeheerView();
		}

		view.setOpdrachtCategorie(opdracht.getOpdrachtCategorie());
		view.setVraag(opdracht.getVraag());
		view.setJuisteAntwoord(opdracht.getJuisteAntwoord());
		view.setHints(opdracht.getHints());
		view.setMaxAantalPogingen(Integer.toString(opdracht
				.getMaxAantalPogingen()));
		view.setMaxAntwoordTijd(Integer.toString(opdracht.getMaxAntwoordTijd()));

		
		view.NieuweHintKnopActionListener(new NieuweHintKnopListener());
		view.OpslaanKnopActionListener(new OpslaanKnopListener());

		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		opdracht.setHints(hints);
		opdracht.setJuisteAntwoord(juisteAntwoord);
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
