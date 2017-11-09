package cse.mlab.hvr.client;

import org.gwtbootstrap3.client.ui.Anchor;
import org.gwtbootstrap3.client.ui.Button;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.client.PreTestState.InternalState;
import cse.mlab.hvr.client.events.PreTestInternalEvent;

public class BasicSystemChecker extends Composite {

	@UiField
	Label systemCheckLabel, allowLabel;
	@UiField
	HTMLPanel browserCheckPanel, reallowPanel, detectPanel;

	@UiField
	Button reaskButton, detectButton;
	
	@UiField
	Anchor chromeAnchor, mozillaAnchor;

	private static BasicSystemCheckerUiBinder uiBinder = GWT.create(BasicSystemCheckerUiBinder.class);

	interface BasicSystemCheckerUiBinder extends UiBinder<Widget, BasicSystemChecker> {
	}

	public BasicSystemChecker() {
		initWidget(uiBinder.createAndBindUi(this));

//		checkFlash();
		check();
	}
	
	public void check(){
//		boolean result = false;
		
		String browser = Hvr.getBrowserName();
		if(browser.toLowerCase().contains("firefox") || browser.toLowerCase().contains("chrome")){
			//ok
			browserCheckPanel.setVisible(false);
//			result = true;
			allowLabel.setVisible(true);
			testMediaDevice();
		}else {
			browserCheckPanel.setVisible(true);
//			systemCheckLabel.setText(browser);
		}

	}
	
	public void updateMicrophoneDetection(){
		detectPanel.setVisible(true);
		reallowPanel.setVisible(false);
		allowLabel.setVisible(false);
	}
	
	public void refreshCheck(boolean status){
		if(status){
			Hvr.getEventBus().fireEvent(new PreTestInternalEvent(new PreTestState(InternalState.BASIC)));
		} else {
			allowLabel.setVisible(false);
			reallowPanel.setVisible(true);
		}
		
	}
	
	@UiHandler("reaskButton")
	void reaskPressed(ClickEvent event){
		detectPanel.setVisible(false);
		reallowPanel.setVisible(false);
		allowLabel.setVisible(true);
		testMediaDevice();
	}
	
	@UiHandler("detectButton")
	void attachMicrophone(ClickEvent event){
		reaskPressed(null);
	}
	
	@UiHandler("chromeAnchor")
	void gotoChrome(ClickEvent event){
		Window.open("https://www.google.com/chrome/", "", "");
	}

	@UiHandler("mozillaAnchor")
	void gotoMozilla(ClickEvent event){
		Window.open("https://www.mozilla.org/en-US/firefox/", "", "");
	}

	public final native void testMediaDevice()/*-{
		
		$wnd.Html5Recorder.captureMicrophone();
		

	}-*/;

	public final native void checkFlash()/*-{
		try {
			if (navigator.mimeTypes["application/x-shockwave-flash"]) {
				var plugin = navigator.mimeTypes["application/x-shockwave-flash"].enabledPlugin;
				var description = plugin.description;
				var versionArray = description.match(/[\d.]+/g);
				var flashVersionOSXScriptable = 12;
				var flashVersion = parseInt(versionArray[0]);

				console.log("user agent: "+ navigator.userAgent + ", ver:" + navigator.appVersion);
				if (navigator.userAgent.indexOf("Mach-O") == -1) {
					if (flashVersion >= flashVersionOSXScriptable) {
						//alert("you have flash "+ flashVersion);
					} else {
						//alert("you don't have flash");
					}
				}
			} else {
				//alert("you don't have flash");
			}
		} catch (err) {
			//alert("error in flash :" + err);
		}
	}-*/;

}
