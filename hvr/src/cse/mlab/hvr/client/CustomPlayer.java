package cse.mlab.hvr.client;

import java.util.ArrayList;
import java.util.List;

import org.gwtbootstrap3.client.shared.event.ModalHideEvent;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.Image;
import org.gwtbootstrap3.client.ui.ImageAnchor;
import org.gwtbootstrap3.client.ui.Modal;
import org.gwtbootstrap3.client.ui.ModalBody;
import org.gwtbootstrap3.client.ui.constants.Toggle;
import org.gwtbootstrap3.client.ui.html.Br;
import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import fr.hd3d.html5.video.client.VideoSource;
import fr.hd3d.html5.video.client.VideoWidget;
import fr.hd3d.html5.video.client.events.VideoEndedEvent;
import fr.hd3d.html5.video.client.handlers.VideoEndedHandler;

public class CustomPlayer extends Composite {
	@UiField
	HTMLPanel customPlayerPanel, playerButtonPanel, playerInstructionPanel,
			testInstructionPanel, fragmentInstructionPanel, playerTextPanel,
			playerAnimationPanel;

	@UiField
	ImageAnchor playSampleButton;

	Label playerInstructionText = new Label();
	Label testInstructionText = new Label();
	static String defaultInstructionText = "Record the word/sentence appear only within box";
	Label fragmentInstructionText = new Label();
	Label textToRecord = new Label();
	@UiField
	Label playerButtonText;

	ImageAnchor recordAnchor, stopAnchor, playAnchor, pauseAnchor;

	@UiField
	Heading playerHeader;

	@UiField
	Button buttonStartOver, buttonUpload;

	@UiField
	Modal videoSampleModal;

	@UiField
	ModalBody videoSampleModalBody;

	String header = "";
	int fullDuration = 0;
	Fragment[] fragments;
	String sampleUrl;

	VideoWidget videoPlayer;
	
	Div timerDiv = new Div();


	// Sound sound;

	private static CustomPlayerUiBinder uiBinder = GWT
			.create(CustomPlayerUiBinder.class);

	interface CustomPlayerUiBinder extends UiBinder<Widget, CustomPlayer> {
	}

	public CustomPlayer(String header, Fragment[] fragments, String sampleUrl,
			String textAboutTest) {
		initWidget(uiBinder.createAndBindUi(this));
		this.header = header;
		this.fragments = fragments;
		this.sampleUrl = sampleUrl;
		this.playerInstructionText.setText(textAboutTest);
		// this.loadSoundSample();
		// this.loadVideoSample();
		this.playSampleButton.setDataTarget("#" + videoSampleModal.getId());
		this.playSampleButton.setDataToggle(Toggle.MODAL);
	}

	@UiHandler("videoSampleModal")
	void videoModalClosed(ModalHideEvent event){
		//videoPlayer.playPause();
		//Window.alert("total duration:"+ videoPlayer.getDuration());
		videoPlayer.setCurrentTime(videoPlayer.getDuration());
		//videoPlayer.
	}
	
	public void loadVideoSample() {
		if (!this.sampleUrl.isEmpty()) {
			videoPlayer = new VideoWidget(true, true, null);
			List<VideoSource> sources = new ArrayList<VideoSource>();
			sources.add(new VideoSource(this.sampleUrl));
			// videoPlayer.playPause();
			videoPlayer.setAutoPlay(true);
			videoPlayer.setHeight("500");
			videoPlayer.setWidth("700");
			videoPlayer.setSources(sources);
			videoPlayer.setPixelSize(500, 400);

			videoPlayer.addEndedHandler(new VideoEndedHandler() {

				@Override
				public void onVideoEnded(VideoEndedEvent event) {
					// TODO Auto-generated method stub

				}
			});
		}
	}

	@UiHandler("playSampleButton")
	void playSpeechSample(ClickEvent event) {
		// old code, when audio sample was used
		// blockUIForAudioSample(this);
		// sound.play();
		if (!this.sampleUrl.isEmpty()) {
			this.loadVideoSample();
			videoSampleModalBody.clear();
			videoSampleModalBody.add(videoPlayer);
			customPlayerPanel.add(videoSampleModal);
		}
	}

	/*
	 * old code- audio sample was used void stopAudioSample(){ sound.stop(); }
	 * 
	 * public native void blockUIForAudioSample(CustomPlayer player)/*-{
	 * $wnd.blockPage(); $wnd.stopAudioSample = $entry(function() {
	 * player.@cse.mlab.hvr.client.CustomPlayer::stopAudioSample()(); });
	 * 
	 * }-
	 */;

