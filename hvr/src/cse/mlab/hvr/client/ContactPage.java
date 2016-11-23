package cse.mlab.hvr.client;

import org.gwtbootstrap3.client.ui.TextArea;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class ContactPage extends Composite{
	
	@UiField
	TextArea textareaMessage;

	private static ContactPageUiBinder uiBinder = GWT
			.create(ContactPageUiBinder.class);

	interface ContactPageUiBinder extends UiBinder<Widget, ContactPage> {
	}

	public ContactPage() {
		initWidget(uiBinder.createAndBindUi(this));
		
		textareaMessage.setHeight("15em");
	}

}
