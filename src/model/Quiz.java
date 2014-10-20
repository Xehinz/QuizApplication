package model;

import java.util.List;
import java.util.ArrayList;

/**
 * 
 * @author johan
 * @version 2014.10.20
 *
 */

public class Quiz {

	private String onderwerp = "";
	private boolean isTest = true;
	private boolean isUniekeDeelname = false;
	private String quizStatus = "new";
	private List <QuizOpdracht>quizOpdrachten;
	
	/**
	 * lege constructor van de Quiz-class
	 */
	public Quiz(){
		quizOpdrachten = new ArrayList<QuizOpdracht>();				
	}
	
	/**
	 * constructor van de Quiz-class
	 * @param onderwerp
	 * 			het onderwerp van deze quiz
	 */
	public Quiz(String onderwerp){
		this.onderwerp = onderwerp;
		quizOpdrachten = new ArrayList<QuizOpdracht>();		
	}
	
	/**
	 * constructor van de Quiz-class
	 * @param onderwerp
	 * 			het onderwper van deze quiz
	 * @param isUniekeDeelname
	 * 			indicator of aan deze quiz slechts 1x mag deelgenomen worden
	 */
	public Quiz(String onderwerp, boolean isUniekeDeelname){
		this.onderwerp = onderwerp;
		this.isUniekeDeelname = isUniekeDeelname;
		quizOpdrachten = new ArrayList<QuizOpdracht>();		
	}
	
	/**
	 * constructor van de Quiz-class
	 * @param onderwerp
	 * 			het onderwerp van deze quiz
	 * @param isUniekeDeelname
	 * 			indicator of aan deze quiz slechts 1x mag deelgenomen worden
	 * @param quizStatus
	 * 			status van de quiz
	 */
	public Quiz(String onderwerp, boolean isUniekeDeelname, String quizStatus){
		this.onderwerp = onderwerp;
		this.isUniekeDeelname = isUniekeDeelname;
		this.quizStatus = quizStatus;
		quizOpdrachten = new ArrayList<QuizOpdracht>();		
	}

	/**
	 * Ophalen van het onderwerp van de quiz
	 * @return
	 */
	public String getOnderwerp(){
		return this.onderwerp;
	}
	
	/**
	 * Ophalen van de indicator of deze quiz een test is of niet
	 * @return
	 */
	public boolean getIsTest(){
		return this.isTest;
	}
	
	/**
	 * Ophalen van de indicator of aan deze quiz slechts 1x mag deelgenomen worden
	 * @return
	 */
	public boolean getIsUniekeDeelname(){
		return this.isUniekeDeelname;
	}

	/**
	 * Instellen van het onderwerp van de quiz
	 * @param onderwerp
	 * 			het onderwerp van deze quiz
	 * @return
	 */
	public boolean setOnderwerp(String onderwerp){
		this.onderwerp = onderwerp;
		return true;
	}
	
	/**
	 * Instellen van de indicator of deze quiz een test is of niet
	 * @param isTest
	 * 			indicator of deze quiz een test is of niet
	 * @return
	 */
	public boolean setIsTest(boolean isTest){
		this.isTest = isTest;
		return true;
	}
	
	/**
	 * Instellen van de indicator of je aan deze quiz slechts 1x mag deelnemen
	 * @param isUniekeDeelname
	 * 			indicator of aan deze quiz slechts 1x mag deelgenomen worden
	 * @return
	 */
	public boolean setIsUniekeDeelname(boolean isUniekeDeelname){
		this.isUniekeDeelname = isUniekeDeelname;
		return true;
	}
	
	public String toString(){
		
	}
	
	
	//toString
	//equals
	//Comparable --> implements Comparable (x)
	// - compareTo
	//hashCode (res)
	
	
}
