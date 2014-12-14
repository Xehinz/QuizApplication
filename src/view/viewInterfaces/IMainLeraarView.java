package view.viewInterfaces;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import persistency.StorageStrategy;
import model.score.ScoreStrategyType;

public interface IMainLeraarView extends IView {
	
	public void setLeraar(String leraar);
	
	public void addLogoutKnopActionListener(ActionListener listener);
	
	public void addAfsluitenKnopActionListener(ActionListener listener);
	
	public void addOpdrachtBeheerKnopActionListener(ActionListener listener);
	
	public void addQuizBeheerKnopActionListener(ActionListener listener);
	
	public void addLeerlingBeheerKnopActionListener(ActionListener listener);
	
	public void addOverzichtScoresKnopActionListener(ActionListener listener);
	
	public void addRodeLoginClickedListener(ActionListener listener);
	
	public void setRodeLoginSelected(boolean selected);
	
	public void setScoreBerekeningSelected(ScoreStrategyType scoreStrategyType);
	
	public void addScoreStrategieChangedListener(ItemListener listener);
	
	public void setOpslagStrategySelected(StorageStrategy storageStrategy);
	
	public void addOpslagStrategyChangedListener(ItemListener listener);
	
	public void setEnabledDBConnectieGegevens(boolean isEnabled);
	
	public void addConnectieGegevensKlikListener(ActionListener listener);
	
	public void addLookAndFeelChangedListener(ItemListener listener);
	
	public void setSelectedLookAndFeel(String className);

}
