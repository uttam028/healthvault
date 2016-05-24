package cse.mlab.hvr.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class ConcussionTest extends Composite {

	private int currentPlayerIndex = 0;
	private Boolean testLoaded = false;

	@UiField
	HTMLPanel concussionTestPanel;
	private static ConcussionTestUiBinder uiBinder = GWT
			.create(ConcussionTestUiBinder.class);

	interface ConcussionTestUiBinder extends UiBinder<Widget, ConcussionTest> {
	}

	public ConcussionTest() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public ConcussionTest(String firstName) {
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
		//DOM.getElementById("conctestprogress-1").setClassName("progtrckr-done");
		if (!testLoaded) {
			Fragment[] fragments1 = new Fragment[8];
			fragments1[0] = new SimpleTextFragment("Participate", 1500);
			fragments1[1] = new SimpleTextFragment("Application", 1500);
			fragments1[2] = new SimpleTextFragment("Education", 1500);
			fragments1[3] = new SimpleTextFragment("Difficulty", 1500);
			fragments1[4] = new SimpleTextFragment("Congratulations", 1500);
			fragments1[5] = new SimpleTextFragment("Possibility", 1500);
			fragments1[6] = new SimpleTextFragment("Mathematical", 1500);
			fragments1[7] = new SimpleTextFragment("Opportunity", 1500);

			Fragment[] fragments2 = { new SimpleTextFragment(
					"We saw several wild animals", 3000) };

			Fragment[] fragments3 = {
					new MarkedTextFragment("Put the Book here", "Put", 3000,
							"Emphasis on highlighted word"),
					new PauseFragment(500),
					new MarkedTextFragment("Put the Book here", "Book", 3000,
							"Same sentence, highlighted different word"),
					new PauseFragment(500),
					new MarkedTextFragment("Put the Book here", "here", 3000,
							"See carefully, highlighted word has been changed") };

			Fragment[] fragments4 = {
					new MarqueeFragment("Pa", 8000, 1200,
							"Read moving word with the pace"),
					new MarqueeFragment("Pa", 8000, 500, "Now Go Faster...") };

			Fragment[] fragments5 = {
					new MarqueeFragment("Ka", 8000, 1200,
							"Read moving word with the pace"),
					new MarqueeFragment("Ka", 8000, 500, "Now Go Faster...") };

			Fragment[] fragments6 = {
					new MarqueeFragment("PaTaKa", 10000, 1300,
							"Read moving word with the pace"),
					new MarqueeFragment("PaTaKa", 10000, 500,
							"Now Go Faster...") };

			Fragment[] fragments7 = { new MarqueeFragment(
					"          Ahhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh", 5000,
					2000, "Keep going.......") };

			Fragment[] fragments8 = { new ImageFragment("images/illusion.jpg",
					10000, "Describe the image in your own words") };

			final CustomPlayer[] players = new CustomPlayer[8];
			final String[] textAboutTests = new String[players.length];
			textAboutTests[0] = "At this test, you have to speak 8 different words. Words will come one by one and you have to finish every word before time expires. A red circle will indicate your time limit.";
			textAboutTests[1] = "During this test, a sentence will appear within below box.";
			textAboutTests[2] = "At this test, same sentence will appear 3 times. Everytime a word will be highlighted in red color. Speak highlighted word in high tone";
			textAboutTests[3] = "At first half of this test a word \"pa\" will move from left to right, and you will speak along with the pace of the word. During second half, same word will move faster.";
			textAboutTests[4] = "This test is same as previous test, in this test word is \"ka\"";
			textAboutTests[5] = "This is also same as previous two, but the word \"PaTaKa\" is more complicated to speak, also you need to maintain the speed";
			textAboutTests[6] = "During this test you have to speak \"Ahhhhhh.....\" in one breath. So before you click start button, take a long breath and start recording";
			textAboutTests[7] = "At this test, an image will appear and you have to describe the image in your test.";

			players[0] = new CustomPlayer("Speech Test 1", fragments1,
					"videos/test01_003.mp4", textAboutTests[0]);
			players[1] = new CustomPlayer("Speech Test 2", fragments2,
					"videos/test01_003.mp4", textAboutTests[1]);
			players[2] = new CustomPlayer("Speech Test 3", fragments3,
					"videos/test01_003.mp4", textAboutTests[2]);
			players[3] = new CustomPlayer("Speech Test 4", fragments4,
					"videos/test01_003.mp4", textAboutTests[3]);
			players[4] = new CustomPlayer("Speech Test 5", fragments5,
					"videos/test01_003.mp4", textAboutTests[4]);
			players[5] = new CustomPlayer("Speech Test 6", fragments6,
					"videos/test01_003.mp4", textAboutTests[5]);
			players[6] = new CustomPlayer("Speech Test 7", fragments7,
					"videos/test01_003.mp4", textAboutTests[6]);
			players[7] = new CustomPlayer("Speech Test 8", fragments8,
					"videos/test01_003.mp4", textAboutTests[7]);

			this.concussionTestPanel.add(players[0]);

			//this.initiateSplittedProgress(String.valueOf(players.length));

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
									String percentage = String
											.valueOf((100.0 / players.length)
													* currentPlayerIndex);
									//makeSplittedProgress(percentage);
									String elementId = "conctestprogress-" +currentPlayerIndex; 
									DOM.getElementById(elementId).setClassName("progtrckr-done");
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

	public final native NodeList<Element> querySelector(String selector)/*-{
		return $wnd.document.querySelectorAll(selector);
	}-*/;

}
