package cse.mlab.hvr.shared;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Cholesterol implements Serializable {
	private static final long serialVersionUID = 57449867069477522L;
	private double hdl;
	private double ldl;
	private double triglycarides;
	private String unit;
	private String recordDate;

	public double getHdl() {
		return hdl;
	}

	public double getLdl() {
		return ldl;
	}

	public String getRecordDate() {
		return recordDate;
	}

	public double getTriglycarides() {
		return triglycarides;
	}

	public String getUnit() {
		return unit;
	}

}
