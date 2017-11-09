package cse.mlab.hvr.client;

import java.util.Date;
import java.util.LinkedList;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.constants.ButtonSize;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.client.events.FileUploadEvent;
import cse.mlab.hvr.client.fragments.AudioBasedFragment;
import cse.mlab.hvr.client.fragments.ButtonControlledTextFragment;
import cse.mlab.hvr.client.fragments.CommonInstructionFragment;
import cse.mlab.hvr.client.fragments.TimerControlledImageFragment;
import cse.mlab.hvr.client.fragments.TimerControlledTextFragment;

public class AudioBasedCustomPlayerHtml5 extends Composite {

	@UiField
	Heading playerHeader;
	@UiField
	HTMLPanel userInterfaceInstructionPanel, fragmentInstructionPanel;

	@UiField
	Label commonInstructionText;

	@UiField
	Button buttonStartTest, buttonStartOver, buttonUpload;

	Label speechTestText;
	Button buttonNext;

	private String header;
	private int subtestId;
	private String startTime;
	private String endTime;
	private long startTimeInMillis;
	private String splitString = "";
	private int retakeCounter = 0;
	private AudioBasedFragment[] fragments;
	private AudioBasedFragment currentFragment = new AudioBasedFragment();
	private LinkedList<AudioBasedFragment> fragmentsLinkedList;

//	private Sound sound;
//	private Audio audio;
//	// create sound controller
//	private SoundController soundController = new SoundController();
//	private SoundHandler soundHandler;

	private boolean playerLoaded = false;
	private Div timerDiv = new Div();

	private Timer timerForBlockingInfiniteRecording;

	private static AudioBasedCustomPlayerUiBinder uiBinder = GWT
			.create(AudioBasedCustomPlayerUiBinder.class);

	interface AudioBasedCustomPlayerUiBinder extends
			UiBinder<Widget, AudioBasedCustomPlayerHtml5> {
	}

	public AudioBasedCustomPlayerHtml5(String header, int subtestId,
			AudioBasedFragment[] fragments) {
		initWidget(uiBinder.createAndBindUi(this));
		this.header = header;
		this.subtestId = subtestId;
		this.fragments = fragments;
		fragmentsLinkedList = new LinkedList<>();
		commonInstructionText.setStyleName("h4");
		reloadFragments();
	}

	public void stopPlayer() {
//		try {
//			if (sound != null) {
//				sound.stop();
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//
//		try {
//			if (audio != null) {
//				audio.getAudioElement().setCurrentTime(audio.getDuration());
//				audio.getAudioElement().pause();
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		}

		try {
			stopRecordingJS(this);
		} catch (Exception e) {
			// TODO: handle exception
		}

