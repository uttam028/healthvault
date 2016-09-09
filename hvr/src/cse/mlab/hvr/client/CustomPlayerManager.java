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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.client.SpeechTestState.TestState;
import cse.mlab.hvr.client.TestInterceptState.InterceptState;
import cse.mlab.hvr.client.events.FileUploadEvent;
import cse.mlab.hvr.client.events.FileUploadEventHandler;
import cse.mlab.hvr.client.events.SpeechTestEvent;
import cse.mlab.hvr.client.events.TestProcessInterceptionEvent;
import cse.mlab.hvr.client.events.TestProcessInterceptionHandler;
import cse.mlab.hvr.shared.Response;
import cse.mlab.hvr.shared.study.Recording;
import cse.mlab.hvr.shared.study.SpeechTest;

public class CustomPlayerManager extends Composite {
	


	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

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

	AudioBasedCustomPlayer[] players;
	SpeechTest metadata;
	String studyId;
	
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
								, String .valueOf(players[currentPlayerIndex].getRetakeCounter()), CustomPlayerManager.this);
						//players[currentPlayerIndex]
						
						if (speechTestRunning) {
							currentPlayerIndex++;
							updatePlayerProgress();
							// Window.alert("I have got the fire of the event");
							try {
								if (currentPlayerIndex > 0) {
									playerManagerPanel.remove(players[currentPlayerIndex - 1]);
									Window.alert("removed player :"+ (currentPlayerIndex-1) + ", study id:"+ studyId);
								}
							} catch (Exception e) {
								// TODO: handle exception
								Window.alert("Exception occurred at removing player, index:"+ currentPlayerIndex + ", len:" + players.length);
							}

							try {
								if (currentPlayerIndex < players.length) {
									playerManagerPanel.add(players[currentPlayerIndex]);
									Window.alert("add new player:"+ currentPlayerIndex);
								} else {
//									loadDysPlayerFromSaveState = false;
									updateSpeechTestRunningStatus(false);
									currentPlayerIndex = 0;
									updatePlayerProgress();
									Window.alert("set player to 0");
									testLoaded = false;
									CustomPlayerManager.this.removeFromParent();
									
									Hvr.getEventBus().fireEvent(
											new SpeechTestEvent(
													new SpeechTestState(studyId, metadata.getTestId(), TestState.COMPLETED)));
								}

							} catch (Exception e) {
								Window.alert("Exception occurred, index:" + currentPlayerIndex + ", len:" + players.length);
							}
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
	
	public void startPlayer(final String studyId, final SpeechTest metadata) {
		this.players = CustomPlayerCreator.getInstance().getAudioBasedCustomPlayer(metadata);
		this.studyId = studyId;
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
	
	private void syncUploadInformation(String studyId, String subtestId, String fileIdentifier, String startTime, String endTime, String retakeCounter){
		Recording recording = new Recording();
		recording.setUserId(MainPage.getLoggedinUser());
		recording.setStudyId(studyId);
		recording.setStartTime(startTime);
		recording.setEndTime(endTime);
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
		greetingService.relocateSoundFile(recording, new AsyncCallback<Response>() {
			
			@Override
			public void onSuccess(Response result) {
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	public native void uploadFile(String studyId, String subtestId, String name, String startTime, String endTime, String retakeCounter, CustomPlayerManager manager)/*-{
		var blob = $wnd.FWRecorder.getBlob(name);
		
		var formData = new FormData();
		var filename = name.replace(/\s/g, "") + ".wav";
		formData.append("file", blob, filename);
		$wnd.$.ajax({
					// url:
					// 'http://m-lab.cse.nd.edu:8080/fileupload/rest/files/upload/',
					// //Server script to process data
					url : 'http://10.32.10.188:8080/phrservice/files/upload/',
					type : 'post',
					contentType : 'multipart/form-data',
					// content: 'text/html',
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
						//alert("let see if it calls before sending");
					},
					success : function(data, textStatus, myXhr){
						//alert("file upload done...." + myXhr.status + ", data:" + data + ", text status:"+ textStatus);
						if(myXhr.status == 200){
							manager.@cse.mlab.hvr.client.CustomPlayerManager::syncUploadInformation(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(studyId, subtestId, data, startTime, endTime, retakeCounter);							
						}
					},
					error : function(myXhr, textStatus, errorThrown){
						//alert("text status:"+ textStatus + ", error : "+ errorThrown);
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
