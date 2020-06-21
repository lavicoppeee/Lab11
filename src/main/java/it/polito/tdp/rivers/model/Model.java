package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.List;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {

	private RiversDAO dao;
	private List<River> rivers;
	private Simulator simulator;
	

	public Model() {
		super();
		this.dao = new RiversDAO();
		
		rivers = dao.getAllRivers();
		//lo faccio qui una volta sola e bona 
		for (River river : rivers) {
			dao.getFlows(river);
		}
		simulator=new Simulator();
	}
	
	public List<River> getAllRivers() {
		return rivers;
	}
	
/**
 * Prendo la prima misurazione del flusso della lista per quel fiume 
 * @param r
 * @return
 */
	public LocalDate getPrimaMisurazione(River r) {

		if (!r.getFlows().isEmpty()) {
			return r.getFlows().get(0).getDay(); 
		}
		return null;
	}
	
	/**
	 * Prendo l'ultima misurazione del flusso della lista per quel fiume 
	 * dato la lista prendo la data del valore che si trova all'ultima posizione della lista
	 * size - 1
	 * @param r
	 * @return
	 */
	public LocalDate getUltimaMisurazione(River r) {
		
		if(!r.getFlows().isEmpty()) {
			return r.getFlows().get(r.getFlows().size()-1).getDay();
		}
		return null;
	}
	
	/**
	 * Calcolo la media del flusso di quel fiume
	 * 
	 * -Calcolo la somma di tutti i flussi per quel fiume
	 *- Divido tutti la somma dei flussi per il numero di flussi che sarebbe 
	 * la lunghezza della lista di flows in fiume
	 * -Salvo la media nel valore di media in fiume
	 * -Ritorno la media
	 * 
	 * @param r
	 * @return
	 */
	public double getMedia(River r) {

		double avg = 0;

		for (Flow f : r.getFlows()) {
			avg += f.getFlow();
		}
		avg = avg / r.getFlows().size();
		r.setFlowAvg(avg);
		return avg;
	}
	
	public double getNumMisurazioni(River r) {

		return r.getFlows().size();

	}
	
	public void Simula(River r,Double k) {
		simulator.init(r,k);
		simulator.run();
	}
	
	public List<Result> getResult(){
		return this.simulator.getResult();
	}
	
	
}
