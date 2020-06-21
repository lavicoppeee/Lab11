package it.polito.tdp.rivers.db;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RiversDAO {

	public List<River> getAllRivers() {
		
		final String sql = "SELECT id, name FROM river";

		List<River> rivers = new LinkedList<River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				rivers.add(new River(res.getInt("id"), res.getString("name")));
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return rivers;
	}
	
	/**
	 * Per ogni fiume estraggo tutti i flussi.
	 * Creo una lista di flussi dove salvo tutti i flussi per una dato fiume.
	 * Collegamento tra il fiume e i flussi è la lista di flussi in fiume
	 * Salvo tutti i paramentri trovati in quella lista che aggiungo in fiume 
	 * 
	 * @param r
	 * @return
	 */
	public List<Flow> getFlows(River r){
		final String sql = "SELECT id, day, flow FROM flow WHERE river = ? ";

		List<Flow> flows = new LinkedList<Flow>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, r.getId());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				flows.add(new Flow(res.getDate("day").toLocalDate(),res.getDouble("flow"),r));
			}
			Collections.sort(flows);
			r.setFlows(flows);

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return flows;
	}
	
}
