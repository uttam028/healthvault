package cse.mlab.hvr.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class Contacts extends Composite {

	@UiField
	HTMLPanel contactPanel;
	
	private static ContactsUiBinder uiBinder = GWT
			.create(ContactsUiBinder.class);

	interface ContactsUiBinder extends UiBinder<Widget, Contacts> {
	}

	public Contacts() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public HTMLPanel getContactPage(){
		return contactPanel;
	}
}
