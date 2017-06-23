package cse.mlab.hvr.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DiagnosisList implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<Diagnosis> diagnosisList = new ArrayList<>();

	public List<Diagnosis> getDiagnosisList() {
		return diagnosisList;
	}

}
