package cse.mlab.hvr.client.fragments;

public class AudioBasedFragment {
	String instructionAudio;
	
	public AudioBasedFragment() {
		// TODO Auto-generated constructor stub
	}
	public AudioBasedFragment(String audioInstructionFileName) {
		// TODO Auto-generated constructor stub
		this.instructionAudio = audioInstructionFileName;
	}
	public String getInstructionAudio() {
		return instructionAudio;
	}
}
