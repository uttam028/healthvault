package cse.mlab.hvr.client;

import org.gwtbootstrap3.client.ui.Anchor;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.InlineCheckBox;
import org.gwtbootstrap3.client.ui.Modal;
import org.gwtbootstrap3.client.ui.ModalBody;
import org.gwtbootstrap3.client.ui.constants.ButtonDismiss;
import org.gwtbootstrap3.client.ui.constants.Toggle;
import org.gwtbootstrap3.client.ui.html.Br;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.client.events.TestCompletionEvent;
import cse.mlab.hvr.client.events.TestCompletionEventHandler;

public class VoicePage extends Composite {

	@UiField
	HTMLPanel voicePagePanel, testInformationPanel, staticConcussionPanel, staticDysartrhiaPanel;
	@UiField
	Modal voicePageModal;
	@UiField
	ModalBody voicePageModalBody;
	@UiField
	InlineCheckBox consentCheckBox;
	@UiField
	Button buttonModalNext;
	
	

	MicrophoneTest microphoneTest;

//	Button configureMicButton, concussionTestButton, dysarthiaTestButton, audioTestButton;
	@UiField
	Anchor buttonStartConcussionTest, buttonStartDysarthriaTest;

	Label labelForTest, labelForHistory;

//	VerticalPanel testPanel;

//	HorizontalPanel historyPanel;

	static boolean isMicrophoneAllowed = false;
	static boolean isMicrophoneLevelOk = false;
	private double microphoneLevel = 0;
	
	static boolean microphoneTestPassed = false;

	String selectedTest = "";
	
	static DysarthiaTestNew audioBasedDysarthiaTest;
	static ConcussionTestNew audioBasedConcussionTest;
	
	Hvr application;
	

	private static VoicePageUiBinder uiBinder = GWT
			.create(VoicePageUiBinder.class);

	interface VoicePageUiBinder extends UiBinder<Widget, VoicePage> {
	}

	public VoicePage(Hvr application) {
		initWidget(uiBinder.createAndBindUi(this));
		this.application = application;
		audioBasedDysarthiaTest = new DysarthiaTestNew();
		audioBasedConcussionTest = new ConcussionTestNew();
		
		
//		configureMicButton = new Button("Configure Microphone");
//		configureMicButton.setType(ButtonType.DEFAULT);
//		configureMicButton.addClickHandler(new ClickHandler() {
//
//			@Override
//			public void onClick(ClickEvent event) {
//				// TODO Auto-generated method stub
//				configureMicrophone();
//			}
//		});

//		concussionTestButton = new Button("Concussion test");
//		concussionTestButton.setDataTarget("#" + voicePageModal.getId());
//		concussionTestButton.setDataToggle(Toggle.MODAL);
//		concussionTestButton.setType(ButtonType.LINK);
//		concussionTestButton.addStyleName("col-lg-offset-6");
//		concussionTestButton.addClickHandler(new ClickHandler() {
//
//			@Override
//			public void onClick(ClickEvent event) {
//				// TODO Auto-generated method stub
//				// voicePagePanel.clear();
//				// voicePagePanel.add(new ConcussionTest());
//				selectedTest = "concussion";
//				voicePagePanel.add(voicePageModal);
//			}
//		});

//		dysarthiaTestButton = new Button("Dysarthia test");
//		dysarthiaTestButton.setDataTarget("#" + voicePageModal.getId());
//		dysarthiaTestButton.setDataToggle(Toggle.MODAL);
//		dysarthiaTestButton.setType(ButtonType.LINK);
//		dysarthiaTestButton.addStyleName("col-lg-offset-6");
//		dysarthiaTestButton.addClickHandler(new ClickHandler() {
//
//			@Override
//			public void onClick(ClickEvent event) {
//				// TODO Auto-generated method stub
//				// voicePagePanel.clear();
//				// voicePagePanel.add(new DysarthiaTest());
//				selectedTest = "dysarthia";
//				voicePagePanel.add(voicePageModal);
//
//			}
//		});
//		
//		audioTestButton = new Button("Audio Test");
//		audioTestButton.addClickHandler(new ClickHandler() {
//			
//			@Override
//			public void onClick(ClickEvent event) {
//				// TODO Auto-generated method stub
//				voicePagePanel.clear();
//				voicePagePanel.add(new AudioTest());
//			}
//		});
	
//		testPanel = new VerticalPanel();
//		labelForTest = new Label("Click to start one of the following test:");
//		labelForTest.addStyleName("col-lg-offset-3 h2");
//
//		labelForHistory = new Label(
//				"There will be a table or list that will show history of speech test taken by user and status of each test");
//		labelForTest.addStyleName("col-lg-offset-3 h3");
//
//		testPanel.add(labelForTest);
//		testPanel.add(new Br());
//		testPanel.add(concussionTestButton);
//		testPanel.add(dysarthiaTestButton);
//		testPanel.add(audioTestButton);
//		testPanel.add(new Br());
//		testPanel.add(new Br());
//		testPanel.add(new Br());
//		testPanel.add(new Br());
//		testPanel.add(new Br());
//		testPanel.add(labelForHistory);

//		constructPage();
		
		buttonStartConcussionTest.setDataTarget("#" + voicePageModal.getId());
		buttonStartConcussionTest.setDataToggle(Toggle.MODAL);
		buttonStartConcussionTest.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				selectedTest = "concussion";
				if(buttonModalNext.getText().equalsIgnoreCase("accept")){
					consentCheckBox.setVisible(true);
				}
				consentCheckBox.setValue(false);
				voicePagePanel.add(voicePageModal);				
			}
		});
		
		buttonStartDysarthriaTest.setDataTarget("#" + voicePageModal.getId());
		buttonStartDysarthriaTest.setDataToggle(Toggle.MODAL);
		buttonStartDysarthriaTest.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				selectedTest = "dysarthria";
				if(buttonModalNext.getText().equalsIgnoreCase("accept")){
					consentCheckBox.setVisible(true);
				}
				consentCheckBox.setValue(false);
				voicePagePanel.add(voicePageModal);				
			}
		});
		
	}
	
	public void setVoiceTest(String selectedTest){
		this.selectedTest = selectedTest;
		voicePagePanel.add(voicePageModal);
//		constructPage();
		
	}

