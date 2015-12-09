package cse.mlab.hvr.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
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

	@Override
	protected void onLoad() {
		// TODO Auto-generated method stub
		super.onLoad();

		if (!testLoaded) {
			Fragment[] fragments1 = new Fragment[8];
			fragments1[0] = new Fragment("Participate", 1500, "text", 0, null);
			fragments1[1] = new Fragment("Application", 1500, "text", 0, null);
			fragments1[2] = new Fragment("Education", 1500, "text", 0, null);
			fragments1[3] = new Fragment("Difficulty", 1500, "text", 0, null);
			fragments1[4] = new Fragment("Congratulations", 1500, "text", 0,
					null);
			fragments1[5] = new Fragment("Possibility", 1500, "text", 0, null);
			fragments1[6] = new Fragment("Mathematical", 1500, "text", 0, null);
			fragments1[7] = new Fragment("Opportunity", 1500, "text", 0, null);

			Fragment[] fragments2 = { new Fragment(
					"We saw several wild animals", 3000, "text", 0, null) };

			Fragment[] fragments3 = {
					new MarkedTextFragment("Put the Book here", "Put", 3000),
					new MarkedTextFragment("Put the Book here", "Book", 3000),
					new MarkedTextFragment("Put the Book here", "here", 3000) };

			Fragment[] fragments4 = {
					new Fragment("Pa", 8000, "marquee", 1200, null),
					new Fragment("Pa", 8000, "marquee", 500, null) };

			Fragment[] fragments5 = {
					new Fragment("Ka", 8000, "marquee", 1200, null),
					new Fragment("Ka", 8000, "marquee", 500, null) };

			Fragment[] fragments6 = {
					new Fragment("PaTaKa", 10000, "marquee", 1300, null),
					new Fragment("PaTaKa", 10000, "marquee", 500, null) };

			Fragment[] fragments7 = { new Fragment(
					"Ahhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh", 5000, "marquee",
					2000, null) };

			Fragment[] fragments8 = { new Fragment(null, 10000, "image", 0,
					"images/illusion.jpg") };

			final CustomPlayer[] players = new CustomPlayer[8];

			players[0] = new CustomPlayer("Speech Test 1", fragments1,
					"samples/con_test1.mp3");
			players[1] = new CustomPlayer("Speech Test 2", fragments2,
					"samples/con_test2.mp3");
			players[2] = new CustomPlayer("Speech Test 3", fragments3, "");
			players[3] = new CustomPlayer("Speech Test 4", fragments4, "");
			players[4] = new CustomPlayer("Speech Test 5", fragments5, "");
			players[5] = new CustomPlayer("Speech Test 6", fragments6, "");
			players[6] = new CustomPlayer("Speech Test 7", fragments7, "");
			players[7] = new CustomPlayer("Speech Test 8", fragments8, "");
			
			this.concussionTestPanel.add(players[0]);

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
										+ currentPlayerIndex + ", len:"
										+ players.length);
							}

							try {
								if (currentPlayerIndex < players.length) {
									concussionTestPanel
											.add(players[currentPlayerIndex]);
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
