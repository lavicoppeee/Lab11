package it.polito.tdp.rivers.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;



public class Simulator {

	//CODA
	private PriorityQueue <Flow> queue;

	//MONDO
	//insieme dei flussi del bacino 
	
	//INPUT
	double Q;
	double C;
	int numDays; //giorni in cui non garantisco 
	double fOutMin;
	private List<Double> capacity ;
	
	//OUTPUT
	private List<Result> results;
	
	
	public void init(River r, double k) {
		this.queue=new PriorityQueue<Flow>();
		this.queue.addAll(r.getFlows());
        
	    capacity = new ArrayList<Double>();
		
		Q = k * 30 * convertM3SecToM3Day(r.getFlowAvg());
	    C = Q / 2;
		fOutMin = convertM3SecToM3Day(0.8 * r.getFlowAvg());
	    numDays = 0;

	   results=new LinkedList<Result>();
	}
	

	public void run () {
		Flow flow;
		
		while((flow=this.queue.poll())!=null){
			//eseguo evento 
			process(flow);
		}
	}
	
	private void process(Flow f) {
		
		double fOut=fOutMin;
		
		// C'è il 5% di possibilità che fOut sia 10 volte fOutMin
		if (Math.random() > 0.95) {
			fOut = 10 * fOutMin;
		}
		
		C += convertM3SecToM3Day(f.getFlow());
		
		if(C>Q) {
			Q=C;
		}else 
			 if(C<fOut) {
				 //non garantisco la quantita minima 
				 numDays++;
				 C=0;
			 }
			 else {
				 C-=fOut;
			 }
		capacity.add(C);
		
		double CAvg = 0;
		for (Double d : capacity) {
			CAvg += d;
		}
		CAvg = CAvg / capacity.size();
		this.results.add(new Result(CAvg, numDays));
	}
	
	
	public List<Result> getResult(){
		return this.results;
	}
	
	
	private double convertM3SecToM3Day(double flowAvg) {
		// TODO Auto-generated method stub
		return flowAvg*60*60*24;
	}
	
}
