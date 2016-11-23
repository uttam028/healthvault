package cse.mlab.hvr.client.fragments;

public class TimerControlledTextFragment extends AudioBasedFragment{

	int timerDuration;
	String text;
	public TimerControlledTextFragment(String audioInstructionFileName, String text, int timerDuration) {
		// TODO Auto-generated constructor stub
		super(audioInstructionFileName);
		this.timerDuration = timerDuration;
		this.text = text;
	}
	public int getTimerDuration() {
		return timerDuration;
	}
	public String getText() {
		return text;
	}
	@Override
	public boolean hasText() {
		// TODO Auto-generated method stub
		return true;
	}
}
