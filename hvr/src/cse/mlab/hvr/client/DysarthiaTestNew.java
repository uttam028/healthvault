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

import cse.mlab.hvr.client.events.FileUploadEvent;
import cse.mlab.hvr.client.events.FileUploadEventHandler;
import cse.mlab.hvr.client.fragments.AudioBasedFragment;
import cse.mlab.hvr.client.fragments.ButtonControlledTextFragment;
import cse.mlab.hvr.client.fragments.CommonInstructionFragment;
import cse.mlab.hvr.client.fragments.TimerControlledImageFragment;
import cse.mlab.hvr.client.fragments.TimerControlledTextFragment;

public class DysarthiaTestNew extends Composite {

	static int currentDysPlayerIndex = 0;
	static boolean dysTestRunning = false;
	private Boolean testLoaded = false;
	@UiField
	HTMLPanel dysarthiaTestPanel;
	//, dysarthriaRecorderPanel;

	Button buttonSaveLater;
	final AudioBasedCustomPlayer[] players = new AudioBasedCustomPlayer[13];
	
	AudioBasedFragment[] fragments1 = {new CommonInstructionFragment("audio/repeatSentences.mp3", "Please repeat the sentences after I say them"),
							new ButtonControlledTextFragment("audio/sentences1.mp3", "We saw several wild animals.", 5000),
							new ButtonControlledTextFragment("audio/sentences2.mp3", "My physician wrote out a prescription.", 5000),
							new ButtonControlledTextFragment("audio/sentences3.mp3", "The municipal judge sentenced the criminal.", 5000)};
	
	AudioBasedFragment[] fragments2 = {new CommonInstructionFragment("audio/repeatWords.mp3", "Please repeat the words after I say them"),
									new ButtonControlledTextFragment("audio/comp1.mp3", "Participate", 2500),
									new ButtonControlledTextFragment("audio/comp2.mp3", "Application", 2500),
									new ButtonControlledTextFragment("audio/comp3.mp3", "Education", 2500),
									new ButtonControlledTextFragment("audio/comp4.mp3", "Difficulty", 2500),
									new ButtonControlledTextFragment("audio/comp5.mp3", "Congratulations", 2500),
									new ButtonControlledTextFragment("audio/comp6.mp3", "Possibility", 2500),
									new ButtonControlledTextFragment("audio/comp7.mp3", "Mathematical", 2500),
									new ButtonControlledTextFragment("audio/comp8.mp3", "Opportunity", 3000),
									new ButtonControlledTextFragment("audio/comp9.mp3", "Statistical Analysis", 4000),
									new ButtonControlledTextFragment("audio/comp10.mp3", "Methodist Episcopal Church", 5000)};
	
	AudioBasedFragment[] fragments3 = {new TimerControlledImageFragment("audio/picture.mp3", "images/illusion.jpg", 5000)};
	
	//ahWithTimer
	AudioBasedFragment[] fragments4 = {new CommonInstructionFragment("", "Please say aaaaahh for 5 seconds"),
			new TimerControlledTextFragment("audio/ahWithTimer.mp3", "",5000)};
	
	AudioBasedFragment[] fragments5 = {new CommonInstructionFragment("audio/ahNoTimer.mp3", "Please say aaaaahh for as long as you can"),
			new ButtonControlledTextFragment("", "", 6000)};
	
	AudioBasedFragment[] fragments6 = {new CommonInstructionFragment("", "Please listen and follow the oral instructions"),
			new TimerControlledTextFragment("audio/pa.mp3", "", 5000),
			new TimerControlledTextFragment("audio/ta.mp3", "", 5000),
			new TimerControlledTextFragment("audio/ka.mp3", "", 5000),
			new TimerControlledTextFragment("audio/pataka.mp3", "", 5000)};
	
	
	AudioBasedFragment[] fragments7 = {new CommonInstructionFragment("audio/monthOfYear.mp3", "Please say the months of the year, beginning with January"),
			new ButtonControlledTextFragment("", "", 12000)};