//	public VoicePage(String firstName) {
//		initWidget(uiBinder.createAndBindUi(this));
//	}

	public native void startMicrophoneTest(VoicePage voicePage, String duration)/*-{
		$wnd.updateMicrophoneLevel = $entry(function(level) {
			console.log("update mic level");
			voicePage.@cse.mlab.hvr.client.VoicePage::accumulateMicLevel(Ljava/lang/String;)(level);
		});
		
		$wnd.FWRecorder.configure(44, 100, 0, 1);
		$wnd.FWRecorder.observeLevel();
		setTimeout(
				function() {
					$wnd.FWRecorder.stopObservingLevel();
					voicePage.@cse.mlab.hvr.client.VoicePage::updateModalAfterTest()();
					//player.@cse.mlab.hvr.client.CustomPlayer::updateTestInstructionText(Ljava/lang/String;)("You can listen recording, if sound quality is ok upload the recording");
					//player.@cse.mlab.hvr.client.CustomPlayer::clearPlayerTextPanel()();
					console.log("final sum:" + voicePage.@cse.mlab.hvr.client.VoicePage::microphoneLevel);
					
				}, parseInt(duration));
	}-*/;
	
	public void updateModalAfterTest(){
		buttonModalNext.setVisible(true);
		voicePageModal.setClosable(true);
		microphoneTest.reloadTestTimer();
		if(microphoneLevel < 2){
			buttonModalNext.setText("Try Again");
			microphoneTestPassed = false;
		} else {
			buttonModalNext.setText("Proceed");
			checkMicLevel();
			microphoneTestPassed = true;
		}
		
	}
	
	public native void checkMicLevel()/*-{
		$wnd.checkMicrophoneLevel();
	}-*/;
	
	public native void updateGraph(String value)/*-{
		console.log("amp value :" + value + ", int:" + parseInt(value));
		var ampArray = [parseInt(value)];
		$wnd.drawTimeDomain(ampArray);
	}-*/;
	
	public void accumulateMicLevel(String level){
		double temp = Double.parseDouble(level);
		microphoneLevel += temp;
		//updateGraph(level);
	}

	@UiHandler("buttonModalNext")
	public void nextButtonPressed(ClickEvent event) {
		if (buttonModalNext.getText().toLowerCase().equals("accept")) {
			if(!isMicrophoneAllowed){
				configureMicrophone();
			}
			
			if(microphoneTestPassed){
				voicePageModal.setTitle("Consent Form");
				voicePageModalBody.remove(microphoneTest);
				voicePageModalBody.add(testInformationPanel);
				buttonModalNext.setText("Accept");
				buttonModalNext.setDataDismiss(ButtonDismiss.MODAL);
				voicePagePanel.clear();
				if (selectedTest == "dysarthria") {
					audioBasedDysarthiaTest.updateDysTestRunningStatus(true);
					audioBasedConcussionTest.updateConTestRunningStatus(false);
					voicePagePanel.add(audioBasedDysarthiaTest);
				} else if (selectedTest == "concussion") {
					audioBasedDysarthiaTest.updateDysTestRunningStatus(false);
					audioBasedConcussionTest.updateConTestRunningStatus(true);
					voicePagePanel.add(audioBasedConcussionTest);
				}				
			} else{
				voicePageModalBody.remove(testInformationPanel);
				voicePageModal.setTitle("Microphone Test");
				consentCheckBox.setVisible(false);
				buttonModalNext.setText("Start");
				microphoneTest = new MicrophoneTest();
				buttonModalNext.setDataDismiss(ButtonDismiss.ALERT);
				voicePageModalBody.add(microphoneTest);
			}
			
		} else if (buttonModalNext.getText().toLowerCase().equals("start") || buttonModalNext.getText().toLowerCase().startsWith("try")) {
			microphoneTest.animateTimer();
			buttonModalNext.setVisible(false);
			voicePageModal.setClosable(false);
			startMicrophoneTest(this, String.valueOf(MicrophoneTest.testDuration));
		} else {
			voicePageModal.setTitle("Consent Form");
			voicePageModalBody.remove(microphoneTest);
			voicePageModalBody.add(testInformationPanel);
			buttonModalNext.setText("Accept");
			buttonModalNext.setDataDismiss(ButtonDismiss.MODAL);

			voicePagePanel.clear();
			if (selectedTest == "dysarthria") {
				audioBasedDysarthiaTest.updateDysTestRunningStatus(true);
				audioBasedConcussionTest.updateConTestRunningStatus(false);
				voicePagePanel.add(audioBasedDysarthiaTest);
			} else if (selectedTest == "concussion") {
				audioBasedDysarthiaTest.updateDysTestRunningStatus(false);
				audioBasedConcussionTest.updateConTestRunningStatus(true);
				voicePagePanel.add(audioBasedConcussionTest);
			}
		}
	}

	private void constructPage() {
//		if (!isMicrophoneAllowed) {
//			voicePagePanel.add(configureMicButton);
//		}
//		voicePagePanel.add(new Br());
//		voicePagePanel.add(new Br());
//		voicePagePanel.add(new Br());
//		voicePagePanel.add(testPanel);
		voicePagePanel.add(new Br());
		voicePagePanel.add(new Br());
		voicePagePanel.add(new Br());
		voicePagePanel.add(new Br());
		voicePagePanel.add(new Br());
		voicePagePanel.add(new Br());
		voicePagePanel.add(new Br());
		voicePagePanel.add(new Br());
		voicePagePanel.add(new Br());
		voicePagePanel.add(new Br());
		voicePagePanel.add(new Br());
		voicePagePanel.add(new Br());
	}

	public  native void activateAccordion()/*-{
		var acc = $wnd.document.getElementsByClassName("accordion");
		var i;
		for (i = 0; i < acc.length; i++) {
			acc[i].onclick = function() {
				this.classList.toggle("active");
				this.nextElementSibling.classList.toggle("show");
			}
		}

	}-*/; 

	
	@Override
	protected void onLoad() {
		// TODO Auto-generated method stub
		super.onLoad();
		activateAccordion();
		// this.voicePagePanel.clear();
		// this.voicePagePanel.add(new Br());
		// this.voicePagePanel.add(new Br());
		updateMicroPhoneAccessiblity();
//		if (isMicrophoneAllowed) {
//			this.voicePagePanel.remove(configureMicButton);
//		}
		
		if(selectedTest.equalsIgnoreCase("dysarthria")){
			if(staticConcussionPanel.isAttached()){
				voicePagePanel.remove(staticConcussionPanel);				
			}
			if(!staticDysartrhiaPanel.isAttached()){
				voicePagePanel.add(staticDysartrhiaPanel);
			}
		} else if(selectedTest.equalsIgnoreCase("concussion")){
			if(staticDysartrhiaPanel.isAttached()){
				voicePagePanel.remove(staticDysartrhiaPanel);				
			}
			if(!staticConcussionPanel.isAttached()){
				voicePagePanel.add(staticConcussionPanel);
			}
			
		}

		Hvr.getEventBus().addHandler(TestCompletionEvent.TYPE,
				new TestCompletionEventHandler() {

					@Override
					public void actionAfterTestCompleted(
							TestCompletionEvent event) {
						// TODO Auto-generated method stub
						voicePagePanel.clear();
//						application.mainPage.loadHomePage(null);
					}
				});

	}
	

	public native void updateMicroPhoneAccessiblity()/*-{
		console.log("mic accessible:"
				+ $wnd.FWRecorder.isMicrophoneAccessible());
		if ($wnd.FWRecorder.isMicrophoneAccessible()) {
			@cse.mlab.hvr.client.VoicePage::isMicrophoneAllowed = true;
			console.log("mic accessible 1:"
					+ $wnd.FWRecorder.isMicrophoneAccessible());
		} else {
			@cse.mlab.hvr.client.VoicePage::isMicrophoneAllowed = false;
			console.log("mic accessible 2 :"
					+ $wnd.FWRecorder.isMicrophoneAccessible());
		}
	}-*/;

	public native void configureMicrophone()/*-{
		//var name = player.@cse.mlab.hvr.client.CustomPlayer::header;
		//console.log("will ask for permission/ start recording from custom player ".concat(name))
		//Recorder.recorder.configure(rate, gain, silenceLevel, silenceTimeout);
		//$wnd.FWRecorder.showPermissionWindow({
		//permanent : true
		//});
		$wnd.microphonePermission();
		//$wnd.FWRecorder.configure(44, 1, 1, 4000);
	}-*/;

}
