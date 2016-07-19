package cse.mlab.hvr.client.fragments;

import cse.mlab.hvr.shared.speechtest.TestFragment;

public class FragmentFactory {
	private static FragmentFactory instance = null;
	private FragmentFactory() {
		// TODO Auto-generated constructor stub
		
	}
	public static FragmentFactory getInstance(){
		if (instance == null) {
			instance = new FragmentFactory();
		}
		return instance;
	}
	
	public AudioBasedFragment getPlayerFragment(TestFragment testFragment){
		if(testFragment.isNextButtonAvailable() && !isEmpty(testFragment.getText())){
			return new ButtonControlledTextFragment(testFragment.getAudioFileName(), testFragment.getText(), testFragment.getDurationToShowNextButton());
		} else if (testFragment.isTimerAvailable() && !isEmpty(testFragment.getImageFileName())) {
			return new TimerControlledImageFragment(testFragment.getAudioFileName(), testFragment.getImageFileName(), testFragment.getDurationOfTimer());
		} else if(testFragment.isTimerAvailable()){
			return new TimerControlledTextFragment(testFragment.getAudioFileName(), testFragment.getDurationOfTimer());
		} else {
			return null;
		}
	}
	
	private static boolean isEmpty(String text){
		if(text == null || text.isEmpty()){
			return true;
		}
		return false;
	}
}
