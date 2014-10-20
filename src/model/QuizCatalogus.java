package model;

import java.util.List;

public class QuizCatalogus {
	private List<Quiz> quizcatalogus;

	public QuizCatalogus(List<Quiz> qc) {
		this.quizcatalogus = qc;
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
	
	public int count(){
		return this.quizcatalogus.size();
	}
}