		try {
			this.removeFromParent();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void reloadFragments() {
		if (fragmentsLinkedList.isEmpty()) {
			for (int i = 0; i < fragments.length; i++) {
				fragmentsLinkedList.addLast(fragments[i]);
			}
		}
	}

	@Override
	protected void onLoad() {
		// TODO Auto-generated method stub
		super.onLoad();
		if (!playerLoaded) {
			playerHeader.setText(header);
			resetUI();
			reloadFragments();
			playerLoaded = true;
			initializeJSFunctions(this);
		}
	}

	protected String getHeader() {
		return this.header;
	}

	protected int getSubtestId() {
		return this.subtestId;
	}

	protected int getRetakeCounter() {
		return this.retakeCounter;
	}

	protected String getStartTime() {
		return this.startTime;
	}

	protected String getEndTime() {
		return this.endTime;
	}
	
	protected String getSplitString() {
		return this.splitString;
	}

	private String getCurrentTime() {
		try {
			Date date = new Date();
			DateTimeFormat formatter = DateTimeFormat
					.getFormat("yyyy-MM-dd HH:mm:ss");
			return formatter.format(date);
		} catch (Exception e) {
			// TODO: handle exception
			return "";
		}
	}

	private void resetUI() {
		// hide upload and retake button
		buttonUpload.setActive(true);
		buttonStartOver.setActive(true);
		buttonStartOver.setVisible(false);
		buttonUpload.setVisible(false);

		commonInstructionText.setText("");
		fragmentInstructionPanel.clear();
		userInterfaceInstructionPanel.clear();
		userInterfaceInstructionPanel.add(buttonStartTest);

	}

	@UiHandler("buttonStartTest")
	void startSpeechTest(ClickEvent event) {
		if (startTime == null) {
			startTime = getCurrentTime();
			startTimeInMillis = System.currentTimeMillis();
			splitString = "";
		}

		userInterfaceInstructionPanel.clear();
		speechTestText = new Label();
		speechTestText.addStyleName("h1");
		buttonNext = new Button("Next");
		buttonNext.setType(ButtonType.SUCCESS);
		buttonNext.setSize(ButtonSize.LARGE);
		buttonNext.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				activateNextFragment();
			}
		});
		// start recording
		// activate next fragment
		activateNextFragment();
		startRecordingJS(this);

	}

	public native void startRecordingJS(AudioBasedCustomPlayerHtml5 player)/*-{
		var name = player.@cse.mlab.hvr.client.AudioBasedCustomPlayerHtml5::header;
		console.log("final name:"+ name);
//		$wnd.FWRecorder.configure(44, 1, 0, 100);
//		$wnd.FWRecorder.record(name, name.concat(".wav"));
		$wnd.Html5Recorder.startRecording(name);
	}-*/;

	public native void stopRecordingJS(AudioBasedCustomPlayerHtml5 player)/*-{
		var name = player.@cse.mlab.hvr.client.AudioBasedCustomPlayerHtml5::header;
		console.log("will stop recording from dys test player ".concat(name))

//		$wnd.FWRecorder.stopRecording(name);
		$wnd.Html5Recorder.stopRecording();

	}-*/;

	private void activateNextFragment() {
		fragmentInstructionPanel.clear();
		userInterfaceInstructionPanel.clear();
		if (fragmentsLinkedList.isEmpty()) {
			stopRecordingJS(this);

			// no more fragments, stop recording and update to next player
			commonInstructionText
					.setText("Retake the test if you have interrupted during last test, otherwise continue.");
			// show upload and retake button
			buttonStartOver.setVisible(true);
			//upload button will be visible by js event
//			buttonUpload.setVisible(true);

		} else {
			
			currentFragment = fragmentsLinkedList.poll();
//			consolelog("type of the fragment:"+ currentFragment.getClass());
			if (currentFragment instanceof CommonInstructionFragment) {
				commonInstructionText
						.setText(((CommonInstructionFragment) currentFragment)
								.getText());
//				consolelog("common instruction audio :"+ currentFragment.getInstructionAudio());
				loadAudioInstruction(currentFragment.getInstructionAudio());
			} else if (currentFragment instanceof ButtonControlledTextFragment) {
				try {
					if (currentFragment.getInstructionAudio() == null
							|| currentFragment.getInstructionAudio().isEmpty()) {
						String fragmentText = ((ButtonControlledTextFragment) currentFragment)
								.getText();
						if (fragmentText != null && !fragmentText.isEmpty()) {
							speechTestText.setText(fragmentText);
						}
						fragmentInstructionPanel.add(speechTestText);
					} else {
						loadAudioInstruction(currentFragment
								.getInstructionAudio());
					}

					if (((ButtonControlledTextFragment) currentFragment)
							.getDurationToShowButton() >= 0) {
						Timer timer = new Timer() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								userInterfaceInstructionPanel.add(buttonNext);
							}
						};
						timer.schedule(((ButtonControlledTextFragment) currentFragment)
								.getDurationToShowButton());
					}

				} catch (Exception e) {
					// TODO: handle exception
					Window.alert("exception in showing button : "
							+ e.getMessage());
				}
			} else if (currentFragment instanceof TimerControlledTextFragment) {
				if (currentFragment.getInstructionAudio() == null || currentFragment.getInstructionAudio().isEmpty()) {
					String fragmentText = ((TimerControlledTextFragment) currentFragment).getText();
					if (fragmentText != null && !fragmentText.isEmpty()) {
						speechTestText.setText(fragmentText);
					}
					fragmentInstructionPanel.add(speechTestText);
				} else {
					loadAudioInstruction(currentFragment.getInstructionAudio());
				}

				if (((TimerControlledTextFragment) currentFragment)
						.getTimerDuration() >= 0) {
					timerDiv.removeFromParent();
					timerDiv = new Div();
					timerDiv.setId("timer_circle");
					timerDiv.setStyleName("center-block");
				}
			} else if (currentFragment instanceof TimerControlledImageFragment) {
				loadAudioInstruction(currentFragment.getInstructionAudio());
				Image displayImage = new Image(
						((TimerControlledImageFragment) currentFragment)
								.getImageURL());
				fragmentInstructionPanel.add(displayImage);
				if (((TimerControlledImageFragment) currentFragment)
						.getTimerDuration() >= 0) {
					timerDiv.removeFromParent();
					timerDiv = new Div();
					timerDiv.setId("timer_circle");
					timerDiv.setStyleName("center-block");
				}
			}
		}
	}

	private void animateTimer() {
		if (currentFragment instanceof TimerControlledTextFragment
				|| currentFragment instanceof TimerControlledImageFragment) {
			userInterfaceInstructionPanel.add(timerDiv);
			int duration = 0;
			if (currentFragment instanceof TimerControlledTextFragment) {
				duration = ((TimerControlledTextFragment) currentFragment)
						.getTimerDuration();
			} else {
				duration = ((TimerControlledImageFragment) currentFragment)
						.getTimerDuration();
			}
			animateTimerJS(String.valueOf(duration));
			Timer timer = new Timer() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					timerDiv.removeFromParent();
					userInterfaceInstructionPanel.clear();
					activateNextFragment();
				}
			};
			timer.schedule(duration + 1000);
		}
	}

	public native void animateTimerJS(String duration)/*-{
		$wnd.animateCircleTimer(duration);
	}-*/;

	
	public native void playHtml5Audio(AudioBasedCustomPlayerHtml5 player, String soundPath)/*-{
		console.log("audio will run from html5");
		player.@cse.mlab.hvr.client.AudioBasedCustomPlayerHtml5::muteRecorder()();
		var audio = new Audio();
		audio.src = soundPath;
		audio.onloadeddata = function(){
			audio.play();
		}
		
		audio.onended = function(){
			//alert("audio playback has been finished");
			player.@cse.mlab.hvr.client.AudioBasedCustomPlayerHtml5::audioInstructionCompleteAction()();
		}
	}-*/;
	
	private void audioInstructionCompleteAction(){
		resumeRecorder();
		if (currentFragment instanceof CommonInstructionFragment) {
			activateNextFragment();
		} else if(currentFragment instanceof ButtonControlledTextFragment){
			String fragmentText = ((ButtonControlledTextFragment) currentFragment).getText();
			if (fragmentText != null && !fragmentText.isEmpty()) {
				speechTestText.setText(fragmentText);
			}
			fragmentInstructionPanel.add(speechTestText);
			
		}else if (currentFragment instanceof TimerControlledTextFragment) {
			String fragmentText = ((TimerControlledTextFragment) currentFragment).getText();
			if (fragmentText != null && !fragmentText.isEmpty()) {
				speechTestText.setText(fragmentText);
			}
			fragmentInstructionPanel.add(speechTestText);
			animateTimer();
		} else if (currentFragment instanceof TimerControlledImageFragment) {
			animateTimer();
		}
	}

	private void loadAudioInstruction(String soundPath) {
		if (soundPath.isEmpty() || soundPath.equals("")) {
			if (currentFragment instanceof CommonInstructionFragment) {
				activateNextFragment();
			}
		} else{
			consolelog("going to play audio instruction "+ soundPath);
			//also going to pause recording, therefore we need to add into the split string
			try{
				consolelog("next fragment:"+ System.currentTimeMillis());
				if(startTimeInMillis >0){
					long temp = System.currentTimeMillis() - startTimeInMillis;
					splitString = splitString + "|" + temp;
				}
			}catch (Exception e) {
				// TODO: handle exception
			}
			playHtml5Audio(this, soundPath);			
		}
		
	}

	public native void muteRecorder()/*-{
//		$wnd.FWRecorder.configure(44, 0.1, 0, 0.1);
		$wnd.Html5Recorder.pauseRecording();
	}-*/;

	public native void resumeRecorder()/*-{
//		$wnd.FWRecorder.configure(44, 90, 0, 0.1);
		$wnd.Html5Recorder.resumeRecording();
	}-*/;

	@UiHandler("buttonUpload")
	void uploadButtonClicked(ClickEvent event) {
		buttonUpload.setActive(false);
		buttonStartOver.setActive(false);
		// uploadRecording(this);
		consolelog("final string:"+ splitString);
		endTime = getCurrentTime();
		playerLoaded = false;
		Hvr.getEventBus().fireEvent(new FileUploadEvent());
	}

	@UiHandler("buttonStartOver")
	void retakeSpeechTest(ClickEvent event) {
		retakeCounter++;
		startTime = null;
		splitString = "";
		reloadFragments();
		resetUI();
	}
	
	private void enableUploadButton(){
		consolelog("call to visible upload button");
		buttonUpload.setVisible(true);
	}
	
	
	public native void initializeJSFunctions(AudioBasedCustomPlayerHtml5 player)/*-{
		$wnd.enableRecordingEnding = $entry(function() {
			console.log("what is the problem to print it");
			player.@cse.mlab.hvr.client.AudioBasedCustomPlayerHtml5::enableUploadButton()();
		});
		
	}-*/;

//	public static native void uploadRecording(AudioBasedCustomPlayerHtml5 player)/*-{
//		var name = player.@cse.mlab.hvr.client.AudioBasedCustomPlayer::header;
//		$wnd.uploadTest(name);
//	}-*/;

	public native void consolelog(String msg)/*-{
		console.log(msg);
	}-*/;
}
