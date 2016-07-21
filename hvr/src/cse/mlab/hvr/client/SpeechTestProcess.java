package cse.mlab.hvr.client;

import java.util.ArrayList;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.FieldSet;
import org.gwtbootstrap3.client.ui.Form;
import org.gwtbootstrap3.client.ui.FormGroup;
import org.gwtbootstrap3.client.ui.FormLabel;
import org.gwtbootstrap3.client.ui.Legend;
import org.gwtbootstrap3.client.ui.NavTabs;
import org.gwtbootstrap3.client.ui.TabContent;
import org.gwtbootstrap3.client.ui.TabListItem;
import org.gwtbootstrap3.client.ui.TabPane;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.Pull;
import org.gwtbootstrap3.client.ui.html.Br;
import org.gwtbootstrap3.client.ui.html.Hr;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.client.SpeechTestState.TestState;
import cse.mlab.hvr.client.events.TestCompletionEvent;
import cse.mlab.hvr.shared.speechtest.HealthStatusQuestion;
import cse.mlab.hvr.shared.speechtest.SpeechTestMetadata;

public class SpeechTestProcess extends Composite {
	@UiField
	HTMLPanel testProcessPanel;

	private NavTabs tabNavigation;
	private TabContent tabContent;

	private TabPane consentPane, healthPane, testPane;
	private TabListItem consentTab, healthTab, testTab;

	private Button acceptConsentButton, declineConsentButton, submitFormButton;

	private String testId;
	private SpeechTestMetadata metadata;

	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	private static SpeechTestUiBinder uiBinder = GWT
			.create(SpeechTestUiBinder.class);

	interface SpeechTestUiBinder extends UiBinder<Widget, SpeechTestProcess> {
	}

