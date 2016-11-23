package cse.mlab.hvr.client.fragments.old;

public class Fragment {

	private int duration;
	private String type;
	private String instructionText;
	public Fragment(int duration, String type) {
		// TODO Auto-generated constructor stub
		this.duration = duration;
		this.type = type;
		this.instructionText = "";
	}
	
	public Fragment(int duration, String type, String instructionText) {
		// TODO Auto-generated constructor stub
		this.duration = duration;
		this.type = type;
		this.instructionText = instructionText;
	}
	
	public int getDuration() {
		return duration;
	}
	public String getType() {
		return type;
	}
	public String getInstructionText() {
		return instructionText;
	}
}