	// public native void unblockUIFromAudioSample()/*-{
	// $wnd.unblockPage();
	// }-*/;

	/*
	 * old code- for audio sample private void loadSoundSample() {
	 * 
	 * // create sound controller SoundController soundController = new
	 * SoundController(); // create a sound sound = soundController.createSound(
	 * Sound.MIME_TYPE_AUDIO_MPEG_MP3, this.sampleUrl);
	 * 
	 * 
	 * // add a sound handler so we know when the sound has loaded
	 * sound.addEventHandler(new SoundHandler() {
	 * 
	 * public void onPlaybackComplete(PlaybackCompleteEvent event) { // WARNING:
	 * this method may in fact never be called; see // Sound.LoadState
	 * unblockUIFromAudioSample();
	 * 
	 * }
	 * 
	 * public void onSoundLoadStateChange(SoundLoadStateChangeEvent event) { //
	 * See detailed documentation in Sound.LoadState // in order to understand
	 * these possible values: // LOAD_STATE_SUPPORTED_AND_READY //
	 * LOAD_STATE_SUPPORTED_NOT_READY // LOAD_STATE_SUPPORTED_MAYBE_READY //
	 * LOAD_STATE_NOT_SUPPORTED // LOAD_STATE_SUPPORT_NOT_KNOWN //
	 * LOAD_STATE_UNINITIALIZED } }); }
	 */

	/*
	 * public CustomPlayer(String firstName) {
	 * initWidget(uiBinder.createAndBindUi(this)); }
	 */

	@UiHandler("buttonStartOver")
	void startOverButtonClicked(ClickEvent event) {
		playerButtonPanel.clear();
		playerButtonPanel.add(recordAnchor);

		// buttonStartOver.setEnabled(false);
		// buttonUpload.setEnabled(false);
		playSampleButton.setEnabled(true);
		buttonStartOver.setVisible(false);
		buttonUpload.setVisible(false);
		playerButtonText.setText("Click to start");

		updateTestInstructionText(defaultInstructionText);
		fragmentInstructionText.setText("");
	}

	void startRecording() {
		startRecordingJS(this);
	}

	public native void startRecordingJS(CustomPlayer player)/*-{
		var name = player.@cse.mlab.hvr.client.CustomPlayer::header;
		var timeout = player.@cse.mlab.hvr.client.CustomPlayer::fullDuration;
		console
				.log("will ask for permission/ start recording from custom player "
						.concat(name).concat(" and duration = ")
						.concat(timeout));
		//Recorder.recorder.configure(rate, gain, silenceLevel, silenceTimeout);
		$wnd.FWRecorder.configure(44, 1, 0, 100);
		$wnd.FWRecorder.record(name, name.concat(".wav"));
		$wnd.enableStopButton = $entry(function() {
			player.@cse.mlab.hvr.client.CustomPlayer::enableStopAnchor()();
			console.log("is there any chance to print it");
			setTimeout(
					function() {
						console.log("time out for this player = "
								.concat(timeout));
						player.@cse.mlab.hvr.client.CustomPlayer::stopRecording()();
						player.@cse.mlab.hvr.client.CustomPlayer::updateTestInstructionText(Ljava/lang/String;)("You can listen recording, if sound quality is ok upload the recording");
						player.@cse.mlab.hvr.client.CustomPlayer::clearPlayerTextPanel()();
					}, timeout);
		});
		$wnd.enablePlayButton = $entry(function() {
			console.log("what is the problem to print it");
			player.@cse.mlab.hvr.client.CustomPlayer::enablePlayAnchor()();
		});
		$wnd.enablePauseButton = $entry(function() {
			player.@cse.mlab.hvr.client.CustomPlayer::enablePauseAnchor()();
		});
	}-*/;

	public void replaceStopWithTimer(){
		playerButtonPanel.clear();
		//playerButtonPanel.add(stopAnchor);
		playerButtonPanel.add(new Br());
		timerDiv.removeFromParent();
		timerDiv = new Div();
		timerDiv.setId("timer_circle");
		timerDiv.setStyleName("center-block");
		playerButtonPanel.add(timerDiv);
		playerButtonText.setText("Recording...");
		
	}
	
