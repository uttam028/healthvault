package cse.mlab.hvr.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class DysarthiaTest extends Composite {

	private int currentPlayerIndex = 0;
	private Boolean testLoaded = false;
	@UiField
	HTMLPanel dysarthiaTestPanel;

	private static DysarthiaTestUiBinder uiBinder = GWT
			.create(DysarthiaTestUiBinder.class);

	interface DysarthiaTestUiBinder extends UiBinder<Widget, DysarthiaTest> {
	}

	public DysarthiaTest() {
		initWidget(uiBinder.createAndBindUi(this));
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
			Fragment[] fragments8 = { new ImageFragment("images/illusion.jpg",
					10000, "Describe the image in your own words") };

			final CustomPlayer[] players = new CustomPlayer[1];

			// players[0] = new CustomPlayer("Speech Test 1", fragments1,
			// "samples/con_test1.mp3");
			// players[1] = new CustomPlayer("Speech Test 2", fragments2,
			// "samples/con_test2.mp3");
			// players[2] = new CustomPlayer("Speech Test 3", fragments3, "");
			// players[3] = new CustomPlayer("Speech Test 4", fragments4, "");
			// players[4] = new CustomPlayer("Speech Test 5", fragments5, "");
			// players[5] = new CustomPlayer("Speech Test 6", fragments6, "");
			// players[6] = new CustomPlayer("Speech Test 7", fragments7, "");
			String textAboutTest0 = "This is test about......";
			players[0] = new CustomPlayer("Image Test", fragments8,
					"videos/test01_003.mp4", textAboutTest0);

			this.dysarthiaTestPanel.add(players[0]);
			this.initiateSplittedProgress(String.valueOf(players.length));

			Hvr.getEventBus().addHandler(FileUploadSuccessEvent.TYPE,
					new FileUploadSuccessEventHandler() {

						@Override
						public void actionAfterFileUpload(
								FileUploadSuccessEvent event) {
							// TODO Auto-generated method stub
							currentPlayerIndex++;
							// Window.alert("I have got the fire of the event");
							try {
								if (currentPlayerIndex > 0) {
									dysarthiaTestPanel
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
									dysarthiaTestPanel
											.add(players[currentPlayerIndex]);
									String percentage = String.valueOf(((100.0/players.length) * currentPlayerIndex));
									makeSplittedProgress(percentage);

								} else {
									currentPlayerIndex = 0;
									testLoaded = false;
									Hvr.getEventBus().fireEvent(
											new TestCompletionEvent());
								}

							} catch (Exception e) {
								Window.alert("Exception occurred, index:"
										+ currentPlayerIndex + ", len:"
										+ players.length);
							}

						}
					});
			testLoaded = true;
		}

	}

}
