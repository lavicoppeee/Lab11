package it.polito.tdp.rivers;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.rivers.model.Model;
import it.polito.tdp.rivers.model.Result;
import it.polito.tdp.rivers.model.River;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	Model model;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<River> boxRiver;

    @FXML
    private TextField txtStartDate;

    @FXML
    private TextField txtEndDate;

    @FXML
    private TextField txtNumMeasurements;

    @FXML
    private TextField txtFMed;

    @FXML
    private TextField txtK;

    @FXML
    private Button btnSimula;

    @FXML
    private TextArea txtResult;

    @FXML
	void doInfo(ActionEvent event) {
    	
		River r = boxRiver.getValue();
		if (r != null) {
			txtStartDate.setText(this.model.getPrimaMisurazione(r).toString());
			txtEndDate.setText(this.model.getUltimaMisurazione(r).toString());
			txtFMed.setText(String.valueOf(this.model.getMedia(r)));
			txtNumMeasurements.setText(String.valueOf(this.model.getNumMisurazioni(r)));
		}

	}

    @FXML
    void doSimulazione(ActionEvent event) {
    	try {
    		
			double k = Double.parseDouble(txtK.getText());
		    this.model.Simula(boxRiver.getValue(), k);
		    
		    for(Result r: this.model.getResult()) {
			txtResult.setText("Numero di giorni \"critici\": "
					+ r.getNumDays() + "\n");
			txtResult.appendText("Occupazione media del bacino: " + r.getAvgC() + "\n");
			txtResult.appendText("SIMULAZIONE TERMINATA!\n");
		  
		    }
			
		} catch (NumberFormatException nfe) {
			txtResult.setText("Devi inserire un valore numerico per il fattore k");
		}
    }

    @FXML
    void initialize() {
        assert boxRiver != null : "fx:id=\"boxRiver\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtStartDate != null : "fx:id=\"txtStartDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtEndDate != null : "fx:id=\"txtEndDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNumMeasurements != null : "fx:id=\"txtNumMeasurements\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtFMed != null : "fx:id=\"txtFMed\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		// TODO Auto-generated method stub
		this.model=model;
		boxRiver.getItems().addAll(this.model.getAllRivers());
		
	}
}
