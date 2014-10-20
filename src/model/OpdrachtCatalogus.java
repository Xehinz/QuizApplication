package model;

import java.util.List;

public class OpdrachtCatalogus {
private List<Opdracht> opdrachtcatalogus;
	
	public OpdrachtCatalogus(List<Opdracht> oc){
		this.opdrachtcatalogus = oc;
	}
	
	public void addOpdracht(Opdracht O){
		this.opdrachtcatalogus.add(O);
	}
	
	public void removeOpdracht(Opdracht O){
		this.opdrachtcatalogus.remove(O);
	}
	
	public Opdracht getOpdracht(int volgnr){
		return this.opdrachtcatalogus.get(volgnr-1);
	}
	
	public boolean hasOpdracht(Opdracht opdracht){
		return this.opdrachtcatalogus.contains(opdracht);
	}
	
	public int count(){
		return this.opdrachtcatalogus.size();
	}
	
	@Override
	public String toString(){
		return "Opdrachtcatalogus met " + this.count() + " opdrachten";
	}
	
}