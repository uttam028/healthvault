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

public class AccountInfoPage extends Composite {

	private static AccountInfoPageUiBinder uiBinder = GWT
			.create(AccountInfoPageUiBinder.class);

	interface AccountInfoPageUiBinder extends UiBinder<Widget, AccountInfoPage> {
	}

	public AccountInfoPage() {
		initWidget(uiBinder.createAndBindUi(this));
	}


}