	public void enableStopAnchor() {
		//playerButtonPanel.clear();
		////playerButtonPanel.add(stopAnchor);
		//timerDiv.removeFromParent();
		//timerDiv = new Div();
		//timerDiv.setId("timer_circle");
		//timerDiv.setStyleName("center-block");
		//playerButtonPanel.add(timerDiv);
		//playerButtonText.setText("Recording...");
		//// playerInstructionText.setText("Record displayed text");
		replaceStopWithTimer();

		int cumulitiveTimer = 0;
		// new MyTimer(this, this.fragments[0].text,
		// this.fragments[0].duration).schedule(cumulitiveTimer);
		new MyTimer(this, this.fragments[0]).schedule(cumulitiveTimer);
		for (int i = 1; i < this.fragments.length; i++) {
			cumulitiveTimer += this.fragments[i - 1].getDuration();
			// new MyTimer(this, this.fragments[i].text,
			// this.fragments[i].duration).schedule(cumulitiveTimer);
			new MyTimer(this, this.fragments[i]).schedule(cumulitiveTimer);
		}
	}

	/*
	 * public void updatePlayerInstructionText(String message) {
	 * playerInstructionText.setText(message); }
	 */

	public void updateTestInstructionText(String message) {
		testInstructionText.setText(message);
	}

	void updatePlayerHtmlPanel(Fragment fragment) {
		fragmentInstructionText.setText(fragment.getInstructionText());
		this.playerTextPanel.clear();
		if (fragment instanceof SimpleTextFragment) {
			playerTextPanel.add(textToRecord);
			textToRecord.setText(((SimpleTextFragment) fragment).getText());
		} else if (fragment instanceof MarkedTextFragment) {
			Div markedDiv = new Div();
			markedDiv.setId("marked_text");
			markedDiv.getElement().setInnerHTML(
					((MarkedTextFragment) fragment).getText());
			playerTextPanel.add(markedDiv);
			markIntonatedText(((MarkedTextFragment) fragment).getMarkedText());
		} else if (fragment instanceof MarqueeFragment) {
			Div marqDiv = new Div();
			marqDiv.setId("marquee_body");
			marqDiv.setStyleName("marquee");
			marqDiv.getElement().setInnerHTML(
					((MarqueeFragment) fragment).getText());
			playerTextPanel.add(marqDiv);
			startMarquee(((MarqueeFragment) fragment).getSpeed() + "");
		} else if (fragment instanceof ImageFragment) {
			Image image = new Image(((ImageFragment) fragment).getImageUrl());
			playerTextPanel.add(image);
		}
		if(fragment instanceof PauseFragment){
			this.playerTextPanel.add(new Br());
			this.playerTextPanel.add(new Br());
			this.playerTextPanel.add(new Br());
			this.playerTextPanel.add(new Br());
			playerAnimationPanel.clear();
			playerButtonText.setText("");
			playerButtonPanel.clear();
		} else{
			this.updatePlayerTimer(fragment.getDuration() + "");
		}
		
	}

	public native void markIntonatedText(String markedText)/*-{
		$wnd.colorMarkedText(markedText);
	}-*/;

	public native void startMarquee(String speed)/*-{

		$wnd.$('.marquee').marquee({
			duration : parseInt(speed),
			//easing : 'linear'
			duplicated : true,
			gap : 200
		});
	}-*/;

	void clearPlayerTextPanel() {
		this.playerTextPanel.clear();
		this.playerTextPanel.add(new Br());
		this.playerTextPanel.add(new Br());
		this.playerTextPanel.add(new Br());
		this.playerTextPanel.add(new Br());

		this.fragmentInstructionText.setText("");
	}

	void updatePlayerTimer(String duration) {
		playerAnimationPanel.clear();
		replaceStopWithTimer();
		//Div timerDiv = new Div();
		//timerDiv.setId("timer_circle");
		//timerDiv.setStyleName("center-block");
		//playerAnimationPanel.add(timerDiv);
		updatePlayerTimerJS(duration);
	}

	public native void updatePlayerTimerJS(String duration)/*-{

		console.log("time at here :" + parseInt(duration));
		$wnd.animateCircleTimer(duration);
		//var name = player.@cse.mlab.hvr.client.CustomPlayer::header;
		//console.log("will stop recording from custom player ".concat(name))

		//$wnd.FWRecorder.stopRecording(name);
	}-*/;

	void stopRecording() {
		stopRecordingJS(this);
	}

	public native void stopRecordingJS(CustomPlayer player)/*-{
		var name = player.@cse.mlab.hvr.client.CustomPlayer::header;
		console.log("will stop recording from custom player ".concat(name))

		$wnd.FWRecorder.stopRecording(name);
	}-*/;

	public static native void consoleTest(String msg)/*-{
		console.log(msg);
	}-*/;

