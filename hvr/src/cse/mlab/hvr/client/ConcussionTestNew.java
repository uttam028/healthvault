package cse.mlab.hvr.client;

import org.gwtbootstrap3.client.ui.Button;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
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
import cse.mlab.hvr.client.events.TestCompletionEvent;
import cse.mlab.hvr.client.fragments.AudioBasedFragment;
import cse.mlab.hvr.client.fragments.ButtonControlledTextFragment;
import cse.mlab.hvr.client.fragments.CommonInstructionFragment;
import cse.mlab.hvr.client.fragments.TimerControlledImageFragment;

public class ConcussionTestNew extends Composite {

	static int currentPlayerIndex = 0;
	static boolean conTestRunning = false;
	private Boolean testLoaded = false;

	Button buttonSaveLaterCon;
	
	final AudioBasedCustomPlayer[] players = new AudioBasedCustomPlayer[3];
	
	
	AudioBasedFragment[] fragments1 = {new CommonInstructionFragment("audio/concussion/1-instruction.m4a", "Please repeat the words after I say them"),
									new ButtonControlledTextFragment("audio/concussion/1-participate.m4a", "Participate", 2500),
									new ButtonControlledTextFragment("audio/concussion/1-application.m4a", "Application", 2500),
									new ButtonControlledTextFragment("audio/concussion/1-education.m4a", "Education", 2500),
									new ButtonControlledTextFragment("audio/concussion/1-difficulty.m4a", "Difficulty", 2500),
									new ButtonControlledTextFragment("audio/concussion/1-congratulations.m4a", "Congratulations", 2500),
									new ButtonControlledTextFragment("audio/concussion/1-possibility.m4a", "Possibility", 2500),
									new ButtonControlledTextFragment("audio/concussion/1-mathematical.m4a", "Mathematical", 2500),
									new ButtonControlledTextFragment("audio/concussion/1-opportunity.m4a", "Opportunity", 3000)};

	AudioBasedFragment[] fragments2 = {new CommonInstructionFragment("audio/concussion/2-instruction.mp3", "Please repeat the sentences after I say them"),
			new ButtonControlledTextFragment("audio/concussion/2-sentence.mp3", "We saw several wild animals.", 6000)};
	
	AudioBasedFragment[] fragments3 = {new CommonInstructionFragment("audio/concussion/3-instructions.m4a", "Repeat the sentences after me, also emphashize on highlighed word"),
			new ButtonControlledTextFragment("audio/concussion/3-put.m4a", "PUT the book here", 6000),
			new ButtonControlledTextFragment("audio/concussion/3-book.m4a", "Put the BOOK here", 6000),
			new ButtonControlledTextFragment("audio/concussion/3-here.m4a", "Put the book HERE", 6000)};
	

	

	static boolean loadConcPlayerFromSaveState = false;

	@UiField
	HTMLPanel concussionTestPanel;
	private static ConcussionTestUiBinder uiBinder = GWT
			.create(ConcussionTestUiBinder.class);

	interface ConcussionTestUiBinder extends UiBinder<Widget, ConcussionTestNew> {
	}

	public ConcussionTestNew() {
		initWidget(uiBinder.createAndBindUi(this));

		players[0] = new AudioBasedCustomPlayer("Speech Test 1", fragments1);
		players[1] = new AudioBasedCustomPlayer("Speech Test 2", fragments2);
		players[2] = new AudioBasedCustomPlayer("Speech Test 3", fragments3);
		
		
		Hvr.getEventBus().addHandler(FileUploadSuccessEvent.TYPE,
				new FileUploadSuccessEventHandler() {

					@Override
					public void actionAfterFileUpload(
							FileUploadSuccessEvent event) {
						// TODO Auto-generated method stub
						if (conTestRunning) {
							currentPlayerIndex++;
							// Window.alert("I have got the fire of the event");
							try {
								if (currentPlayerIndex > 0) {
									concussionTestPanel
											.remove(players[currentPlayerIndex - 1]);
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
									concussionTestPanel
											.add(players[currentPlayerIndex]);
									// String percentage = String
									// .valueOf((100.0 / players.length)
									// * currentPlayerIndex);
									// makeSplittedProgress(percentage);
									String elementId = "conctestprogress-"
											+ currentPlayerIndex;
									DOM.getElementById(elementId)
											.setClassName(
													"progtrckr-done");
								} else {
									loadConcPlayerFromSaveState = false;
									updateConTestRunningStatus(false);
									currentPlayerIndex = 0;
									testLoaded = false;
									Hvr.getEventBus().fireEvent(
											new TestCompletionEvent(new SpeechTestState("2", TestState.COMPLETED)));
								}

							} catch (Exception e) {
								Window.alert("Exception occurred, index:"
										+ currentPlayerIndex
										+ ", len:"
										+ players.length);
							}
						}
					}
				});
	}

	public ConcussionTestNew(String firstName) {
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
		// DOM.getElementById("conctestprogress-1").setClassName("progtrckr-done");
		if (!testLoaded) {
			for (int i = 0; i < currentPlayerIndex; i++) {
				String elementId = "conctestprogress-" + (i + 1);
				DOM.getElementById(elementId)
						.setClassName("progtrckr-done");
			}
			for(int i=currentPlayerIndex;i<players.length;i++){
				String elementId = "conctestprogress-" + (i + 1);
				DOM.getElementById(elementId)
						.setClassName("progtrckr-todo");				
			}
			
			if (loadConcPlayerFromSaveState) {
				// currentPlayerIndex--;
				// this.concussionTestPanel.clear();
				this.concussionTestPanel.add(players[currentPlayerIndex]);

			} else {

				// Fragment[] fragments8 = { new
				// ImageFragment("images/illusion.jpg",
				// 10000, "Describe the image in your own words") };

				// players[7] = new CustomPlayer("Speech Test 8", fragments8,
				// "videos/test01_003.mp4", textAboutTests[7]);

				this.concussionTestPanel.add(players[0]);
				currentPlayerIndex = 0;
				// this.initiateSplittedProgress(String.valueOf(players.length));


				testLoaded = true;
			}
		}
	}

	protected void updateConTestRunningStatus(boolean state) {
		conTestRunning = state;
	}

	@UiHandler("buttonSaveLaterCon")
	void saveLaterConcussionClicked(ClickEvent event) {
		updateConTestRunningStatus(false);
		loadConcPlayerFromSaveState = true;
		//this.concussionTestPanel.clear();
		Hvr.getEventBus().fireEvent(new TestCompletionEvent(new SpeechTestState("2", TestState.SAVED)));
	}

	public final native NodeList<Element> querySelector(String selector)/*-{
		return $wnd.document.querySelectorAll(selector);
	}-*/;

}
