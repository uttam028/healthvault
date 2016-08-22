package cse.mlab.hvr.client;

import org.gwtbootstrap3.client.ui.ProgressBar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.client.SpeechTestState.TestState;
import cse.mlab.hvr.client.events.FileUploadSuccessEvent;
import cse.mlab.hvr.client.events.FileUploadSuccessEventHandler;
import cse.mlab.hvr.client.events.SpeechTestEvent;
import cse.mlab.hvr.shared.study.SpeechTest;

public class CustomPlayerManager extends Composite {
	static int currentPlayerIndex = 0;
	static boolean speechTestRunning = false;
	private Boolean testLoaded = false;
	@UiField
	HTMLPanel playerManagerPanel;
	
	@UiField
	ProgressBar playerProgressBar;


	final AudioBasedCustomPlayer[] players;

	private static CustomPlayerManagerUiBinder uiBinder = GWT
			.create(CustomPlayerManagerUiBinder.class);

	interface CustomPlayerManagerUiBinder extends
			UiBinder<Widget, CustomPlayerManager> {
	}

	public CustomPlayerManager(final String studyId, final SpeechTest metadata) {
		initWidget(uiBinder.createAndBindUi(this));
		players = CustomPlayerCreator.getInstance().getAudioBasedCustomPlayer(
				metadata);
		
		this.playerManagerPanel.add(players[0]);
		currentPlayerIndex=0;
		updatePlayerProgress();
		updateSpeechTestRunningStatus(true);

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
									String elementId = "dystestprogress-"
											+ currentPlayerIndex;
									DOM.getElementById(elementId).setClassName(
											"progtrckr-done");

								} else {
//									loadDysPlayerFromSaveState = false;
									updateSpeechTestRunningStatus(false);
									currentPlayerIndex = 0;
									updatePlayerProgress();
									testLoaded = false;
									Hvr.getEventBus().fireEvent(
											new SpeechTestEvent(
													new SpeechTestState(studyId, metadata.getTestId(), TestState.COMPLETED)));
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
	
	private void updatePlayerProgress(){
		//playerProgressBar.set
		double percent = ((currentPlayerIndex * 1.0)/this.players.length) * 100;
		playerProgressBar.setPercent(percent);
		playerProgressBar.setText(Math.round(percent) + "%");
	}
	
	
	protected void updateSpeechTestRunningStatus(boolean state) {
		speechTestRunning = state;
	}

}
