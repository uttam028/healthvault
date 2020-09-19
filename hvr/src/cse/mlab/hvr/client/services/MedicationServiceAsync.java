package cse.mlab.hvr.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;

import cse.mlab.hvr.shared.Diagnosis;
import cse.mlab.hvr.shared.DiagnosisList;
import cse.mlab.hvr.shared.Medication;
import cse.mlab.hvr.shared.MedicationList;
import cse.mlab.hvr.shared.Response;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface MedicationServiceAsync {
	
	void saveMedication(MedicationList medicationList, AsyncCallback<Response> callback);
	void updateMedication(Medication medication, AsyncCallback<Response> callback);
//	void getMedications(String email, AsyncCallback<String> callback);
	void getMedicationsList(String email, AsyncCallback<MedicationList> callback);
	void deleteMedications(String email, String list, AsyncCallback<Response> callback);
	void stopUsingMedication(long id, String endDate, AsyncCallback<Response> callback);
	
	//diagnosis
	void saveDiagnosis(Diagnosis diagnosis, AsyncCallback<Response> callback);
	void updateDiagnosis(Diagnosis diagnosis, AsyncCallback<Response> callback);
	void getDiagnosisList(String email, AsyncCallback<DiagnosisList> callback);
	void deleteDiagnosis(String email, String list, AsyncCallback<Response> callback);

}
