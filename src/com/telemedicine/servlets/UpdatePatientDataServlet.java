package com.telemedicine.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.telemedicine.dao.ControllerMySQL;
import com.telemedicine.models.APIRequest;
import com.telemedicine.models.APIResponse;
import com.telemedicine.models.Patient;


public class UpdatePatientDataServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    public UpdatePatientDataServlet() {super();}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ControllerMySQL controllerMySQL = new ControllerMySQL();
		
		// --> You need to call this method to insert the data (weight and height) into the patient
		// boolean result = controllerMySQL.updatePatientData(patientId, patientWeight, patientHeight);
			
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
	
		APIRequest requestAPI = new Gson().fromJson(request.getParameter("APIRequest"), APIRequest.class);
		
		if(requestAPI.getPatientId() != 0) {
			
			if(requestAPI.getPatientWeight() == 0.0f && requestAPI.getPatientHeight() == 0.0f) {
				
				sendMessage("Parameters Missing", true, response);
			} else {
			
				Patient patient = controllerMySQL.searchPatientByPatientId(requestAPI.getPatientId());
				if(patient != null) {
					
					if(requestAPI.getPatientWeight() == 0.0f) {
						
						//boolean result = controllerMySQL.updateAccount(requestAPI.getUserName(), user.getEmail(), requestAPI.getUserId());
						//boolean result = controllerMySQL.updatePatientData(patientId, patientWeight, patientHeight);
						boolean result = controllerMySQL.updatePatientData(patient.getPatientId(), patient.getPatientWeight(), requestAPI.getPatientHeight());
						if(result) {
							patient.setPatientHeight(requestAPI.getPatientHeight());
							sendPatientAccountData(patient, "Height updated successfully", response);
						} else {
							sendMessage("Error updating your weight", true, response);
						}
					} else {
						
					Patient patientExists = controllerMySQL.searchPatientByPatientId(requestAPI.getPatientId());
						if(patientExists != null) {
							
							sendMessage("Height already in used", true, response);
						} else {
							boolean result = controllerMySQL.updatePatientData(patient.getPatientId(), requestAPI.getPatientWeight(), patient.getPatientHeight());							if(result) {
							if(result) {
								patient.setPatientWeight(requestAPI.getPatientWeight());
								sendPatientAccountData(patient, "Weight updated successfully", response);	
							} else {
								sendMessage("Error updating your weight", true, response);
							}
						}
					}	
				} 
				}
			}
		} else {
			sendMessage("Patient ID Missing", true, response);
		}
	}

	private void sendPatientAccountData(Patient patient, String message, HttpServletResponse response)
			throws ServletException, IOException {

		APIResponse responseModel = new APIResponse();
		responseModel.setPatient(patient);

		responseModel.setError(false);
		responseModel.setAPImessage(message);
		response.getWriter().print(new Gson().toJson(responseModel));
		response.getWriter().flush();
		
	}

	
	
	private void sendMessage(String message, boolean error, HttpServletResponse response) throws ServletException, IOException {

		APIResponse responseModel = new APIResponse();
		responseModel.setError(error);
		responseModel.setAPImessage(message);
		response.getWriter().print(new Gson().toJson(responseModel));
		response.getWriter().flush();
	}
}
