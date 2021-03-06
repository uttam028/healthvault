package cse.mlab.hvr.client;

import com.google.gwt.user.client.ui.Composite;

public class AudioBasedCustomPlayer extends Composite {

//	@UiField
//	Heading playerHeader;
//	@UiField
//	HTMLPanel userInterfaceInstructionPanel, fragmentInstructionPanel;
//
//	@UiField
//	Label commonInstructionText;
//
//	@UiField
//	Button buttonStartTest, buttonStartOver, buttonUpload;
//
//	Label speechTestText;
//	Button buttonNext;
//
//	private String header;
//	private int subtestId;
//	private String startTime;
//	private String endTime;
//	private int retakeCounter = 0;
//	private AudioBasedFragment[] fragments;
//	private AudioBasedFragment currentFragment = new AudioBasedFragment();
//	private LinkedList<AudioBasedFragment> fragmentsLinkedList;
//
//	private Sound sound;
//	private Audio audio;
//	// create sound controller
//	private SoundController soundController = new SoundController();
//	private SoundHandler soundHandler;
//
//	private boolean playerLoaded = false;
//	private Div timerDiv = new Div();
//
//	private Timer timerForBlockingInfiniteRecording;
//
//	private static AudioBasedCustomPlayerUiBinder uiBinder = GWT
//			.create(AudioBasedCustomPlayerUiBinder.class);
//
//	interface AudioBasedCustomPlayerUiBinder extends
//			UiBinder<Widget, AudioBasedCustomPlayer> {
//	}
//
//	public AudioBasedCustomPlayer(String header, int subtestId,
//			AudioBasedFragment[] fragments) {
//		initWidget(uiBinder.createAndBindUi(this));
//		this.header = header;
//		this.subtestId = subtestId;
//		this.fragments = fragments;
//		fragmentsLinkedList = new LinkedList<>();
//		commonInstructionText.setStyleName("h4");
//		reloadFragments();
//	}
//
//	public void stopPlayer() {
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
//
//		try {
//			stopRecordingJS(this);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//
//		try {
//			this.removeFromParent();
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
//
//	private void reloadFragments() {
//		if (fragmentsLinkedList.isEmpty()) {
//			for (int i = 0; i < fragments.length; i++) {
//				fragmentsLinkedList.addLast(fragments[i]);
//			}
//		}
//	}
//
//	@Override
//	protected void onLoad() {
//		// TODO Auto-generated method stub
//		super.onLoad();
//		if (!playerLoaded) {
//			playerHeader.setText(header);
//			resetUI();
//			reloadFragments();
//			playerLoaded = true;
//		}
//	}
//
//	protected String getHeader() {
//		return this.header;
//	}
//
//	protected int getSubtestId() {
//		return this.subtestId;
//	}
//
//	protected int getRetakeCounter() {
//		return this.retakeCounter;
//	}
//
//	protected String getStartTime() {
//		return this.startTime;
//	}
//
//	protected String getEndTime() {
//		return this.endTime;
//	}
//
//	private String getCurrentTime() {
//		try {
//			Date date = new Date();
//			DateTimeFormat formatter = DateTimeFormat
//					.getFormat("yyyy-MM-dd HH:mm:ss");
//			return formatter.format(date);
//		} catch (Exception e) {
//			// TODO: handle exception
//			return "";
//		}
//	}
//
//	private void resetUI() {
//		// hide upload and retake button
//		buttonStartOver.setVisible(false);
//		buttonUpload.setVisible(false);
//
//		commonInstructionText.setText("");
//		fragmentInstructionPanel.clear();
//		userInterfaceInstructionPanel.clear();
//		userInterfaceInstructionPanel.add(buttonStartTest);
//
//	}
//
//	@UiHandler("buttonStartTest")
//	void startSpeechTest(ClickEvent event) {
//		if (startTime == null) {
//			startTime = getCurrentTime();
//		}
//
//		userInterfaceInstructionPanel.clear();
//		speechTestText = new Label();
//		speechTestText.addStyleName("h1");
//		buttonNext = new Button("Next");
//		buttonNext.setType(ButtonType.SUCCESS);
//		buttonNext.setSize(ButtonSize.LARGE);
//		buttonNext.addClickHandler(new ClickHandler() {
//
//			@Override
//			public void onClick(ClickEvent event) {
//				// TODO Auto-generated method stub
//				activateNextFragment();
//			}
//		});
//		// start recording
//		// activate next fragment
//		activateNextFragment();
//		startRecordingJS(this);
//
//	}
//
//	public native void startRecordingJS(AudioBasedCustomPlayer player)/*-{
//		var name = player.@cse.mlab.hvr.client.AudioBasedCustomPlayer::header;
//		$wnd.FWRecorder.configure(44, 1, 0, 100);
//		$wnd.FWRecorder.record(name, name.concat(".wav"));
//
//	}-*/;
//
//	public native void stopRecordingJS(AudioBasedCustomPlayer player)/*-{
//		var name = player.@cse.mlab.hvr.client.AudioBasedCustomPlayer::header;
//		console.log("will stop recording from dys test player ".concat(name))
//
//		$wnd.FWRecorder.stopRecording(name);
//
//	}-*/;
//
//	private void activateNextFragment() {
//		fragmentInstructionPanel.clear();
//		userInterfaceInstructionPanel.clear();
//		if (fragmentsLinkedList.isEmpty()) {
//			// no more fragments, stop recording and update to next player
//			commonInstructionText
//					.setText("Retake the test if you have interrupted during last test, otherwise continue.");
//			// show upload and retake button
//			buttonStartOver.setVisible(true);
//			buttonUpload.setVisible(true);
//
//			stopRecordingJS(this);
//
//		} else {
//			currentFragment = fragmentsLinkedList.poll();
//			if (currentFragment instanceof CommonInstructionFragment) {
//				commonInstructionText
//						.setText(((CommonInstructionFragment) currentFragment)
//								.getText());
//				loadAudioInstruction(currentFragment.getInstructionAudio());
//			} else if (currentFragment instanceof ButtonControlledTextFragment) {
//				try {
//					if (currentFragment.getInstructionAudio() == null
//							|| currentFragment.getInstructionAudio().isEmpty()) {
//						String fragmentText = ((ButtonControlledTextFragment) currentFragment)
//								.getText();
//						if (fragmentText != null && !fragmentText.isEmpty()) {
//							speechTestText.setText(fragmentText);
//						}
//						fragmentInstructionPanel.add(speechTestText);
//					} else {
//						loadAudioInstruction(currentFragment
//								.getInstructionAudio());
//					}
//
//					if (((ButtonControlledTextFragment) currentFragment)
//							.getDurationToShowButton() >= 0) {
//						Timer timer = new Timer() {
//
//							@Override
//							public void run() {
//								// TODO Auto-generated method stub
//								userInterfaceInstructionPanel.add(buttonNext);
//							}
//						};
//						timer.schedule(((ButtonControlledTextFragment) currentFragment)
//								.getDurationToShowButton());
//					}
//
//				} catch (Exception e) {
//					// TODO: handle exception
//					Window.alert("exception in showing button : "
//							+ e.getMessage());
//				}
//			} else if (currentFragment instanceof TimerControlledTextFragment) {
//				if (currentFragment.getInstructionAudio() == null || currentFragment.getInstructionAudio().isEmpty()) {
//					String fragmentText = ((TimerControlledTextFragment) currentFragment).getText();
//					if (fragmentText != null && !fragmentText.isEmpty()) {
//						speechTestText.setText(fragmentText);
//					}
//					fragmentInstructionPanel.add(speechTestText);
//				} else {
//					loadAudioInstruction(currentFragment.getInstructionAudio());
//				}
//
//				if (((TimerControlledTextFragment) currentFragment)
//						.getTimerDuration() >= 0) {
//					timerDiv.removeFromParent();
//					timerDiv = new Div();
//					timerDiv.setId("timer_circle");
//					timerDiv.setStyleName("center-block");
//				}
//			} else if (currentFragment instanceof TimerControlledImageFragment) {
//				loadAudioInstruction(currentFragment.getInstructionAudio());
//				Image displayImage = new Image(
//						((TimerControlledImageFragment) currentFragment)
//								.getImageURL());
//				fragmentInstructionPanel.add(displayImage);
//				if (((TimerControlledImageFragment) currentFragment)
//						.getTimerDuration() >= 0) {
//					timerDiv.removeFromParent();
//					timerDiv = new Div();
//					timerDiv.setId("timer_circle");
//					timerDiv.setStyleName("center-block");
//				}
//			}
//		}
//	}
//
//	private void animateTimer() {
//		if (currentFragment instanceof TimerControlledTextFragment
//				|| currentFragment instanceof TimerControlledImageFragment) {
//			userInterfaceInstructionPanel.add(timerDiv);
//			int duration = 0;
//			if (currentFragment instanceof TimerControlledTextFragment) {
//				duration = ((TimerControlledTextFragment) currentFragment)
//						.getTimerDuration();
//			} else {
//				duration = ((TimerControlledImageFragment) currentFragment)
//						.getTimerDuration();
//			}
//			animateTimerJS(String.valueOf(duration));
//			Timer timer = new Timer() {
//
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					timerDiv.removeFromParent();
//					userInterfaceInstructionPanel.clear();
//					activateNextFragment();
//				}
//			};
//			timer.schedule(duration + 1000);
//		}
//	}
//
//	public native void animateTimerJS(String duration)/*-{
//		$wnd.animateCircleTimer(duration);
//	}-*/;
//
//	private void loadAudioInstruction(String soundPath) {
//		if (soundPath.isEmpty() || soundPath.equals("")) {
//			if (currentFragment instanceof CommonInstructionFragment) {
//				activateNextFragment();
//			}
//		} else {
//
//			// create a sound
//			if (Hvr.getBrowserName().toLowerCase().contains("firefox")) {
//				// sound =
//				// soundController.createSound(Sound.MIME_TYPE_AUDIO_BASIC,
//				// soundPath);
//				// final Audio audio = Audio.createIfSupported();
//				audio = Audio.createIfSupported();
//				audio.setSrc(soundPath);
//				final AudioElement element = audio.getAudioElement();
//				// Window.alert("audio supported:"+ Audio.isSupported()+
//				// ", duration:"+ audio.getDuration() + ",pr" +
//				// audio.getPlaybackRate() + ",inittime:"+
//				// audio.getInitialTime());
//				muteRecorder();
//				// audio.setVolume(100);
//				// audio.play();
//				element.play();
//
//				// Window.alert("duration just before:"+ element.getDuration());
//				Timer toLoad = new Timer() {
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						Timer timer = new Timer() {
//
//							@Override
//							public void run() {
//								// Window.alert("completed sound:"+
//								// audio.getDuration());
//								resumeRecorder();
//								if (currentFragment instanceof CommonInstructionFragment) {
//									activateNextFragment();
//								} else if(currentFragment instanceof ButtonControlledTextFragment){
//									String fragmentText = ((ButtonControlledTextFragment) currentFragment).getText();
//									if (fragmentText != null && !fragmentText.isEmpty()) {
//										speechTestText.setText(fragmentText);
//									}
//									fragmentInstructionPanel.add(speechTestText);
//									
//								}else if (currentFragment instanceof TimerControlledTextFragment) {
//									String fragmentText = ((TimerControlledTextFragment) currentFragment).getText();
//									if (fragmentText != null && !fragmentText.isEmpty()) {
//										speechTestText.setText(fragmentText);
//									}
//									fragmentInstructionPanel.add(speechTestText);
//									animateTimer();
//								} else if (currentFragment instanceof TimerControlledImageFragment) {
//									animateTimer();
//								}
//							}
//
//						};
//						timer.schedule((int) (element.getDuration() * 1000));
//
//					}
//				};
//				toLoad.schedule(500);
//			} else {
//				sound = soundController.createSound(
//						Sound.MIME_TYPE_AUDIO_MPEG_MP3, soundPath);
//				if (soundHandler != null) {
//					sound.removeEventHandler(soundHandler);
//				}
//				soundHandler = new SoundHandler() {
//
//					@Override
//					public void onSoundLoadStateChange(
//							SoundLoadStateChangeEvent event) {
//						// TODO Auto-generated method stub
//
//						// See detailed documentation in Sound.LoadState
//						// in order to understand these possible values:
//						// LOAD_STATE_SUPPORTED_AND_READY
//						// LOAD_STATE_SUPPORTED_NOT_READY
//						// LOAD_STATE_SUPPORTED_MAYBE_READY
//						// LOAD_STATE_NOT_SUPPORTED
//						// LOAD_STATE_SUPPORT_NOT_KNOWN
//						// LOAD_STATE_UNINITIALIZED
//						// Window.alert("load state:"+
//						// event.getLoadState().name() + "agent:"+
//						// Window.Navigator.getUserAgent().toLowerCase());
//						muteRecorder();
//						// sound.setVolume(100);
//						sound.play();
//					}
//
//					@Override
//					public void onPlaybackComplete(PlaybackCompleteEvent event) {
//						// TODO Auto-generated method stub
//						// Window.alert("playback completed");
//						resumeRecorder();
//						if (currentFragment instanceof CommonInstructionFragment) {
//							activateNextFragment();
//						} else if(currentFragment instanceof ButtonControlledTextFragment){
//							String fragmentText = ((ButtonControlledTextFragment) currentFragment).getText();
//							if (fragmentText != null && !fragmentText.isEmpty()) {
//								speechTestText.setText(fragmentText);
//							}
//							fragmentInstructionPanel.add(speechTestText);
//							
//						} else if (currentFragment instanceof TimerControlledTextFragment) {
//							String fragmentText = ((TimerControlledTextFragment) currentFragment).getText();
//							if (fragmentText != null && !fragmentText.isEmpty()) {
//								speechTestText.setText(fragmentText);
//							}
//							fragmentInstructionPanel.add(speechTestText);
//							animateTimer();
//						} else if (currentFragment instanceof TimerControlledImageFragment) {
//							animateTimer();
//						}
//					}
//				};
//				sound.addEventHandler(soundHandler);
//			}
//
//		}
//	}
//
//	public native void muteRecorder()/*-{
//		$wnd.FWRecorder.configure(44, 0.1, 0, 0.1);
//	}-*/;
//
//	public native void resumeRecorder()/*-{
//		$wnd.FWRecorder.configure(44, 90, 0, 0.1);
//	}-*/;
//
//	@UiHandler("buttonUpload")
//	void uploadButtonClicked(ClickEvent event) {
//		// uploadRecording(this);
//		endTime = getCurrentTime();
//		playerLoaded = false;
//		Hvr.getEventBus().fireEvent(new FileUploadEvent());
//	}
//
//	@UiHandler("buttonStartOver")
//	void retakeSpeechTest(ClickEvent event) {
//		retakeCounter++;
//		startTime = null;
//		reloadFragments();
//		resetUI();
//	}
//
//	public static native void uploadRecording(AudioBasedCustomPlayer player)/*-{
//		var name = player.@cse.mlab.hvr.client.AudioBasedCustomPlayer::header;
//		$wnd.uploadTest(name);
//	}-*/;

}
