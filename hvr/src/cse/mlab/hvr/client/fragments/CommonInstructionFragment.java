package cse.mlab.hvr.client.fragments;

public class CommonInstructionFragment extends AudioBasedFragment{
	String text;
	
	public CommonInstructionFragment(String audioInstructionFileName, String text) {
		// TODO Auto-generated constructor stub
		super(audioInstructionFileName);
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
}
