package com.phr.study;

import java.io.Serializable;

public class TestFragment implements Serializable{
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
	private boolean nextButtonAvailable;
	private int durationToShowNextButton;
	
	public TestFragment() {
		// TODO Auto-generated constructor stub
	}

	public TestFragment(int fragmentId, String text, String imageFileName,
			String audioFileName, boolean timerAvailable, int durationOfTimer,
			boolean nextButtonAvailable, int durationToShowNextButton) {
		super();
		this.fragmentId = fragmentId;
		this.text = text;
		this.imageFileName = imageFileName;
		this.audioFileName = audioFileName;
		this.timerAvailable = timerAvailable;
		this.durationOfTimer = durationOfTimer;
		this.nextButtonAvailable = nextButtonAvailable;
		this.durationToShowNextButton = durationToShowNextButton;
	}

	public int getFragmentId() {
		return fragmentId;
	}

	public void setFragmentId(int fragmentId) {
		this.fragmentId = fragmentId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String getAudioFileName() {
		return audioFileName;
	}

	public void setAudioFileName(String audioFileName) {
		this.audioFileName = audioFileName;
	}

	public boolean isTimerAvailable() {
		return timerAvailable;
	}

	public void setTimerAvailable(boolean timerAvailable) {
		this.timerAvailable = timerAvailable;
	}

	public int getDurationOfTimer() {
		return durationOfTimer;
	}

	public void setDurationOfTimer(int durationOfTimer) {
		this.durationOfTimer = durationOfTimer;
	}

	public boolean isNextButtonAvailable() {
		return nextButtonAvailable;
	}

	public void setNextButtonAvailable(boolean nextButtonAvailable) {
		this.nextButtonAvailable = nextButtonAvailable;
	}

	public int getDurationToShowNextButton() {
		return durationToShowNextButton;
	}

	public void setDurationToShowNextButton(int durationToShowNextButton) {
		this.durationToShowNextButton = durationToShowNextButton;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
