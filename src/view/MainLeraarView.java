package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.MenuSelectionManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicCheckBoxMenuItemUI;
import javax.swing.plaf.basic.BasicRadioButtonMenuItemUI;

import persistency.StorageStrategy;
import model.score.ScoreStrategyType;
import view.viewInterfaces.IMainLeraarView;

/**
 * 
 * @author Ben Vandenberk
 *
 */
@SuppressWarnings("serial")
public class MainLeraarView extends JFrame implements IMainLeraarView {
	
	private JLabel lblLeraar;
	private JButton btnLogout, btnOpdrachtBeheer, btnQuizBeheer, btnLeerlingbeheer, btnOverzichtScores, btnAfsluiten;
	private JMenuBar menuBar;
	private JMenu instellingenMenu, viewMenu, opslagMenu, scoreMenu;
	private JCheckBoxMenuItem mCbxRodeLogin;
	private JRadioButtonMenuItem[] mRbtnScoreKeuzes, mRbtnOpslagKeuzes;
	private JMenuItem menuitConnectionString;
	
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	
	public MainLeraarView() {
		super("Quiz applicatie (Leraar)");
		this.setSize(450, 300);		
		
		layout = new GridBagLayout();
		this.setLayout(layout);
		
		lblLeraar = new JLabel("Leraar:");
		constraints = new GridBagConstraints();
		constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(10, 20, 0, 0);
		this.add(lblLeraar, constraints);
		
		btnLogout = new JButton("Logout");
		constraints = new GridBagConstraints();
		constraints.gridy = 0;
		constraints.gridx = 1;
		constraints.weightx = 0.1;
		constraints.anchor = GridBagConstraints.NORTHEAST;
		constraints.insets = new Insets(10, 0, 0, 20);
		this.add(btnLogout, constraints);
		
		btnOpdrachtBeheer = new JButton("Opdrachtbeheer");
		constraints = new GridBagConstraints();
		constraints.gridy = 1;
		constraints.gridx = 0;
		constraints.gridwidth = 2;
		constraints.weightx = 1;
		constraints.weighty = 10;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(10, 20, 10, 20);
		this.add(btnOpdrachtBeheer, constraints);
		
		btnQuizBeheer = new JButton("Quizbeheer");
		constraints = new GridBagConstraints();
		constraints.gridy = 2;
		constraints.gridx = 0;
		constraints.gridwidth = 2;
		constraints.weightx = 1;
		constraints.weighty = 10;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 20, 10, 20);
		this.add(btnQuizBeheer, constraints);
		
