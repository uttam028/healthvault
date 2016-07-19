package cse.mlab.hvr.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class HomePage extends Composite{
	

	Hvr application;
	private static LandingPageUiBinder uiBinder = GWT
			.create(LandingPageUiBinder.class);

	interface LandingPageUiBinder extends UiBinder<Widget, HomePage> {
	}

	public HomePage(Hvr application) {
		initWidget(uiBinder.createAndBindUi(this));
		this.application = application;
	}


	public HomePage(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	
	
//	@UiHandler("buttonConcussion")
//	void loadConcussionPage(ClickEvent event){
//		//this.application.mainPage.loadConcussionPage();
//		this.application.mainPage.loadVoicePage("concussion");
//	}
//	
//	@UiHandler("buttonDysarthria")
//	void loadDysarthriaPage(ClickEvent event){
//		//this.application.mainPage.loadDysarthriaPage();
//		this.application.mainPage.loadVoicePage("dysarthria");
//	}
}
