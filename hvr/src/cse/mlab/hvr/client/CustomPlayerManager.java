package cse.mlab.hvr.client;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.Modal;
import org.gwtbootstrap3.client.ui.ModalFooter;
import org.gwtbootstrap3.client.ui.ModalHeader;
import org.gwtbootstrap3.client.ui.ProgressBar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.client.SpeechTestState.TestState;
import cse.mlab.hvr.client.TestInterceptState.InterceptState;
import cse.mlab.hvr.client.events.FileUploadSuccessEvent;
import cse.mlab.hvr.client.events.FileUploadSuccessEventHandler;
import cse.mlab.hvr.client.events.SpeechTestEvent;
import cse.mlab.hvr.client.events.TestProcessInterceptionEvent;
import cse.mlab.hvr.client.events.TestProcessInterceptionHandler;
import cse.mlab.hvr.shared.study.SpeechTest;

public class CustomPlayerManager extends Composite {
	static int currentPlayerIndex = 0;
	static boolean speechTestRunning = false;
	private Boolean testLoaded = false;
	@UiField
	HTMLPanel playerManagerPanel, allowedPlayerHeader, deniedPlayerHeader;
	@UiField
	Heading permissionHeading;
	
	@UiField
	ProgressBar playerProgressBar;
	
	//@UiField
	//Button restartButton;

	private static Modal exitModal;

	final AudioBasedCustomPlayer[] players;
	final SpeechTest metadata;
	
	private static boolean microphoneAllowed  = false; 

	private static CustomPlayerManagerUiBinder uiBinder = GWT
			.create(CustomPlayerManagerUiBinder.class);

	interface CustomPlayerManagerUiBinder extends
			UiBinder<Widget, CustomPlayerManager> {
	}

	public CustomPlayerManager(final String studyId, final SpeechTest metadata) {
		initWidget(uiBinder.createAndBindUi(this));
		players = CustomPlayerCreator.getInstance().getAudioBasedCustomPlayer(metadata);

		this.metadata = metadata;
		updateMicroPhoneAccessiblity();
		if(microphoneAllowed){
			allowedPlayerHeader.setVisible(true);
			deniedPlayerHeader.setVisible(false);
			initializePlayer();
		} else {
			allowedPlayerHeader.setVisible(false);
			deniedPlayerHeader.setVisible(true);
			permissionHeading.setText("Please allow permission to access your microphone.");
			askForPermission(this);
		}		
		
		initializeExitModal(studyId);
		

		Hvr.getEventBus().addHandler(FileUploadSuccessEvent.TYPE,
				new FileUploadSuccessEventHandler() {

					@Override
					public void actionAfterFileUpload(
							FileUploadSuccessEvent event) {
						// TODO Auto-generated method stub
						if (speechTestRunning) {
							currentPlayerIndex++;
							updatePlayerProgress();
							// Window.alert("I have got the fire of the event");
							try {
								if (currentPlayerIndex > 0) {
									playerManagerPanel
											.remove(players[currentPlayerIndex - 1]);
									// dysarthriaRecorderPanel.remove(players[currentDysPlayerIndex
									// - 1]);
								}
							} catch (Exception e) {
								// TODO: handle exception
								Window.alert("Exception occurred at removing player, index:"
										+ currentPlayerIndex
										+ ", len:"
										+ players.length);
							}

							try {
								if (currentPlayerIndex < players.length) {
									playerManagerPanel
											.add(players[currentPlayerIndex]);
									// dysarthriaRecorderPanel.add(players[currentDysPlayerIndex]);
									// String percentage = String
									// .valueOf(((100.0 /
									// players.length) *
									// currentPlayerIndex));
									// makeSplittedProgress(percentage);
									/*
									String elementId = "dystestprogress-"
											+ currentPlayerIndex;
									DOM.getElementById(elementId).setClassName(
											"progtrckr-done");
									*/
								} else {
//									loadDysPlayerFromSaveState = false;
									updateSpeechTestRunningStatus(false);
									currentPlayerIndex = 0;
									updatePlayerProgress();
									testLoaded = false;
									Hvr.getEventBus().fireEvent(
											new SpeechTestEvent(
													new SpeechTestState(studyId, metadata.getTestId(), TestState.COMPLETED)));
									CustomPlayerManager.this.removeFromParent();
								}

							} catch (Exception e) {
								Window.alert("Exception occurred, index:"
										+ currentPlayerIndex + ", len:"
										+ players.length);
							}
						}
					}
				});
	}
	
	private void initializePlayer(){
		this.playerManagerPanel.add(players[0]);
		currentPlayerIndex=0;
		updatePlayerProgress();
		updateSpeechTestRunningStatus(true);
		
	}
	