	AudioBasedFragment[] fragments8 = {new ButtonControlledTextFragment("audio/passage.mp3", "You wish to know all about my grandfather. Well, he is nearly"
					+ " 93 years old, yet he still thinks as swiftly as ever. He dresses himself in an old black frock coat,"
					+ " usually several buttons missing. A long beard clings to this chin, giving those who observe him a pronounced feeling of the utmost respect."
					+ " When he speaks, his voice is just a bit cracked and quivers a bit. Twice each day he plays skillfully and"
					+ " with zest upon a small organ. Except in the winter when the snow or ice prevents, he slowly takes a short walk in"
					+ " the open air each day. We have often urged him to walk more and smoke less, but he always answers, \"Banana Oil!\""
					+ " Grandfather likes to be modern in his language", 0)};
	

	AudioBasedFragment[] fragments9 = {new CommonInstructionFragment("audio/repeatWords.mp3", "Please repeat the words after I say them"),
			new ButtonControlledTextFragment("audio/easy1.mp3", "Mom", 1500),
			new ButtonControlledTextFragment("audio/easy2.mp3", "Bob", 1500),
			new ButtonControlledTextFragment("audio/easy3.mp3", "Peep", 1500),
			new ButtonControlledTextFragment("audio/easy4.mp3", "Bib", 1500),
			new ButtonControlledTextFragment("audio/easy5.mp3", "Tot", 1500),
			new ButtonControlledTextFragment("audio/easy6.mp3", "Deed", 1500),
			new ButtonControlledTextFragment("audio/easy7.mp3", "Kick", 1500),
			new ButtonControlledTextFragment("audio/easy8.mp3", "Gag", 1500),
			new ButtonControlledTextFragment("audio/easy9.mp3", "Fife", 1500),
			new ButtonControlledTextFragment("audio/easy10.mp3", "Sis", 1500),
			new ButtonControlledTextFragment("audio/easy11.mp3", "Zoos", 1500),
			new ButtonControlledTextFragment("audio/easy12.mp3", "Church", 2000),
			new ButtonControlledTextFragment("audio/easy13.mp3", "Shush", 2000),
			new ButtonControlledTextFragment("audio/easy14.mp3", "Lull", 1500)};

	AudioBasedFragment[] fragments10 = {new CommonInstructionFragment("audio/repeatWords.mp3", "Please repeat the words after I say them"),
			new ButtonControlledTextFragment("audio/increasing1.mp3", "Cat", 1500),
			new ButtonControlledTextFragment("audio/increasing2.mp3", "Catnip", 2000),
			new ButtonControlledTextFragment("audio/increasing3.mp3", "Catapult", 2000),
			new ButtonControlledTextFragment("audio/increasing4.mp3", "Catastrophe", 2500),
			new ButtonControlledTextFragment("audio/increasing5.mp3", "Please", 1500),
			new ButtonControlledTextFragment("audio/increasing6.mp3", "Pleasing", 2000),
			new ButtonControlledTextFragment("audio/increasing7.mp3", "Pleasingly", 2500),
			new ButtonControlledTextFragment("audio/increasing8.mp3", "Thick", 1500),
			new ButtonControlledTextFragment("audio/increasing9.mp3", "Thicken", 2000),
			new ButtonControlledTextFragment("audio/increasing10.mp3", "Thickening", 2500)};

	AudioBasedFragment[] fragments11 = {new CommonInstructionFragment("audio/repeatSentences.mp3", "Please repeat the sentences after I say them"),
			new ButtonControlledTextFragment("audio/arizona1.mp3", "The supermarket chain shut down because of poor management.", 8000),
			new ButtonControlledTextFragment("audio/arizona2.mp3", "Much more money must be donated to make this department succeed.", 9000),
			new ButtonControlledTextFragment("audio/arizona3.mp3", "In this famous coffee shop they serve the best doughnuts in town.", 9000),
			new ButtonControlledTextFragment("audio/arizona4.mp3", "The chairman decided to pave over the shopping center garden.", 9000),
			new ButtonControlledTextFragment("audio/arizona5.mp3", "The standards committee met this afternoon in an open meeting.", 9000)};

	AudioBasedFragment[] fragments12 = {new CommonInstructionFragment("audio/1to30.mp3", "Please say the numbers from one to thirty"),
			new ButtonControlledTextFragment("", "", 25000)};