	public void enablePlayAnchor() {
		playerButtonPanel.clear();
		playerButtonPanel.add(playAnchor);
		playerButtonText.setText("Click to Listen");

		playerAnimationPanel.clear();
		Div timerDiv = new Div();
		timerDiv.setId("timer_line");
		timerDiv.setStyleName("center-block");
		playerAnimationPanel.add(timerDiv);

		// buttonStartOver.setEnabled(true);
		// buttonUpload.setEnabled(true);
		playSampleButton.setEnabled(true);
		buttonStartOver.setVisible(true);
		buttonUpload.setVisible(true);
	}

	void playRecording() {
		playRecording(this);
	}

	public static native void playRecording(CustomPlayer player)/*-{
		var name = player.@cse.mlab.hvr.client.CustomPlayer::header;
		console.log("will playback from custom player ".concat(name))
		var duration = $wnd.FWRecorder.duration(name);
		$wnd.FWRecorder.playBack(name);
		$wnd.animateLineTimer(duration);
	}-*/;

	void enablePauseAnchor() {
		playerButtonPanel.clear();
		playerButtonPanel.add(pauseAnchor);
		playerButtonText.setText("Playing...");
	}

	@UiHandler("buttonUpload")
	void uploadButtonClicked(ClickEvent event) {
		uploadRecording(this);
		Hvr.getEventBus().fireEvent(new FileUploadSuccessEvent());
	}

	public static native void uploadRecording(CustomPlayer player)/*-{
		var name = player.@cse.mlab.hvr.client.CustomPlayer::header;
		console.log("will playback from custom player ".concat(name));
		//$wnd.uploadTest(name);
		$wnd.uploadTest_merge();
	}-*/;

	@Override
	protected void onLoad() {
		// TODO Auto-generated method stub
		super.onLoad();
		playerButtonPanel.clear();
		playerHeader.setText(this.header);

		consoleTest("going to print for player:" + this.header);
		for (int i = 0; i < this.fragments.length; i++) {
			consoleTest("text:" + this.fragments[i].getType() + ", duration:"
					+ this.fragments[i].getDuration());
			this.fullDuration += this.fragments[i].getDuration();
		}

		recordAnchor = new ImageAnchor();
		recordAnchor.add(new Image("images/record.png"));
		recordAnchor.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				buttonStartOver.setVisible(false);
				buttonUpload.setVisible(false);
				playSampleButton.setEnabled(false);
				startRecording();
			}
		});

		stopAnchor = new ImageAnchor();
		stopAnchor.add(new Image("images/stop.png"));
		stopAnchor.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				// stopRecording();
			}
		});

		playAnchor = new ImageAnchor();
		playAnchor.add(new Image("images/play.png"));
		playAnchor.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				buttonStartOver.setVisible(false);
				buttonUpload.setVisible(false);
				playSampleButton.setEnabled(false);
				playRecording();
			}
		});

		pauseAnchor = new ImageAnchor();
		pauseAnchor.add(new Image("images/pause.png"));
		pauseAnchor.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				// pauseRecording();
			}
		});

		playerButtonPanel.add(recordAnchor);
		playerInstructionPanel.add(playerInstructionText);

		testInstructionPanel.add(testInstructionText);
		testInstructionText.setText(defaultInstructionText);
		testInstructionText.setStyleName("double_font");

		fragmentInstructionPanel.add(fragmentInstructionText);
		fragmentInstructionText.setText("");
		fragmentInstructionText.setStyleName("double_font");

		clearPlayerTextPanel();
		playerTextPanel.add(textToRecord);
		textToRecord.addStyleName("speech_text");

		// buttonStartOver.setEnabled(false);
		// buttonUpload.setEnabled(false);
		playSampleButton.setEnabled(true);
		buttonStartOver.setVisible(false);
		buttonUpload.setVisible(false);
	}
}

class MyTimer extends Timer {
	CustomPlayer player;
	// private String text;
	// private int duration;

	private Fragment fragment;

	/*
	 * MyTimer(CustomPlayer player, String text) { this.player = player;
	 * this.text = text; }
	 * 
	 * MyTimer(CustomPlayer player, String text, int duration) { this.player =
	 * player; this.text = text; this.duration = duration; }
	 */
	MyTimer(CustomPlayer player, Fragment fragment) {
		this.player = player;
		this.fragment = fragment;
	}

	public void run() {
		// player.updatePlayerText(text);
		// player.updatePlayerTimer(duration+"");
		player.updatePlayerHtmlPanel(fragment);
	}
}