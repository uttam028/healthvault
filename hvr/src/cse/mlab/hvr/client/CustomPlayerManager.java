package cse.mlab.hvr.client;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Modal;
import org.gwtbootstrap3.client.ui.ModalFooter;
import org.gwtbootstrap3.client.ui.ModalHeader;
import org.gwtbootstrap3.client.ui.ProgressBar;
import org.gwtbootstrap3.client.ui.constants.ButtonSize;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.client.PreTestState.InternalState;
import cse.mlab.hvr.client.SpeechTestState.TestState;
import cse.mlab.hvr.client.TestInterceptState.InterceptState;
import cse.mlab.hvr.client.events.FileUploadEvent;
import cse.mlab.hvr.client.events.FileUploadEventHandler;
import cse.mlab.hvr.client.events.PreTestInternalEvent;
import cse.mlab.hvr.client.events.PreTestInternalEventHandler;
import cse.mlab.hvr.client.events.SpeechTestEvent;
import cse.mlab.hvr.client.events.TestProcessInterceptionEvent;
import cse.mlab.hvr.client.events.TestProcessInterceptionHandler;
import cse.mlab.hvr.client.services.SpeechService;
import cse.mlab.hvr.client.services.SpeechServiceAsync;
import cse.mlab.hvr.shared.Response;
import cse.mlab.hvr.shared.study.Recording;
import cse.mlab.hvr.shared.study.SpeechTest;

public class CustomPlayerManager extends Composite {
	


	private final SpeechServiceAsync service = GWT.create(SpeechService.class);

	static int currentPlayerIndex = 0;
	static boolean speechTestRunning = false;
	private Boolean testLoaded = false;
	@UiField
	HTMLPanel playerManagerPanel, allowedPlayerHeader, playerContentPanel;
//	@UiField
//	Heading permissionHeading;
	
	@UiField
	ProgressBar playerProgressBar;
	
	//@UiField
	//Button restartButton;

	private static Modal exitModal;

//	AudioBasedCustomPlayer[] players;
	AudioBasedCustomPlayerHtml5[] players;
	BasicSystemChecker basicSystemChecker;
	SpeechTest metadata;
	String studyId;
	private String participationId;
	
	private static boolean microphoneAllowed  = false; 

	private static CustomPlayerManagerUiBinder uiBinder = GWT
			.create(CustomPlayerManagerUiBinder.class);

	interface CustomPlayerManagerUiBinder extends
			UiBinder<Widget, CustomPlayerManager> {
	}
	private static CustomPlayerManager instance = null;

	private CustomPlayerManager(){
		initWidget(uiBinder.createAndBindUi(this));
		Hvr.getEventBus().addHandler(FileUploadEvent.TYPE,
				new FileUploadEventHandler() {

					@Override
					public void actionAfterFileUpload(
							FileUploadEvent event) {
						
						uploadFile(studyId, String.valueOf(players[currentPlayerIndex].getSubtestId()), players[currentPlayerIndex].getHeader()
								, players[currentPlayerIndex].getStartTime(), players[currentPlayerIndex].getEndTime()
								, String .valueOf(players[currentPlayerIndex].getRetakeCounter()), players[currentPlayerIndex].getSplitString(), CustomPlayerManager.this);
						//players[currentPlayerIndex]
						
						if (speechTestRunning) {
							currentPlayerIndex++;
							updatePlayerProgress();
							// Window.alert("I have got the fire of the event");
							try {
								if (currentPlayerIndex > 0) {
									playerContentPanel.remove(players[currentPlayerIndex - 1]);
								}
							} catch (Exception e) {
								// TODO: handle exception
								Window.alert("Exception occurred at removing player, index:"+ currentPlayerIndex + ", len:" + players.length);
							}

							try {
								if (currentPlayerIndex < players.length) {
									playerContentPanel.add(players[currentPlayerIndex]);
								} else {
//									loadDysPlayerFromSaveState = false;
									updateSpeechTestRunningStatus(false);
									currentPlayerIndex = 0;
									updatePlayerProgress();
									testLoaded = false;
									CustomPlayerManager.this.removeFromParent();
									
									Hvr.getEventBus().fireEvent(
											new SpeechTestEvent(new SpeechTestState(studyId, metadata.getTestId(), participationId, TestState.COMPLETED)));
								}

							} catch (Exception e) {
								Window.alert("Exception occurred, index:" + currentPlayerIndex + ", len:" + players.length);
							}
						}
					}
				});
		
		Hvr.getEventBus().addHandler(PreTestInternalEvent.TYPE, new PreTestInternalEventHandler() {
			
			@Override
			public void actionAfterPreTestEvent(PreTestInternalEvent event) {
				
				// TODO Auto-generated method stub
				if(event.getPreTestState().getState() == InternalState.BASIC){
//					if(microphoneAllowed){
//						allowedPlayerHeader.setVisible(true);
//						deniedPlayerHeader.setVisible(false);
						initializePlayer();
//					} else {
//						Window.alert("basic check done...microphone allowed:"+ microphoneAllowed );
//						allowedPlayerHeader.setVisible(false);
//						deniedPlayerHeader.setVisible(true);
//						permissionHeading.setText("Please allow permission to access your microphone.");
//						askForPermission(CustomPlayerManager.this);
//					}		
					
				}else if (event.getPreTestState().getState() == InternalState.VOLUME) {
					playerContentPanel.clear();
					playerContentPanel.add(new MicrophoneChecker());
//					playerContentPanel.add(players[0]);
				}else if (event.getPreTestState().getState() == InternalState.MICROPHONE) {
					playerContentPanel.clear();
					playerContentPanel.add(new PreTestQuestionsPresenter(metadata.getTestId(), participationId));									
				}else if (event.getPreTestState().getState() == InternalState.QUESTION) {
					
					playerProgressBar.setPercent(0);
					allowedPlayerHeader.setVisible(true);
					
					playerContentPanel.clear();
					playerContentPanel.add(players[0]);									
				}
			}
		});
		
	}