	AudioBasedFragment[] fragments13 = {new CommonInstructionFragment("audio/daysOfWeek.mp3", "Please say the days of the week beginning with Monday"),
			new ButtonControlledTextFragment("", "", 8000)};
	
	static boolean loadDysPlayerFromSaveState = false;

	private static DysarthiaTestUiBinder uiBinder = GWT
			.create(DysarthiaTestUiBinder.class);

	interface DysarthiaTestUiBinder extends UiBinder<Widget, DysarthiaTestNew> {
	}

	public DysarthiaTestNew() {
		initWidget(uiBinder.createAndBindUi(this));

		players[0] = new AudioBasedCustomPlayer("Test 1", 1, fragments1);
		players[1] = new AudioBasedCustomPlayer("Test 2", 2, fragments2);
		players[2] = new AudioBasedCustomPlayer("Test 3", 3, fragments3);
		players[3] = new AudioBasedCustomPlayer("Test 4", 4, fragments4);
		players[4] = new AudioBasedCustomPlayer("Test 5", 5, fragments5);
		players[5] = new AudioBasedCustomPlayer("Test 6", 6, fragments6);
		players[6] = new AudioBasedCustomPlayer("Test 7", 7, fragments7);
		players[7] = new AudioBasedCustomPlayer("Test 8", 8, fragments8);
		players[8] = new AudioBasedCustomPlayer("Test 9", 9, fragments9);
		players[9] = new AudioBasedCustomPlayer("Test 10", 10, fragments10);
		players[10] = new AudioBasedCustomPlayer("Test 11", 11, fragments11);
		players[11] = new AudioBasedCustomPlayer("Test 12", 12, fragments12);
		players[12] = new AudioBasedCustomPlayer("Test 13", 13, fragments13);
		
		Hvr.getEventBus().addHandler(FileUploadEvent.TYPE,
				new FileUploadEventHandler() {

					@Override
					public void actionAfterFileUpload(
							FileUploadEvent event) {
						// TODO Auto-generated method stub
						if (dysTestRunning) {
							currentDysPlayerIndex++;
							// Window.alert("I have got the fire of the event");
							try {
								if (currentDysPlayerIndex > 0) {
									dysarthiaTestPanel.remove(players[currentDysPlayerIndex - 1]);
//									dysarthriaRecorderPanel.remove(players[currentDysPlayerIndex - 1]);
								}
							} catch (Exception e) {
								// TODO: handle exception
								Window.alert("Exception occurred at removing player, index:"
										+ currentDysPlayerIndex
										+ ", len:" + players.length);
							}

							try {
								if (currentDysPlayerIndex < players.length) {
									dysarthiaTestPanel.add(players[currentDysPlayerIndex]);
//									dysarthriaRecorderPanel.add(players[currentDysPlayerIndex]);
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
//									Hvr.getEventBus().fireEvent(
//											new SpeechTestEvent(new SpeechTestState("1", TestState.COMPLETED)));
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

	public DysarthiaTestNew(String firstName) {
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
			int counter1 = 0, counter2 = 0;
			
			try {
				for (int i = 0; i < currentDysPlayerIndex; i++) {
					String elementId = "dystestprogress-" + (i + 1);
					DOM.getElementById(elementId)
							.setClassName("progtrckr-done");
					counter1++;
				}
				for(int i=currentDysPlayerIndex;i<players.length;i++){
					String elementId = "dystestprogress-" + (i + 1);
					DOM.getElementById(elementId)
							.setClassName("progtrckr-todo");
					counter2++;					
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				Window.alert("exception : "+ e.getMessage() + ", c1 : " + counter1 + ", c2 : " + counter2);
			}
			
			if (loadDysPlayerFromSaveState) {
				// this.dysarthiaTestPanel.clear();
				// currentDysPlayerIndex--;
				this.dysarthiaTestPanel.add(players[currentDysPlayerIndex]);
//				this.dysarthriaRecorderPanel.add(players[currentDysPlayerIndex]);

			} else {

				this.dysarthiaTestPanel.add(players[0]);
//				this.dysarthriaRecorderPanel.add(players[0]);
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
//		Hvr.getEventBus().fireEvent(new SpeechTestEvent(new SpeechTestState("1", TestState.SAVED)));
	}

}
