package cse.mlab.hvr.client;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.IconType;

//import com.allen_sauer.gwt.voices.client.Sound;
//import com.allen_sauer.gwt.voices.client.SoundController;
//import com.allen_sauer.gwt.voices.client.handler.SoundHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.client.PreTestState.InternalState;
import cse.mlab.hvr.client.events.PreTestInternalEvent;

public class VolumeChecker extends Composite {
	
	@UiField
	Button buttonYes, buttonNo;
	@UiField
	HTMLPanel textPanel, imagePanel;
	
//	private Sound sound;
//	private Audio audio;
//	private SoundController soundController = new SoundController();
//	private SoundHandler soundHandler;

	private static String testAudioPath = "audio/test.mp3";

	private static VolumeCheckerUiBinder uiBinder = GWT
			.create(VolumeCheckerUiBinder.class);

	interface VolumeCheckerUiBinder extends UiBinder<Widget, VolumeChecker> {
	}

	public VolumeChecker() {
		initWidget(uiBinder.createAndBindUi(this));
		buttonYes.setType(ButtonType.SUCCESS);
		buttonNo.setType(ButtonType.DANGER);
		buttonYes.setIcon(IconType.CHECK);
		buttonNo.setIcon(IconType.CLOSE);
		
		//playHtml5Audio(this, testAudioPath);
		noButtonHandler(null);
	}

	@UiHandler("buttonYes")
	void yesButtonHandler(ClickEvent event){
//		Hvr.getEventBus().fireEvent(new VolumeCheckEvent());
		Hvr.getEventBus().fireEvent(new PreTestInternalEvent(new PreTestState(InternalState.VOLUME)));

	}
	
	@UiHandler("buttonNo")
	void noButtonHandler(ClickEvent event){
		//loadAudioInstruction(testAudioPath);
		playHtml5Audio(this, testAudioPath);
		buttonYes.setVisible(false);
		buttonNo.setVisible(false);
		textPanel.setVisible(false);
		imagePanel.setVisible(true);
	}
	
	private void audioCompleteAction(){
		buttonYes.setVisible(true);
		buttonNo.setVisible(true);
		textPanel.setVisible(true);
		imagePanel.setVisible(false);
	}
	
	public native void playHtml5Audio(VolumeChecker checker, String soundPath)/*-{
			console.log("audio will run from html5");
			var audio = new Audio();
			audio.src = soundPath;
			audio.onloadeddata = function(){
				audio.play();
			}
			
			audio.onended = function(){
				//alert("audio playback has been finished");
				checker.@cse.mlab.hvr.client.VolumeChecker::audioCompleteAction()();
			}
	}-*/;
	
	private void loadAudioInstruction(String soundPath) {
//		if (soundPath.isEmpty() || soundPath.equals("")) {
//		} else {
//			
//			
//			// create a sound
//			if(Hvr.getBrowserName().toLowerCase().contains("firefox") || Hvr.getBrowserName().toLowerCase().contains("safari")){
//				//sound = soundController.createSound(Sound.MIME_TYPE_AUDIO_BASIC,
//						//soundPath);
//				//final Audio audio = Audio.createIfSupported();
//				audio = Audio.createIfSupported();
//				audio.setSrc(soundPath);
//				final AudioElement element = audio.getAudioElement();
//				//Window.alert("audio supported:"+ Audio.isSupported()+ ", duration:"+ audio.getDuration() + ",pr" + audio.getPlaybackRate() + ",inittime:"+ audio.getInitialTime());
//				//audio.setVolume(100);
//				//audio.play();
//				element.play();
//				
//				//Window.alert("duration just before:"+ element.getDuration());
//				Timer toLoad = new Timer(){
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						Timer timer = new Timer() {
//
//							@Override
//							public void run() {
//								//Window.alert("completed sound:"+ audio.getDuration());
//								audioCompleteAction();
//							}
//						};
//						timer.schedule((int) (element.getDuration()*1000));
//						
//					}
//				};
//				toLoad.schedule(1000);
//			}else {
//				sound = soundController.createSound(Sound.MIME_TYPE_AUDIO_MPEG_MP3,
//						soundPath);								
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
//						//Window.alert("load state:"+ event.getLoadState().name() + "agent:"+ Window.Navigator.getUserAgent().toLowerCase());
//						//sound.setVolume(100);
//						sound.play();
//					}
//
//					@Override
//					public void onPlaybackComplete(PlaybackCompleteEvent event) {
//						// TODO Auto-generated method stub
//						// Window.alert("playback completed");
//						audioCompleteAction();
//					}
//				};
//				sound.addEventHandler(soundHandler);			}
//
//			
//		}
	}
	
}