	public static CustomPlayerManager getInstance() {
		if (instance == null) {
			instance = new CustomPlayerManager();
		}
		return instance;
	}
	
	public void startPlayer(final String studyId, final SpeechTest metadata, final String participationId) {
		this.players = CustomPlayerCreator.getInstance().getAudioBasedCustomPlayer(metadata);
		this.studyId = studyId;
		this.metadata = metadata;
		this.participationId = participationId;
		
		allowedPlayerHeader.setVisible(false);
		initializeJSFunctions(this);
		initializeExitModal(studyId);

		this.playerContentPanel.clear();
		basicSystemChecker = new BasicSystemChecker();
		this.playerContentPanel.add(basicSystemChecker);
		
		
	}
	
	private void initializePlayer(){
		//this.playerManagerPanel.add(players[0]);
		this.playerContentPanel.clear();
		this.playerContentPanel.add(new VolumeChecker());
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
		Label headerLabel = new Label("Do you want to exit speech test?");
		headerLabel.getElement().getStyle().setFontSize(2, Unit.EM);
		header.add(headerLabel);
	
		exitModal.add(header);
		header.setClosable(false);
		exitModal.add(footer);
		Button yesButton = new Button("Yes");
		Button noButton = new Button("No");
		//yesButton.setType(ButtonType.DANGER);
		//noButton.setType(ButtonType.DANGER);
		yesButton.setSize(ButtonSize.LARGE);
		noButton.setSize(ButtonSize.LARGE);
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
	
//	public static native void askForPermission(CustomPlayerManager manager)/*-{
//		$wnd.FWRecorder.record("mictest", "mictest.wav");
//		//$wnd.microphonePermission();
//		
//		$wnd.callbackForPermission = $entry(function() {
//			$wnd.FWRecorder.stopRecording("mictest");
////			manager.@cse.mlab.hvr.client.CustomPlayerManager::updateMicroPhoneAccessiblity()();
//			manager.@cse.mlab.hvr.client.CustomPlayerManager::updateManagerAfterPermission()();
//		});
//		
//	}-*/;
	
//	private void updateManagerAfterPermission(){
//		if(microphoneAllowed){
////			allowedPlayerHeader.setVisible(true);
//			deniedPlayerHeader.setVisible(false);
//			initializePlayer();
//		} else {
////			allowedPlayerHeader.setVisible(false);
//			deniedPlayerHeader.setVisible(true);
//			permissionHeading.setText("You have denied to access microphone. Please refresh and try again.");
//		}
//	}

	public static native void askForPermissionSecondTime(CustomPlayerManager manager)/*-{
		$wnd.microphonePermission();
		$wnd.FWRecorder.record("mic", "mic.wav");
	}-*/;
	
	
	/*@UiHandler("restartButton")
	void restartPermission(ClickEvent event){
		askForPermissionSecondTime(this);;
	}*/
	
	public native void initializeJSFunctions(CustomPlayerManager manager)/*-{
//		console.log("mic accessible:"
//				+ $wnd.FWRecorder.isMicrophoneAccessible());
//		if ($wnd.FWRecorder.isMicrophoneAccessible()) {
//			@cse.mlab.hvr.client.CustomPlayerManager::microphoneAllowed = true;
//			console.log("mic accessible 1:"
//					+ $wnd.FWRecorder.isMicrophoneAccessible());
//		} else {
//			@cse.mlab.hvr.client.CustomPlayerManager::microphoneAllowed = false;
//			console.log("mic accessible 2 :"
//					+ $wnd.FWRecorder.isMicrophoneAccessible());
//		}
		$wnd.updateMirophonePermission = $entry(function(permission) {
			console.log("get permission status from recorder:"+ permission);
			if(permission){
				@cse.mlab.hvr.client.CustomPlayerManager::microphoneAllowed = true;
			}else{
				@cse.mlab.hvr.client.CustomPlayerManager::microphoneAllowed = false;
			}
			manager.@cse.mlab.hvr.client.CustomPlayerManager::microphoneHasBeenAllowed()();
		});
		
		$wnd.updateMirophoneDetection = $entry(function() {
			console.log("microphone is not available:");
			manager.@cse.mlab.hvr.client.CustomPlayerManager::microphoneNotDetected()();
		});
		
		
	}-*/;
	
	private void microphoneNotDetected(){
		basicSystemChecker.updateMicrophoneDetection();
	}
	
	private void microphoneHasBeenAllowed(){
		basicSystemChecker.refreshCheck(microphoneAllowed);
	}
	
	private void syncUploadInformation(String studyId, String subtestId, String fileIdentifier, String startTime, String endTime, String retakeCounter, String splitString){
		Recording recording = new Recording();
		recording.setUserId(MainPage.getLoggedinUser());
		recording.setStudyId(studyId);
		recording.setStartTime(startTime);
		recording.setEndTime(endTime);
		recording.setSplitString(splitString);
		try {
			recording.setRetakeCounter(Integer.parseInt(retakeCounter));			
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			recording.setSubtestId(Integer.parseInt(subtestId));			
		} catch (Exception e) {
			// TODO: handle exception
		}
		recording.setFileIdentifier(fileIdentifier);
		service.relocateSoundFile(recording, new AsyncCallback<Response>() {
			
			@Override
			public void onSuccess(Response result) {
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	public native void uploadFile(String studyId, String subtestId, String name, String startTime, String endTime, String retakeCounter, String splitString, CustomPlayerManager manager)/*-{
		//var blob = $wnd.FWRecorder.getBlob(name);
		
		var blob = $wnd.Html5Recorder.getBlobData();
		console.log("window.location.hostname - "+ window.location.hostname + ", document.domain-" + document.domain + ", window.location.host"+ window.location.host + ", split string :"+ splitString);
		
		var formData = new FormData();
		var filename = name.replace(/\s/g, "") + ".wav";
		formData.append("file", blob, filename);
		$wnd.$.ajax({
					// url:
					// 'http://m-lab.cse.nd.edu:8080/fileupload/rest/files/upload/',
					// //Server script to process data
					//url : 'http://129.74.247.110:80/markerinterface/files/upload/',
					//url : 'https://speechmarker.com/markerinterface/files/upload/',
					url : 'https://speechbiomarker.com//hvr/fileupload',
					type : 'post',
					contentType : 'multipart/form-data',
					//content: 'text/html',
					xhr : function() { // Custom XMLHttpRequest
						var myXhr = $wnd.$.ajaxSettings.xhr();
						if (myXhr.upload) { // Check if upload property exists
							myXhr.upload.addEventListener('progress',
									progressHandlingFunction, false); // For
																		// handling
																		// the
																		// progress
																		// of
																		// the
																		// upload
						}
						return myXhr;
					},
					// Ajax events
					beforeSend : function(){
//						alert("let see if it calls before sending");
					},
					success : function(data, textStatus, myXhr){
						console.log("file upload done...." + myXhr.status + ", data:" + data + ", text status:"+ textStatus);
						if(myXhr.status == 200){
							manager.@cse.mlab.hvr.client.CustomPlayerManager::syncUploadInformation(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(studyId, subtestId, data, startTime, endTime, retakeCounter, splitString);							
						}
					},
					error : function(myXhr, textStatus, errorThrown){
						console.log("text status:"+ textStatus + ", error : "+ errorThrown);
//						alert("text status:"+ textStatus + ", error : "+ errorThrown);
					}, // Form data
					//complete : function(myXhr, textStatus){
						//alert("text status :"+ textStatus + "study id:" + studyId + ", subtestId : "+ subtestId + ", name :"+ name);
					//},
					data : formData,
					// Options to tell jQuery not to process data or worry about
					// content-type.
					cache : false,
					contentType : false,
					processData : false
				});

		//function beforeSendHandler() {
			
		//}

		function progressHandlingFunction(e) {
			// if(e.lengthComputable){
			// $('progress').attr({value:e.loaded,max:e.total});
			// console.log("progress : "+ {value:e.loaded,max:e.total});
			//alert("making some progress");
			// }
		}
		
	}-*/;

}
