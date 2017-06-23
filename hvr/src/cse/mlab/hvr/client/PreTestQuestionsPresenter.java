package cse.mlab.hvr.client;

import org.gwtbootstrap3.client.ui.Button;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.client.events.PreTestSubmitEvent;

public class PreTestQuestionsPresenter extends Composite  {

	@UiField
	Button pretestSubmitButton;
	
	private static PreTestQuestionsPresenterUiBinder uiBinder = GWT
			.create(PreTestQuestionsPresenterUiBinder.class);

	interface PreTestQuestionsPresenterUiBinder extends
			UiBinder<Widget, PreTestQuestionsPresenter> {
	}

	public PreTestQuestionsPresenter() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("pretestSubmitButton")
	void pretestAnswersSubmitted(ClickEvent event){
		Hvr.eventBus.fireEvent(new PreTestSubmitEvent());
	}
}
