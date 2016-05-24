package cse.mlab.hvr.client;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Modal;
import org.gwtbootstrap3.client.ui.ModalBody;
import org.gwtbootstrap3.client.ui.constants.ButtonDismiss;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.Toggle;
import org.gwtbootstrap3.client.ui.html.Br;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.junit.client.impl.JUnitHost.TestInfo;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class VoicePage extends Composite {

	@UiField
	HTMLPanel voicePagePanel, testInformationPanel;
	@UiField
	Modal voicePageModal;
	@UiField
	ModalBody voicePageModalBody;
	@UiField
	Button buttonModalNext;

	MicrophoneTest microphoneTest;

	Button configureMicButton, concussionTestButton, dysarthiaTestButton;

	Label labelForTest, labelForHistory;

	VerticalPanel testPanel;

	HorizontalPanel historyPanel;

	static boolean isMicrophoneAllowed = false;
	static boolean isMicrophoneLevelOk = false;
	private double microphoneLevel = 0;

	String selectedTest = "";

	private static VoicePageUiBinder uiBinder = GWT
			.create(VoicePageUiBinder.class);

	interface VoicePageUiBinder extends UiBinder<Widget, VoicePage> {
	}

	public VoicePage() {
		initWidget(uiBinder.createAndBindUi(this));
		configureMicButton = new Button("Configure Microphone");
		configureMicButton.setType(ButtonType.DEFAULT);
		configureMicButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				configureMicrophone();
			}
		});

		concussionTestButton = new Button("Concussion test");
		concussionTestButton.setDataTarget("#" + voicePageModal.getId());
		concussionTestButton.setDataToggle(Toggle.MODAL);
		concussionTestButton.setType(ButtonType.LINK);
		concussionTestButton.addStyleName("col-lg-offset-6");
		concussionTestButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				// voicePagePanel.clear();
				// voicePagePanel.add(new ConcussionTest());
				selectedTest = "concussion";
				voicePagePanel.add(voicePageModal);
			}
		});

		dysarthiaTestButton = new Button("Dysarthia test");
		dysarthiaTestButton.setDataTarget("#" + voicePageModal.getId());
		dysarthiaTestButton.setDataToggle(Toggle.MODAL);
		dysarthiaTestButton.setType(ButtonType.LINK);
		dysarthiaTestButton.addStyleName("col-lg-offset-6");
		dysarthiaTestButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				// voicePagePanel.clear();
				// voicePagePanel.add(new DysarthiaTest());
				selectedTest = "dysarthia";
				voicePagePanel.add(voicePageModal);

			}
		});

		testPanel = new VerticalPanel();
		labelForTest = new Label("Click to start one of the following test:");
		labelForTest.addStyleName("col-lg-offset-3 h2");

		labelForHistory = new Label(
				"There will be a table or list that will show history of speech test taken by user and status of each test");
		labelForTest.addStyleName("col-lg-offset-3 h3");

		testPanel.add(labelForTest);
		testPanel.add(new Br());
		testPanel.add(concussionTestButton);
		testPanel.add(dysarthiaTestButton);
		testPanel.add(new Br());
		testPanel.add(new Br());
		testPanel.add(new Br());
		testPanel.add(new Br());
		testPanel.add(new Br());
		testPanel.add(labelForHistory);

		constructPage();
	}

	public VoicePage(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public native void startMicrophoneTest(VoicePage voicePage, String duration)/*-{
		$wnd.updateMicrophoneLevel = $entry(function(level) {
			console.log("update mic level");
			voicePage.@cse.mlab.hvr.client.VoicePage::accumulateMicLevel(Ljava/lang/String;)(level);
		});
		
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
		if(microphoneLevel < 10){
			buttonModalNext.setText("Try Again");
		} else {
			buttonModalNext.setText("Ok");
			checkMicLevel();
		}
		
	}
	
	public native void checkMicLevel()/*-{
		$wnd.checkMicrophoneLevel();
	}-*/;
	public void accumulateMicLevel(String level){
		double temp = Double.parseDouble(level);
		microphoneLevel += temp;
	}

	@UiHandler("buttonModalNext")
	public void nextButtonPressed(ClickEvent event) {
		if (buttonModalNext.getText().toLowerCase().equals("next")) {
			voicePageModalBody.remove(testInformationPanel);
			voicePageModal.setTitle("Microphone Test");
			buttonModalNext.setText("Start");
			microphoneTest = new MicrophoneTest();
			buttonModalNext.setDataDismiss(ButtonDismiss.ALERT);
			voicePageModalBody.add(microphoneTest);
			if(!isMicrophoneAllowed){
				configureMicrophone();
			}
		} else if (buttonModalNext.getText().toLowerCase().equals("start") || buttonModalNext.getText().toLowerCase().startsWith("try")) {
			microphoneTest.animateTimer();
			buttonModalNext.setVisible(false);
			voicePageModal.setClosable(false);
			startMicrophoneTest(this, String.valueOf(MicrophoneTest.testDuration));
		} else {
			voicePageModalBody.remove(microphoneTest);
			voicePageModalBody.add(testInformationPanel);
			buttonModalNext.setText("Next");
			buttonModalNext.setDataDismiss(ButtonDismiss.MODAL);

			voicePagePanel.clear();
			if (selectedTest == "dysarthia") {
				voicePagePanel.add(new DysarthiaTest());
			} else if (selectedTest == "concussion") {
				voicePagePanel.add(new ConcussionTest());
			}

		}
	}

	private void constructPage() {
		if (!isMicrophoneAllowed) {
			voicePagePanel.add(configureMicButton);
		}
		voicePagePanel.add(new Br());
		voicePagePanel.add(new Br());
		voicePagePanel.add(new Br());
		voicePagePanel.add(testPanel);
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

	@Override
	protected void onLoad() {
		// TODO Auto-generated method stub
		super.onLoad();

		// this.voicePagePanel.clear();
		// this.voicePagePanel.add(new Br());
		// this.voicePagePanel.add(new Br());
		updateMicroPhoneAccessiblity();
		if (isMicrophoneAllowed) {
			this.voicePagePanel.remove(configureMicButton);
		}

		Hvr.getEventBus().addHandler(TestCompletionEvent.TYPE,
				new TestCompletionEventHandler() {

					@Override
					public void actionAfterTestCompleted(
							TestCompletionEvent event) {
						// TODO Auto-generated method stub
						voicePagePanel.clear();
						constructPage();
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
