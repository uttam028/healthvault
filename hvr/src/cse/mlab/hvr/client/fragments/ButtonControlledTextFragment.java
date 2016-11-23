package cse.mlab.hvr.client.fragments;

public class ButtonControlledTextFragment extends AudioBasedFragment{

	int durationToShowButton;
	String text;
	
	public ButtonControlledTextFragment(String audioInstructionFileName, String text, int durationToShowButton) {
		// TODO Auto-generated constructor stub
		super(audioInstructionFileName);
		this.text = text;
		this.durationToShowButton = durationToShowButton;
	}
	
	public String getText() {
		return text;
	}
	public int getDurationToShowButton() {
		return durationToShowButton;
	}
	@Override
	public boolean hasText() {
		// TODO Auto-generated method stub
		return true;
	}
}
