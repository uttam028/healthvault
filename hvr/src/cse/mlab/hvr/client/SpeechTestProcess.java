package cse.mlab.hvr.client;

import org.gwtbootstrap3.client.ui.NavTabs;
import org.gwtbootstrap3.client.ui.TabContent;
import org.gwtbootstrap3.client.ui.TabListItem;
import org.gwtbootstrap3.client.ui.TabPane;
import org.gwtbootstrap3.client.ui.html.Br;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.shared.speechtest.SpeechTestMetadata;

public class SpeechTestProcess extends Composite {
	@UiField
	TabPane playerTab;
	@UiField
	HTMLPanel testProcessPanel;
	
	private NavTabs tabsNavigation;
	private TabContent tabContent;
	
	
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	private static SpeechTestUiBinder uiBinder = GWT
			.create(SpeechTestUiBinder.class);

	interface SpeechTestUiBinder extends UiBinder<Widget, SpeechTestProcess> {
	}

	public SpeechTestProcess(String testId) {
		initWidget(uiBinder.createAndBindUi(this));
		greetingService.getSpeechTestMetadata(testId, new AsyncCallback<SpeechTestMetadata>() {
			
			@Override
			public void onSuccess(SpeechTestMetadata result) {
				// TODO Auto-generated method stub
				playerTab.add(new CustomPlayerManager(result));
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("Faile to load the player, please try again");
			}
		});
	}
	
	private void loadProcess(SpeechTestMetadata metadata){
		if(metadata.getTestOverview().isConsentFileAvailable()){
			TabPane consentPane = new TabPane();
			consentPane.setActive(true);
			consentPane.add(new Br());
			
			Frame frame = new Frame("http://10.32.10.188:8080/hvr/metadata/1/consent.pdf");
			
			
			TabListItem consentTab = new TabListItem("Consent");
			consentTab.setActive(true);
			consentTab.setDataTargetWidget(consentPane);
		}
	}

}