		btnLeerlingbeheer = new JButton("Leerlingbeheer");
		constraints = new GridBagConstraints();
		constraints.gridy = 3;
		constraints.gridx = 0;
		constraints.gridwidth = 2;
		constraints.weightx = 1;
		constraints.weighty = 10;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 20, 10, 20);
		this.add(btnLeerlingbeheer, constraints);
		
		btnOverzichtScores = new JButton("Overzicht Scores");
		constraints = new GridBagConstraints();
		constraints.gridy = 4;
		constraints.gridx = 0;
		constraints.gridwidth = 2;
		constraints.weightx = 1;
		constraints.weighty = 10;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 20, 0, 20);
		this.add(btnOverzichtScores, constraints);
		
		btnAfsluiten = new JButton("Afsluiten");
		constraints = new GridBagConstraints();
		constraints.gridy = 5;
		constraints.gridx = 1;
		constraints.weightx = 0.5;
		constraints.weighty = 0.1;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.insets = new Insets(10, 0, 10, 20);
		this.add(btnAfsluiten, constraints);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);		
		
		instellingenMenu = new JMenu("Instellingen");
		instellingenMenu.setMnemonic('I');		
		menuBar.add(instellingenMenu);
		
		viewMenu = new JMenu("Views");
		viewMenu.setMnemonic('V');
		instellingenMenu.add(viewMenu);
		instellingenMenu.addSeparator();
		
		opslagMenu = new JMenu("Opslag");
		opslagMenu.setMnemonic('O');		
		instellingenMenu.add(opslagMenu);
		instellingenMenu.addSeparator();
		
		scoreMenu = new JMenu("Scoreberekening");
		scoreMenu.setMnemonic('S');
		instellingenMenu.add(scoreMenu);
		
		mCbxRodeLogin = new JCheckBoxMenuItem("Rood loginschermpje");
		mCbxRodeLogin.setUI(new MenuCheckBoxBlijftOpenUI());
		viewMenu.add(mCbxRodeLogin);
		
		ButtonGroup opslagKeuzesGroep = new ButtonGroup();
		StorageStrategy[] opslagKeuzes = StorageStrategy.values();
		mRbtnOpslagKeuzes = new JRadioButtonMenuItem[opslagKeuzes.length];
		for (int i = 0; i < opslagKeuzes.length; i++) {
			mRbtnOpslagKeuzes[i] = new JRadioButtonMenuItem(opslagKeuzes[i].toString());
			mRbtnOpslagKeuzes[i].setUI(new MenuRadioButtonBlijftOpenUI());
			opslagKeuzesGroep.add(mRbtnOpslagKeuzes[i]);
			opslagMenu.add(mRbtnOpslagKeuzes[i]);
		}
		opslagMenu.addSeparator();
		
		menuitConnectionString = new JMenuItem("Database connectie gegevens");
		opslagMenu.add(menuitConnectionString);		
		
		ButtonGroup scoreKeuzesGroep = new ButtonGroup();
		ScoreStrategyType[] scoreKeuzes = ScoreStrategyType.values();
		mRbtnScoreKeuzes = new JRadioButtonMenuItem[scoreKeuzes.length];
		for (int i = 0; i < scoreKeuzes.length; i++) {
			mRbtnScoreKeuzes[i] = new JRadioButtonMenuItem(scoreKeuzes[i].toString());
			mRbtnScoreKeuzes[i].setUI(new MenuRadioButtonBlijftOpenUI());
			scoreKeuzesGroep.add(mRbtnScoreKeuzes[i]);
			scoreMenu.add(mRbtnScoreKeuzes[i]);
		}	
				
	}
	
	public void setLeraar(String leraar) {
		lblLeraar.setText(String.format("Leraar: %s", leraar));
	}
	
	public void addLogoutKnopActionListener(ActionListener listener) {
		btnLogout.addActionListener(listener);
	}
	
	public void addAfsluitenKnopActionListener(ActionListener listener) {
		btnAfsluiten.addActionListener(listener);
	}
	
	public void addOpdrachtBeheerKnopActionListener(ActionListener listener) {
		btnOpdrachtBeheer.addActionListener(listener);
	}
	
	public void addQuizBeheerKnopActionListener(ActionListener listener) {
		btnQuizBeheer.addActionListener(listener);
	}
	
	public void addLeerlingBeheerKnopActionListener(ActionListener listener) {
		btnLeerlingbeheer.addActionListener(listener);
	}
	
	public void addOverzichtScoresKnopActionListener(ActionListener listener) {
		btnOverzichtScores.addActionListener(listener);
	}
	
	public void addRodeLoginClickedListener(ActionListener listener) {
		mCbxRodeLogin.addActionListener(listener);
	}
	
	public void setRodeLoginSelected(boolean selected) {
		mCbxRodeLogin.setSelected(selected);
	}
	
	public void setScoreBerekeningSelected(ScoreStrategyType scoreStrategyType) {
		for (JRadioButtonMenuItem mRbtn : mRbtnScoreKeuzes) {
			if (mRbtn.getText().equals(scoreStrategyType.toString())) {
				mRbtn.setSelected(true);
			}
		}
	}
	
	public void addScoreStrategieChangedListener(ItemListener listener) {
		for (JRadioButtonMenuItem mRbtn : mRbtnScoreKeuzes) {
			mRbtn.addItemListener(listener);
		}
	}
	
	public void setOpslagStrategySelected(StorageStrategy storageStrategy) {
		for (JRadioButtonMenuItem mRbtn : mRbtnOpslagKeuzes) {
			if (mRbtn.getText().equals(storageStrategy.toString())) {
				mRbtn.setSelected(true);				
			}
		}
		
		if (!storageStrategy.equals(StorageStrategy.DATABASE)) {
			menuitConnectionString.setEnabled(false);
		}
	}
	
	public void addOpslagStrategyChangedListener(ItemListener listener) {
		for (JRadioButtonMenuItem mRbtn : mRbtnOpslagKeuzes) {
			mRbtn.addItemListener(listener);
		}
	}
	
	public void setEnabledDBConnectieGegevens(boolean isEnabled) {
		menuitConnectionString.setEnabled(isEnabled);
	}
	
	class MenuCheckBoxBlijftOpenUI extends BasicCheckBoxMenuItemUI {
		
		  @Override
		   protected void doClick(MenuSelectionManager msm) {
		      menuItem.doClick(0);
		   }
		  
	}
	
	class MenuRadioButtonBlijftOpenUI extends BasicRadioButtonMenuItemUI {
		
		 @Override
		   protected void doClick(MenuSelectionManager msm) {
		      menuItem.doClick(0);
		   }
		 
	}
	
}