	private void initializeExitModal(final String studyId){
		exitModal = new Modal();
		exitModal.setClosable(false);
		//exitModal.setFade(true);
		//exitModal.setDataBackdrop(ModalBackdrop.STATIC);
		//exitModal.setId("exitmodal");
		ModalFooter footer = new ModalFooter();
		ModalHeader header = new ModalHeader();
		header.add(new Label("Do you want to exit speech test?"));
		exitModal.add(header);
		header.setClosable(false);
		exitModal.add(footer);
		Button yesButton = new Button("Yes");
		Button noButton = new Button("No");
		footer.add(yesButton);
		footer.add(noButton);
		yesButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				players[currentPlayerIndex].stopPlayer();
				// TODO Auto-generated method stub
				//exitModal.setVisible(false);
				exitModal.hide();
				//TODO: in future utilize yes event, right now solve the issue by firing test incomplete event
				//Hvr.getEventBus().fireEvent(new TestProcessInterceptionEvent(new TestInterceptState(studyId, InterceptState.YES)));
				Hvr.getEventBus().fireEvent(
						new SpeechTestEvent(
								new SpeechTestState(studyId, metadata.getTestId(), TestState.INCOMPLETE)));
				exitModal.removeFromParent();
					
				}
		});
		
		noButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				//exitModal.setVisible(false);
				exitModal.hide();
				Hvr.updateSpeechTestState(true);
				//TODO: in future utilize yes event, right now solve the issue by firing test incomplete event
				//Hvr.getEventBus().fireEvent(new TestProcessInterceptionEvent(new TestInterceptState(studyId, InterceptState.NO)));
				
			}
		});
		
		Hvr.getEventBus().addHandler(TestProcessInterceptionEvent.TYPE, new TestProcessInterceptionHandler() {
			
			@Override
			public void actionAfterTestInterception(TestProcessInterceptionEvent event) {
				//Window.alert("received event :"+ event.getInterceptState().getState().name());
				// TODO Auto-generated method stub
				if(event.getInterceptState().getState() == InterceptState.START){
					exitModal.show();
				}
			}
		});
	}
	
	private void updatePlayerProgress(){
		//playerProgressBar.set
		double percent = ((currentPlayerIndex * 1.0)/this.players.length) * 100;
		playerProgressBar.setPercent(percent);
		playerProgressBar.setText(Math.round(percent) + "%");
	}
	
	
	protected void updateSpeechTestRunningStatus(boolean state) {
		speechTestRunning = state;
	}
	
	public static native void askForPermission(CustomPlayerManager manager)/*-{
		$wnd.FWRecorder.record("mictest", "mictest.wav");
		//$wnd.microphonePermission();
		
		$wnd.callbackForPermission = $entry(function() {
			console.log("what is the problem to print it");
			$wnd.FWRecorder.stopRecording("mictest");
			manager.@cse.mlab.hvr.client.CustomPlayerManager::updateMicroPhoneAccessiblity()();
			manager.@cse.mlab.hvr.client.CustomPlayerManager::updateManagerAfterPermission()();
		});
		
	}-*/;
	
	private void updateManagerAfterPermission(){
		if(microphoneAllowed){
			allowedPlayerHeader.setVisible(true);
			deniedPlayerHeader.setVisible(false);
			initializePlayer();
		} else {
			allowedPlayerHeader.setVisible(false);
			deniedPlayerHeader.setVisible(true);
			permissionHeading.setText("You have denied to access microphone. Please refresh and try again.");
		}
	}

	public static native void askForPermissionSecondTime(CustomPlayerManager manager)/*-{
		$wnd.microphonePermission();
		$wnd.FWRecorder.record("mic", "mic.wav");
	}-*/;
	
	
	/*@UiHandler("restartButton")
	void restartPermission(ClickEvent event){
		askForPermissionSecondTime(this);;
	}*/
	
	public native void updateMicroPhoneAccessiblity()/*-{
		console.log("mic accessible:"
				+ $wnd.FWRecorder.isMicrophoneAccessible());
		if ($wnd.FWRecorder.isMicrophoneAccessible()) {
			@cse.mlab.hvr.client.CustomPlayerManager::microphoneAllowed = true;
			console.log("mic accessible 1:"
					+ $wnd.FWRecorder.isMicrophoneAccessible());
		} else {
			@cse.mlab.hvr.client.CustomPlayerManager::microphoneAllowed = false;
			console.log("mic accessible 2 :"
					+ $wnd.FWRecorder.isMicrophoneAccessible());
		}
}-*/;


}
