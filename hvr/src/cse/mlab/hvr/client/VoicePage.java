package cse.mlab.hvr.client;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.html.Br;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class VoicePage extends Composite {
	
	@UiField
	HTMLPanel voicePagePanel;
	
	Button configureMicButton, concussionTestButton, dysarthiaTestButton;
	
	Label labelForTest, labelForHistory;
	
	VerticalPanel testPanel;
	
	HorizontalPanel historyPanel;
	
	

	private static VoicePageUiBinder uiBinder = GWT
			.create(VoicePageUiBinder.class);

	interface VoicePageUiBinder extends UiBinder<Widget, VoicePage> {
	}

	public VoicePage() {
		initWidget(uiBinder.createAndBindUi(this));
		configureMicButton = new Button("Configure Microphone");
		configureMicButton.setType(ButtonType.DEFAULT);
		configureMicButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				configureMicrophone();
			}
		});
		
		concussionTestButton = new Button("Concussion test");
		concussionTestButton.setType(ButtonType.LINK);
		concussionTestButton.addStyleName("col-lg-offset-6");
		concussionTestButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				voicePagePanel.clear();
				voicePagePanel.add(new ConcussionTest());
			}
		});
		
		dysarthiaTestButton = new Button("Dysarthia test");
		dysarthiaTestButton.setType(ButtonType.LINK);
		dysarthiaTestButton.addStyleName("col-lg-offset-6");
		dysarthiaTestButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				voicePagePanel.clear();
				voicePagePanel.add(new DysarthiaTest());				
			}
		});
		
		testPanel = new VerticalPanel();
		labelForTest= new Label("Click to start one of the following test:");
		labelForTest.addStyleName("col-lg-offset-3 h2");

		labelForHistory = new Label("There will be a table or list that will show history of speech test taken by user and status of each test");
		labelForTest.addStyleName("col-lg-offset-3 h3");
		
		testPanel.add(labelForTest);
		testPanel.add(new Br());
		testPanel.add(concussionTestButton);
		testPanel.add(dysarthiaTestButton);
		testPanel.add(new Br());
		testPanel.add(new Br());
		testPanel.add(new Br());
		testPanel.add(new Br());
		testPanel.add(new Br());
		testPanel.add(labelForHistory);
		
		constructPage();
	}

	
	public VoicePage(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	private void constructPage(){
		voicePagePanel.add(configureMicButton);
		voicePagePanel.add(new Br());
		voicePagePanel.add(new Br());
		voicePagePanel.add(new Br());
		voicePagePanel.add(testPanel);
		voicePagePanel.add(new Br());
		voicePagePanel.add(new Br());
		voicePagePanel.add(new Br());
		voicePagePanel.add(new Br());
		voicePagePanel.add(new Br());
		voicePagePanel.add(new Br());
		voicePagePanel.add(new Br());
		voicePagePanel.add(new Br());
		voicePagePanel.add(new Br());
		voicePagePanel.add(new Br());
		voicePagePanel.add(new Br());
		voicePagePanel.add(new Br());
	}
	
	@Override
	protected void onLoad() {
		// TODO Auto-generated method stub
		super.onLoad();
		
		//this.voicePagePanel.clear();
		//this.voicePagePanel.add(new Br());
		//this.voicePagePanel.add(new Br());
		Hvr.getEventBus().addHandler(TestCompletionEvent.TYPE,
				new TestCompletionEventHandler() {
					
					@Override
					public void actionAfterTestCompleted(TestCompletionEvent event) {
						// TODO Auto-generated method stub
						voicePagePanel.clear();
						constructPage();
					}
				});

		
	}
	
	public native void configureMicrophone()/*-{
		//var name = player.@cse.mlab.hvr.client.CustomPlayer::header;
		//console.log("will ask for permission/ start recording from custom player ".concat(name))
		//Recorder.recorder.configure(rate, gain, silenceLevel, silenceTimeout);
		$wnd.FWRecorder.showPermissionWindow({permanent: true});
		$wnd.FWRecorder.configure(44, 1, 1, 4000);
	}-*/;

}

