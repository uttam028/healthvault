package cse.mlab.hvr.client;

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

public class PersonalInfoPage extends Composite{

	private static PersonalInfoUiBinder uiBinder = GWT
			.create(PersonalInfoUiBinder.class);

	interface PersonalInfoUiBinder extends UiBinder<Widget, PersonalInfoPage> {
	}

	public PersonalInfoPage() {
		initWidget(uiBinder.createAndBindUi(this));
	}


}
