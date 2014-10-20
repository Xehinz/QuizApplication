package model;

import java.util.List;

public class QuizCatalogus implements Comparable<QuizCatalogus>, Cloneable {
	private List<Quiz> quizcatalogus;

	public QuizCatalogus(List<Quiz> qc) {
		this.quizcatalogus = qc;
	}
	
	protected List<Quiz> getList(){
		return this.quizcatalogus;
	}

	public void addOpdracht(Quiz Q) {
		this.quizcatalogus.add(Q);
	}

	public void removeOpdracht(Quiz Q) {
		this.quizcatalogus.remove(Q);
	}
	
	public Quiz getQuiz(int volgnr){
		return this.quizcatalogus.get(volgnr-1);
	}
	
	public boolean hasQuiz(Quiz quiz){
		return this.quizcatalogus.contains(quiz);
	}
	
	public int count(){
		return this.quizcatalogus.size();
	}
	
	@Override
	public String toString(){
		return "Quizcatalogus met " + this.count() + " quizzen";
	}
	
	public boolean equals (QuizCatalogus aQuizCatalogus){
		return this.quizcatalogus.equals(aQuizCatalogus.getList());
	}

	@Override
	public int compareTo(QuizCatalogus arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}
