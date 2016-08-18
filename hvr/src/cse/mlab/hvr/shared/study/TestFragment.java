package cse.mlab.hvr.shared.study;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TestFragment implements Serializable, IsSerializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int fragmentId;
	private String text;
	private String imageFileName;
	private String audioFileName;
	private boolean timerAvailable;
	private int durationOfTimer;
	private boolean isNextButtonAvailable;
	private int durationToShowNextButton;
	
	public TestFragment() {
		// TODO Auto-generated constructor stub
	}

	public TestFragment(int fragmentId, String text, String imageFileName,
			String audioFileName, boolean timerAvailable, int durationOfTimer,
			boolean isNextButtonAvailable, int durationToShowNextButton) {
		super();
		this.fragmentId = fragmentId;
		this.text = text;
		this.imageFileName = imageFileName;
		this.audioFileName = audioFileName;
		this.timerAvailable = timerAvailable;
		this.durationOfTimer = durationOfTimer;
		this.isNextButtonAvailable = isNextButtonAvailable;
		this.durationToShowNextButton = durationToShowNextButton;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getFragmentId() {
		return fragmentId;
	}

	public String getText() {
		return text;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public String getAudioFileName() {
		return audioFileName;
	}

	public boolean isTimerAvailable() {
		return timerAvailable;
	}

	public int getDurationOfTimer() {
		return durationOfTimer;
	}

	public boolean isNextButtonAvailable() {
		return isNextButtonAvailable;
	}

	public int getDurationToShowNextButton() {
		return durationToShowNextButton;
	}

}
