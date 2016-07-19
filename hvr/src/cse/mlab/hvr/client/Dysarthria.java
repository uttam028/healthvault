package cse.mlab.hvr.client;

import org.gwtbootstrap3.client.ui.Anchor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class Dysarthria extends Composite {
	
	@UiField
	Anchor buttonStartDysarthriaTest;
	
	Hvr application;
	private static DysarthriaUiBinder uiBinder = GWT
			.create(DysarthriaUiBinder.class);

	interface DysarthriaUiBinder extends UiBinder<Widget, Dysarthria> {
	}

	public Dysarthria(Hvr application) {
		initWidget(uiBinder.createAndBindUi(this));
		this.application = application;
	}


	public Dysarthria(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("buttonStartDysarthriaTest")
	void clickedToStartDysarthriaTest(ClickEvent event){
//		this.application.mainPage.loadVoicePage("dysarthria");
	}

}
