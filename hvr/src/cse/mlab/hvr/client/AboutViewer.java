package cse.mlab.hvr.client;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ImageAnchor;
import org.gwtbootstrap3.client.ui.constants.IconSize;
import org.gwtbootstrap3.client.ui.constants.IconType;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class AboutViewer extends Composite {
	
	@UiField
	Button linkContact;

	@UiField
	ImageAnchor anchorDownload;
	
	private static AboutViewerUiBinder uiBinder = GWT
			.create(AboutViewerUiBinder.class);

	interface AboutViewerUiBinder extends UiBinder<Widget, AboutViewer> {
	}

	public AboutViewer(final String consentFormUrl) {
		initWidget(uiBinder.createAndBindUi(this));
		linkContact.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				History.newItem("contact");
			}
		});
		
		anchorDownload.setIcon(IconType.FILE_PDF_O);
		anchorDownload.setIconSize(IconSize.TIMES3);
		anchorDownload.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				//Window.Location.replace("/downloadServlet");
				Window.open(consentFormUrl, "", "");
			}
		});
	}

	

}
