package cse.mlab.hvr.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import cse.mlab.hvr.shared.Diagnosis;
import cse.mlab.hvr.shared.DiagnosisList;
import cse.mlab.hvr.shared.Medication;
import cse.mlab.hvr.shared.MedicationList;
import cse.mlab.hvr.shared.Response;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("medication")
public interface MedicationService extends RemoteService {
	
	  Response saveMedication(MedicationList medicationList);
	  Response updateMedication(Medication medication);
//	  String getMedications(String email);
	  MedicationList getMedicationsList(String email);
	  Response deleteMedications(String email, String list);
	  Response stopUsingMedication(long id, String endDate);
	  
	  
	  //diagnosis
	  Response saveDiagnosis(Diagnosis diagnosis);
	  Response updateDiagnosis(Diagnosis diagnosis);
	  DiagnosisList getDiagnosisList(String email);
	  Response deleteDiagnosis(String email, String list);

}
