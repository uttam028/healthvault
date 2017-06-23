package cse.mlab.hvr.client;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Form;
import org.gwtbootstrap3.client.ui.TextArea;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class ContactPage extends Composite {

	@UiField
	TextArea textareaMessage;

	@UiField
	Form messageForm;

	@UiField
	HTMLPanel resultPanel;

	@UiField
	Button sendButton;

	private static ContactPageUiBinder uiBinder = GWT
			.create(ContactPageUiBinder.class);

	interface ContactPageUiBinder extends UiBinder<Widget, ContactPage> {
	}

	public ContactPage() {
		initWidget(uiBinder.createAndBindUi(this));

		textareaMessage.setHeight("15em");
		resultPanel.clear();

		sendButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if (messageForm.validate()) {
					resultPanel.add(new MessagePanel("Your message has been received.", "", false, "", ""));
					messageForm.reset();
					Timer timer = new Timer() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							resultPanel.clear();
						}
					};
					timer.schedule(5 * 1000);

				}
			}
		});
	}

	@Override
	protected void onLoad() {
		// TODO Auto-generated method stub
		super.onLoad();
		messageForm.reset();
	}
}
