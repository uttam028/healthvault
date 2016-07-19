package cse.mlab.hvr.client.fragments;

public class TimerControlledTextFragment extends AudioBasedFragment{

	int timerDuration;
	public TimerControlledTextFragment(String audioInstructionFileName, int timerDuration) {
		// TODO Auto-generated constructor stub
		super(audioInstructionFileName);
		this.timerDuration = timerDuration;
	}
	public int getTimerDuration() {
		return timerDuration;
	}
}
