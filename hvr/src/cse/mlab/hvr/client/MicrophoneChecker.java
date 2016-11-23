package cse.mlab.hvr.client;

import java.util.ArrayList;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.client.events.MicrophoneCheckEvent;

public class MicrophoneChecker extends Composite {
	
	@UiField
	HTMLPanel startPanel, recordingPanel, listeningPanel, soundPanel, finalPanel;
	
	@UiField
	Button buttonYes, buttonNo, buttonStart, buttonListen;
	
	@UiField
	Heading recordingLabel;
	
	private Div timerDiv = new Div();
	
	private static final String testFileName = "testrecording";
	private static final int recordingTime = 4000;

	private static MicrophoneCheckerUiBinder uiBinder = GWT
			.create(MicrophoneCheckerUiBinder.class);

	interface MicrophoneCheckerUiBinder extends
			UiBinder<Widget, MicrophoneChecker> {
	}

	public MicrophoneChecker() {
		initWidget(uiBinder.createAndBindUi(this));
		initializeChecker();
	}
	
	private void initializeChecker(){
		startPanel.setVisible(true);
		recordingPanel.setVisible(false);
		listeningPanel.setVisible(false);
		soundPanel.setVisible(false);
		finalPanel.setVisible(false);
	}
	
	@UiHandler("buttonStart")
	void startRecording(ClickEvent event){
		recordingLabel.setText(randomPhraseGenerator());
		
		startPanel.setVisible(false);
		recordingPanel.setVisible(true);
		listeningPanel.setVisible(false);
		soundPanel.setVisible(false);
		finalPanel.setVisible(false);
		startRecording();
		if(timerDiv != null){
			timerDiv.removeFromParent();
		}
		timerDiv = new Div();
		timerDiv.setId("timer_circle");
		timerDiv.setStyleName("center-block");					
		animateTimer();
	}
	private void animateTimer() {
		recordingPanel.add(timerDiv);
		int duration = recordingTime;
		animateTimerJS(String.valueOf(duration));
		Timer timer = new Timer() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				startPanel.setVisible(false);
				recordingPanel.setVisible(false);
				listeningPanel.setVisible(true);
				soundPanel.setVisible(false);
				finalPanel.setVisible(false);
				stopRecording();
			}
		};
		timer.schedule(duration + 1000);
	}

	public native void animateTimerJS(String duration)/*-{
		$wnd.animateCircleTimer(duration);
	}-*/;
	
	@UiHandler("buttonListen")
	void listenRecording(ClickEvent event){
		startPanel.setVisible(false);
		recordingPanel.setVisible(false);
		listeningPanel.setVisible(false);
		soundPanel.setVisible(true);
		finalPanel.setVisible(false);
		
		int duration = recordingTime;
		Timer timer = new Timer() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				startPanel.setVisible(false);
				recordingPanel.setVisible(false);
				listeningPanel.setVisible(false);
				soundPanel.setVisible(false);
				finalPanel.setVisible(true);
			}
		};
		timer.schedule(duration + 1000);
		playRecording();
		
	}
	
	@UiHandler("buttonNo")
	void repeatRecording(ClickEvent event){
		initializeChecker();
	}
	
	@UiHandler("buttonYes")
	void loadSpeechTest(ClickEvent event){
		Hvr.getEventBus().fireEvent(new MicrophoneCheckEvent());
	}
	
	void startRecording() {
		startRecordingJS(this);
	}

	public native void startRecordingJS(MicrophoneChecker checker)/*-{
		var name = @cse.mlab.hvr.client.MicrophoneChecker::testFileName;
		$wnd.FWRecorder.configure(44, 90, 0, 0.1);		
		$wnd.FWRecorder.record(name, name.concat(".wav"));
	}-*/;

	void stopRecording() {
		stopRecordingJS(this);
	}

	public native void stopRecordingJS(MicrophoneChecker checker)/*-{
		var name = @cse.mlab.hvr.client.MicrophoneChecker::testFileName;
		//console.log("will stop recording from custom player ".concat(name))
		$wnd.FWRecorder.stopRecording(name);
	}-*/;
	
	void playRecording() {
		playRecording(this);
	}

	public static native void playRecording(MicrophoneChecker checker)/*-{
		var name = @cse.mlab.hvr.client.MicrophoneChecker::testFileName;
		console.log("will playback from custom player ".concat(name))
		var duration = $wnd.FWRecorder.duration(name);
		$wnd.FWRecorder.playBack(name);
	}-*/;

	private static String randomPhraseGenerator(){
		ArrayList<String> randomPhrases = new ArrayList<>();
		randomPhrases.add(" The quick brown fox jumps over the lazy dog.");
		randomPhrases.add("I will never be this young again. I just got older.");
		randomPhrases.add(" I love eating toasted cheese and tuna sandwiches.");
		randomPhrases.add("Lets all be unique together until we realise we are all the same.");
		
		int randomIndex = (int) (Math.random() * (randomPhrases.size() + 1));
		try {
			return randomPhrases.get(randomIndex);
		} catch (Exception e) {
			// TODO: handle exception
			return randomPhrases.get(0);
		}
	}
}
