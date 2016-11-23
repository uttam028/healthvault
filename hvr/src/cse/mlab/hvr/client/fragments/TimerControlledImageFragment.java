package cse.mlab.hvr.client.fragments;

public class TimerControlledImageFragment extends AudioBasedFragment {
	int timerDuration;
	String imageURL;

	public TimerControlledImageFragment(String audioInstructionFileName,
			String imageURL, int timerDuration) {
		// TODO Auto-generated constructor stub
		super(audioInstructionFileName);
		this.imageURL = imageURL;
		this.timerDuration = timerDuration;
	}

	public int getTimerDuration() {
		return timerDuration;
	}

	public String getImageURL() {
		return imageURL;
	}
	@Override
	public boolean hasText() {
		// TODO Auto-generated method stub
		return false;
	}
}
