package cse.mlab.hvr.client;

import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class MicrophoneTest extends Composite {
	@UiField
	HTMLPanel timerPanel, instructionPanel;

	@UiField
	Label instructionText;

	boolean loded = false;
	String header = "mictest";
	static final int testDuration = 10 * 1000;
	Div timerDiv;

	private static MicrophoneTestUiBinder uiBinder = GWT
			.create(MicrophoneTestUiBinder.class);

	interface MicrophoneTestUiBinder extends UiBinder<Widget, MicrophoneTest> {
	}

	public MicrophoneTest() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public MicrophoneTest(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public native void initiateTestTimer(String duration)/*-{
		$wnd.initiateMicrophoneTestTimer(duration);
	}-*/;

	public native void animateTimer()/*-{
		$wnd.animateMicrophoneTestTimer();
	}-*/;
	
	public native void initiateCheckbox()/*-{
		$wnd.initiateCheckbox();
	}-*/;
	
	public void reloadTestTimer(){
		timerPanel.clear();
		timerDiv = new Div();
		timerDiv.setId("mic_test_timer_circle");
		timerDiv.setStyleName("half-center-block");
		timerPanel.add(timerDiv);
		initiateTestTimer(String.valueOf(testDuration));		
	}

	@Override
	protected void onLoad() {
		// TODO Auto-generated method stub
		super.onLoad();
		// initiateTestTimer();
		if (!loded) {
			timerDiv = new Div();
			timerDiv.setId("mic_test_timer_circle");
			timerDiv.setStyleName("half-center-block");
			timerPanel.add(timerDiv);
			initiateTestTimer(String.valueOf(testDuration));
			initiateCheckbox();
			loded = true;
		}
	}
}
