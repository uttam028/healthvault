package cse.mlab.hvr.client;

import org.gwtbootstrap3.client.ui.Anchor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class Concussion extends Composite  {
	
	@UiField
	Anchor buttonStartConcussionTest;
	
	Hvr application;

	private static ConcussionUiBinder uiBinder = GWT
			.create(ConcussionUiBinder.class);

	interface ConcussionUiBinder extends UiBinder<Widget, Concussion> {
	}

	public Concussion(Hvr application) {
		initWidget(uiBinder.createAndBindUi(this));
		this.application = application;
	}


	public Concussion(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("buttonStartConcussionTest")
	void userClickedToStartConcussionTest(ClickEvent event){
//		this.application.mainPage.loadVoicePage("concussion");;
	}
}