	public SpeechTestProcess(String testId) {
		initWidget(uiBinder.createAndBindUi(this));
		this.testId = testId;
		tabNavigation = new NavTabs();
		tabContent = new TabContent();
		testProcessPanel.add(tabNavigation);
		testProcessPanel.add(tabContent);

		consentPane = new TabPane();
		consentTab = new TabListItem("Consent");

		healthPane = new TabPane();
		healthTab = new TabListItem("Health QA");

		testPane = new TabPane();
		testTab = new TabListItem("Speech Test");

		declineConsentButton = new Button("Decline");
		declineConsentButton.setType(ButtonType.DANGER);
		// declineConsentButton.setSize(ButtonSize.LARGE);
		declineConsentButton.setPull(Pull.LEFT);
		declineConsentButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				Hvr.getEventBus().fireEvent(
						new TestCompletionEvent(new SpeechTestState(
								SpeechTestProcess.this.testId,
								TestState.DECLINED)));
			}
		});

		acceptConsentButton = new Button("Accept");
		acceptConsentButton.setType(ButtonType.SUCCESS);
		// acceptConsentButton.setSize(ButtonSize.LARGE);
		acceptConsentButton.setPull(Pull.RIGHT);
		acceptConsentButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if (healthTab.isAttached()) {
					enableHealthTab();
				} else {
					enableTestTab();
				}
			}
		});
		
		submitFormButton = new Button("Submit");
		submitFormButton.setType(ButtonType.SUCCESS);
		submitFormButton.setPull(Pull.RIGHT);
		submitFormButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(testTab.isAttached()){
					enableTestTab();
				}
			}
		});
		

		greetingService.getSpeechTestMetadata(testId,
				new AsyncCallback<SpeechTestMetadata>() {

					@Override
					public void onSuccess(SpeechTestMetadata result) {
						// TODO Auto-generated method stub
						// playerTab.add(new CustomPlayerManager(result));
						metadata = result;
						loadProcess(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						Window.alert("Fail to load the player, please try again");
					}
				});
	}

	private void loadProcess(SpeechTestMetadata metadata) {

		boolean tabEnabled = false;

		if (metadata.getTestOverview() != null
				&& metadata.getTestOverview().isConsentFileAvailable()) {
			consentPane.setActive(true);
			HTMLPanel consentContenPanel = new HTMLPanel("Electronic Consent Form");
			consentContenPanel
					.addStyleName("col-lg-offset-1 col-lg-10 panel_border");
			consentPane.add(new Br());
			consentPane.add(consentContenPanel);

			consentContenPanel.add(new Br());

			Frame frame = new Frame(
					"http://10.32.10.188:8080/hvr/metadata/1/consent.pdf");
			frame.setHeight("450px");
			frame.setWidth("100%");
			consentContenPanel.add(frame);
			consentContenPanel.add(new Br());
			consentContenPanel.add(new Hr());
			consentContenPanel.add(declineConsentButton);
			consentContenPanel.add(acceptConsentButton);

			consentTab.setActive(true);
			consentTab.setEnabled(true);
			tabEnabled = true;
			consentTab.setDataTargetWidget(consentPane);

			tabNavigation.add(consentTab);
			tabContent.add(consentPane);

		}

		if (metadata.getHealthStatusQuestions() != null
				&& metadata.getHealthStatusQuestions().size() > 0) {
			if (tabEnabled) {
				healthPane.setActive(false);
				healthTab.setActive(false);
				healthTab.setEnabled(false);
			} else {
				healthPane.setActive(true);
				healthTab.setActive(true);
				healthTab.setEnabled(true);
				tabEnabled = true;

				loadHealthQAForm();
			}
			healthTab.setDataTargetWidget(healthPane);

			tabNavigation.add(healthTab);
			tabContent.add(healthPane);
		}

		if (metadata.getSubTests() != null && metadata.getSubTests().size() > 0) {
			if (tabEnabled) {
				testPane.setActive(false);
				testTab.setActive(false);
				testTab.setEnabled(false);
			} else {
				testPane.setActive(true);
				testTab.setActive(true);
				testTab.setEnabled(true);
				tabEnabled = true;

				testPane.add(new CustomPlayerManager(metadata));
			}
			testTab.setDataTargetWidget(testPane);
			tabNavigation.add(testTab);
			tabContent.add(testPane);
		}

	}

	private void updateTabState(TabPane tabPane, TabListItem tab, boolean enable) {
		tabPane.setActive(enable);
		tab.setActive(enable);
		tab.setEnabled(enable);
	}

	private void enableHealthTab() {
		if (consentTab.isAttached()) {
			updateTabState(consentPane, consentTab, false);
		}

		updateTabState(healthPane, healthTab, true);

		loadHealthQAForm();
	}

	private void enableTestTab() {
		if (consentTab.isAttached()) {
			updateTabState(consentPane, consentTab, false);
		}
		if (healthTab.isAttached()) {
			updateTabState(healthPane, healthTab, false);
		}
		updateTabState(testPane, testTab, true);

		testPane.add(new CustomPlayerManager(metadata));

	}

	private void loadHealthQAForm() {
		ArrayList<HealthStatusQuestion> healthQuestions = this.metadata
				.getHealthStatusQuestions();
		if (healthQuestions != null) {
			healthPane.add(new Br());
			healthPane.add(new Br());
			Form qaForm = new Form();
			qaForm.addStyleName("col-lg-offset-2 col-lg-8");
			FieldSet qaFieldSet = new FieldSet();
			qaForm.add(qaFieldSet);
			Legend formLegend = new Legend("Please answer below questions:");
			qaFieldSet.add(formLegend);
			for (int i = 0; i < healthQuestions.size(); i++) {
				FormGroup formGroup = new FormGroup();
				TextBox answerBox = new TextBox();
				FormLabel questionLabel = new FormLabel();
				questionLabel.setText( (i+1) + ". " + healthQuestions.get(i).getQuestion());
				questionLabel.setPull(Pull.LEFT);
				formGroup.add(questionLabel);
				formGroup.add(new Br());
				formGroup.add(answerBox);
				qaFieldSet.add(formGroup);
			}
			qaForm.add(new Br());
			qaForm.add(submitFormButton);
			healthPane.add(qaForm);
		}

	}

}
