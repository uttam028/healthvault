package cse.mlab.hvr.client;

import org.gwtbootstrap3.client.ui.Button;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.client.SpeechTestState.TestState;
import cse.mlab.hvr.client.events.FileUploadSuccessEvent;
import cse.mlab.hvr.client.events.FileUploadSuccessEventHandler;
import cse.mlab.hvr.client.events.SpeechTestEvent;
import cse.mlab.hvr.client.fragments.Fragment;
import cse.mlab.hvr.client.fragments.ImageFragment;
import cse.mlab.hvr.client.fragments.MarqueeFragment;
import cse.mlab.hvr.client.fragments.PauseFragment;
import cse.mlab.hvr.client.fragments.SimpleTextFragment;

public class DysarthiaTest extends Composite {

	static int currentDysPlayerIndex = 0;
	static boolean dysTestRunning = false;
	private Boolean testLoaded = false;
	@UiField
	HTMLPanel dysarthiaTestPanel;

	Button buttonSaveLater;
	final CustomPlayer[] players = new CustomPlayer[5];
	Fragment[] fragments1 = {
			new SimpleTextFragment("We saw several wild animals", 5000),
			new PauseFragment(1500),
			new SimpleTextFragment("My physician wrote out a prescription",
					5000),
			new PauseFragment(1500),
			new SimpleTextFragment(
					"The municipal judge sentenced the criminal", 5000) };

	Fragment[] fragments2 = { new SimpleTextFragment("Participate", 2500),
			new PauseFragment(500),
			new SimpleTextFragment("Application", 2500),
			new PauseFragment(500), new SimpleTextFragment("Education", 2500),
			new PauseFragment(500), new SimpleTextFragment("Difficulty", 2500),
			new PauseFragment(500),
			new SimpleTextFragment("Congratulations", 2500),
			new PauseFragment(500),
			new SimpleTextFragment("Possibility", 2500),
			new PauseFragment(500),
			new SimpleTextFragment("Mathematical", 2500),
			new PauseFragment(500),
			new SimpleTextFragment("Opportunity", 2500),
			new PauseFragment(500),
			new SimpleTextFragment("Statistical Analysis", 2500),
			new PauseFragment(500),
			new SimpleTextFragment("Methodist Episcopal Church", 2500) };

	Fragment[] fragments3 = { new ImageFragment("images/illusion.jpg", 10000,
			"Describe the image in your own words") };

	Fragment[] fragments4 = { new MarqueeFragment(
			"          Ahhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh", 5000, 2000, "") };

	Fragment[] fragments5 = { new MarqueeFragment(
			"          Ahhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh",
			15000, 2000,
			"Try again as long as you can until you run out of air") };

	String[] textAboutTest = new String[players.length];

	static boolean loadDysPlayerFromSaveState = false;

	private static DysarthiaTestUiBinder uiBinder = GWT
			.create(DysarthiaTestUiBinder.class);

	interface DysarthiaTestUiBinder extends UiBinder<Widget, DysarthiaTest> {
	}

	public DysarthiaTest() {
		initWidget(uiBinder.createAndBindUi(this));
		textAboutTest[0] = "At this test, you have to speak 3 different sentences. Sentences will come one by one and you have to finish every word before time expires.";
		textAboutTest[1] = "At this test, you have to speak 10 different words. Words will come one by one and you have to finish every word before time expires.";
		textAboutTest[2] = "At this test, an image will appear and you have to describe the image in your own words.";

		textAboutTest[3] = "During this test you have to speak \"Ahhhhhh.....\" in one breath. So before you click start button, take a long breath and start recording";
		textAboutTest[4] = "During this test you have to speak \"Ahhhhhh.....\" in one breath. So before you click start button, take a long breath and start recording";

		players[0] = new CustomPlayer("Speech Test 1", fragments1,
				"samples/con_test1.mp3", textAboutTest[0]);
		players[1] = new CustomPlayer("Speech Test 2", fragments2,
				"samples/con_test1.mp3", textAboutTest[1]);
		players[2] = new CustomPlayer("Speech Test 3", fragments3,
				"samples/con_test1.mp3", textAboutTest[2]);

		players[3] = new CustomPlayer("Speech Test 4", fragments4,
				"samples/con_test1.mp3", textAboutTest[3]);
		players[4] = new CustomPlayer("Speech Test 5", fragments5,
				"samples/con_test1.mp3", textAboutTest[4]);
		
		Hvr.getEventBus().addHandler(FileUploadSuccessEvent.TYPE,
				new FileUploadSuccessEventHandler() {

					@Override
					public void actionAfterFileUpload(
							FileUploadSuccessEvent event) {
						// TODO Auto-generated method stub
						if (dysTestRunning) {
							currentDysPlayerIndex++;
							// Window.alert("I have got the fire of the event");
							try {
								if (currentDysPlayerIndex > 0) {
									dysarthiaTestPanel
											.remove(players[currentDysPlayerIndex - 1]);
								}
							} catch (Exception e) {
								// TODO: handle exception
								Window.alert("Exception occurred at removing player, index:"
										+ currentDysPlayerIndex
										+ ", len:" + players.length);
							}

							try {
								if (currentDysPlayerIndex < players.length) {
									dysarthiaTestPanel
											.add(players[currentDysPlayerIndex]);
									// String percentage = String
									// .valueOf(((100.0 /
									// players.length) *
									// currentPlayerIndex));
									// makeSplittedProgress(percentage);
									String elementId = "dystestprogress-"
											+ currentDysPlayerIndex;
									DOM.getElementById(elementId)
											.setClassName(
													"progtrckr-done");

								} else {
									loadDysPlayerFromSaveState = false;
									updateDysTestRunningStatus(false);
									currentDysPlayerIndex = 0;
									testLoaded = false;
									Hvr.getEventBus().fireEvent(
											new SpeechTestEvent(new SpeechTestState("1", TestState.COMPLETED)));
								}

							} catch (Exception e) {
								Window.alert("Exception occurred, index:"
										+ currentDysPlayerIndex
										+ ", len:" + players.length);
							}
						}
					}
				});
	}

	public DysarthiaTest(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public native void initiateSplittedProgress(String splitCount)/*-{
		$wnd.initiateSplittedProgress(splitCount);
	}-*/;

	public native void makeSplittedProgress(String percent)/*-{
		$wnd.makeProgressAtSplittedBar(percent);
	}-*/;

	@Override
	protected void onLoad() {

		// TODO Auto-generated method stub
		super.onLoad();

		if (!testLoaded) {
			for (int i = 0; i < currentDysPlayerIndex; i++) {
				String elementId = "dystestprogress-" + (i + 1);
				DOM.getElementById(elementId)
						.setClassName("progtrckr-done");
			}
			for(int i=currentDysPlayerIndex;i<players.length;i++){
				String elementId = "dystestprogress-" + (i + 1);
				DOM.getElementById(elementId)
						.setClassName("progtrckr-todo");
				
			}
			
			if (loadDysPlayerFromSaveState) {
				// this.dysarthiaTestPanel.clear();
				// currentDysPlayerIndex--;

				this.dysarthiaTestPanel.add(players[currentDysPlayerIndex]);

			} else {

				this.dysarthiaTestPanel.add(players[0]);
				currentDysPlayerIndex = 0;
				// this.initiateSplittedProgress(String.valueOf(players.length));


				testLoaded = true;
			}
		}
	}

	protected void updateDysTestRunningStatus(boolean state) {
		dysTestRunning = state;
	}

	@UiHandler("buttonSaveLater")
	void saveForLaterClicked(ClickEvent event) {
		updateDysTestRunningStatus(false);
		loadDysPlayerFromSaveState = true;
		//this.dysarthiaTestPanel.clear();
		Hvr.getEventBus().fireEvent(new SpeechTestEvent(new SpeechTestState("1", TestState.SAVED)));
	}

}
